package com.dev.anh.job.model.output;

import java.time.LocalDateTime;

public record ChatMessageItem(
	Long id,
	Long roomId,
	Long senderId,
	String senderName,
	String senderEmail,
	Long recipientId,
	String recipientName,
	String recipientEmail,
	String content,
	LocalDateTime createdAt) {

}
