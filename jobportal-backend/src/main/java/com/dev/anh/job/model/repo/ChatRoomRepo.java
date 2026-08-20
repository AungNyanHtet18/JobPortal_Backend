package com.dev.anh.job.model.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dev.anh.job.model.BaseRepository;
import com.dev.anh.job.model.entity.ChatRoom;

public interface ChatRoomRepo extends BaseRepository<ChatRoom, Long>{

	@Query("""
		select c from ChatRoom c
		where (c.sender.id = :accountId and c.recipient.id = :recipientId)
		   or (c.sender.id = :recipientId and c.recipient.id = :accountId)
		""")
	Optional<ChatRoom> findRoomBetween(@Param("accountId") Long accountId, @Param("recipientId") Long recipientId);

	
	
}
