package com.dev.anh.job.model.repo;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.dev.anh.job.model.BaseRepository;
import com.dev.anh.job.model.entity.ChatMessage;
import com.dev.anh.job.model.output.ChatMessageItem;

public interface ChatMessageRepo extends BaseRepository<ChatMessage, Long>{

	@Query("""
		select new com.dev.anh.job.model.output.ChatMessageItem(
			m.id,
			m.chatRoom.id,
			m.sender.id,
			m.sender.name,
			m.sender.email,
			case
				when m.sender.id = m.chatRoom.sender.id then m.chatRoom.recipient.id
				else m.chatRoom.sender.id
			end,
			case
				when m.sender.id = m.chatRoom.sender.id then m.chatRoom.recipient.name
				else m.chatRoom.sender.name
			end,
			case
				when m.sender.id = m.chatRoom.sender.id then m.chatRoom.recipient.email
				else m.chatRoom.sender.email
			end,
			m.content,
			m.createdAt
		)
		from ChatMessage m
		where m.chatRoom.id = :roomId
		order by m.createdAt asc, m.id asc
		""")
	List<ChatMessageItem> findMessagesByRoomId(@Param("roomId") Long roomId);

	@Query("""
			select m.read 
			from ChatMessage m
			where m.chatRoom.id = :roomId and m.read = :read and m.sender.id = :senderId
			""")
	List<Boolean> findUnReadMessage(@Param("roomId") Long roomId, @Param("read") boolean read, @Param("senderId") Long senderId);


}
