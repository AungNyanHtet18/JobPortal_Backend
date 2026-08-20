package com.dev.anh.job.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dev.anh.job.model.entity.Account;
import com.dev.anh.job.model.entity.AccountFollow;
import com.dev.anh.job.model.entity.AccountFollow_;
import com.dev.anh.job.model.entity.Account_;
import com.dev.anh.job.model.entity.ChatMessage;
import com.dev.anh.job.model.entity.ChatRoom;
import com.dev.anh.job.model.entity.ChatRoom_;
import com.dev.anh.job.model.input.ChatMessageForm;
import com.dev.anh.job.model.output.AccountFollowListItem;
import com.dev.anh.job.model.output.ChatAccountDetail;
import com.dev.anh.job.model.output.ChatMessageItem;
import com.dev.anh.job.model.output.ChatRoomAccountListItem;
import com.dev.anh.job.model.repo.AccountFollowRepo;
import com.dev.anh.job.model.repo.AccountRepo;
import com.dev.anh.job.model.repo.ChatMessageRepo;
import com.dev.anh.job.model.repo.ChatRoomRepo;
import com.dev.anh.job.utils.exception.BusinessException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatService {

	private final AccountService accountService;
	private final AccountRepo accountRepo;
	private final AccountFollowRepo accountFollowRepo;
	private final ChatRoomRepo chatRoomRepo;
	private final ChatMessageRepo chatMessageRepo;
			
	@Transactional
	public ChatMessageItem sendMessage(String username, ChatMessageForm form) {
		var sender = accountService.findAccount(username);
		var recipient = accountService.findAccount(form.recipientId());
		checkChatAllowed(sender, recipient);
		var room = getOrCreateRoom(sender, recipient);

		var message = new ChatMessage();
		message.setChatRoom(room);
		message.setSender(sender);
		message.setContent(form.content().trim());

		var saved = chatMessageRepo.save(message);

		return new ChatMessageItem(
					saved.getId(),
					room.getId(),
					sender.getId(),
					sender.getName(),
					sender.getEmail(),
					recipient.getId(),
					recipient.getName(),
					recipient.getEmail(),
					saved.getContent(),
					saved.getCreatedAt());
	}
	
	public List<ChatMessageItem> findMessage(String username, Long recipientId) {
		var sender = accountService.findAccount(username);
		var recipient = accountService.findAccount(recipientId);
		checkChatAllowed(sender, recipient);

		return chatRoomRepo.findRoomBetween(sender.getId(), recipient.getId())
				.map(room -> chatMessageRepo.findMessagesByRoomId(room.getId()))
				.orElseGet(List::of);
	}
	
	public List<ChatRoomAccountListItem> searchChatRoomAccount(String username) {
		return chatRoomRepo.search(queryFuncForChat(username));
	}
	
	public List<AccountFollowListItem> searchFollowerAccount(String username) {
		return accountFollowRepo.search(queryFuncForFollow(username));
	}
	
	public ChatAccountDetail findChatAccountById(Long id) {
		return accountRepo.findById(id).map(ChatAccountDetail::from)
				 .orElseThrow(() -> new BusinessException("Chat Account ID: %d was not found".formatted(id)));
	}
	
	private Function<CriteriaBuilder, CriteriaQuery<ChatRoomAccountListItem>> queryFuncForChat(String username) {
		 return cb -> {
			var cq = cb.createQuery(ChatRoomAccountListItem.class);
			var root = cq.from(ChatRoom.class);
			
			var senderAccount = root.join(ChatRoom_.sender);
			var recipientAccount = root.join(ChatRoom_.recipient);
			ChatRoomAccountListItem.select(cq, cb, root, senderAccount, recipientAccount, username);
			
			cq.where(cb.or(
		    	cb.equal(senderAccount.get(Account_.email), username.toLowerCase()),
		        cb.equal(recipientAccount.get(Account_.email), username.toLowerCase())));
			
			return cq;
		 };
	}
	
	private Function<CriteriaBuilder, CriteriaQuery<AccountFollowListItem>> queryFuncForFollow(String username) {
		 return cb -> { 
			var cq = cb.createQuery(AccountFollowListItem.class);
			var root = cq.from(AccountFollow.class);
			
			var follower = root.join(AccountFollow_.follower, JoinType.INNER);
			AccountFollowListItem.select(cq, cb, root);
			
			var param = new ArrayList<Predicate>();
			param.add(cb.equal(cb.lower(follower.get(Account_.email)), username.toLowerCase(Locale.ROOT))); // Locale.ROOT(To make the lowercase conversion independent of the computer/server's language settings.)
			cq.where(param.toArray(size -> new Predicate[size]));
			
			return cq;
		 };
	}

	private ChatRoom getOrCreateRoom(Account sender, Account recipient) {
		return chatRoomRepo.findRoomBetween(sender.getId(), recipient.getId())
				.orElseGet(() -> {
					var chatRoom = new ChatRoom();
					chatRoom.setSender(sender);
					chatRoom.setRecipient(recipient);
					return chatRoomRepo.save(chatRoom);
				});
	}

	private void checkChatAllowed(Account sender, Account recipient) {
		if(sender.getId().equals(recipient.getId())) {
			throw new BusinessException("You cannot chat with yourself.");
		}
	}
}
