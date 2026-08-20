package com.dev.anh.job.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.anh.job.model.input.ChatMessageForm;
import com.dev.anh.job.model.input.UnReadMessageSenderRequestList;
import com.dev.anh.job.model.output.AccountFollowListItem;
import com.dev.anh.job.model.output.ChatAccountDetail;
import com.dev.anh.job.model.output.ChatMessageItem;
import com.dev.anh.job.model.output.ChatRoomAccountListItem;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.output.UnReadMessageSenderListItem;
import com.dev.anh.job.model.service.ChatService;
import com.dev.anh.job.utils.exception.BusinessException;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("chat")
@RequiredArgsConstructor
public class ChatController {

	private final ChatService chatService;
	private final SimpMessagingTemplate messagingTemplate;
		
	@MessageMapping("chat.send")
	void sendMessage(@Payload @Validated ChatMessageForm form, Principal principal) {
		
		if (principal == null) {
		    throw new BusinessException("Authenticated user is required for chat.");
		}

		String username = principal.getName();
		var message = chatService.sendMessage(username, form);

		messagingTemplate.convertAndSendToUser(username, "/queue/messages", message);
		messagingTemplate.convertAndSendToUser(message.recipientEmail(), "/queue/messages", message);
	}
	
	@GetMapping("messages/{recipientId}")
	List<ChatMessageItem> findMessage(@PathVariable @NotNull(message = "Recipient account is required") Long recipientId) {
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		return chatService.findMessage(username, recipientId);
	}
	
	@GetMapping("chatRoom/accountList")
	List<ChatRoomAccountListItem> searchChatRoomAccount() {
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		return chatService.searchChatRoomAccount(username);
	}
	
	@GetMapping("followList")
	List<AccountFollowListItem> searchFollowerAccount() {
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		return chatService.searchFollowerAccount(username);
	}
	
	@GetMapping("account/{id}")
	ChatAccountDetail findChatAccountById(@PathVariable Long id) {
		return chatService.findChatAccountById(id);
	}
	
	@PostMapping("unreadMessage") 
	ModificationResult<List<UnReadMessageSenderListItem>> unReadMessage(@RequestBody @NotEmpty List<@Valid UnReadMessageSenderRequestList> senderList) {
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		return chatService.unReadMessage(username, senderList);
	}
	
	
	
	
	
}
