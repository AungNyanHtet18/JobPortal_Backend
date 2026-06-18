package com.dev.anh.job.utils.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.dev.anh.job.model.consts.ApplicationStatus;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobApplyEmailService {

	@Value("${app.mail.from}")
	private String mailFrom;
	
	@Value("${app.mail.name}")
	private String mailName;
		
	private final JavaMailSender javaMailSender;
	
	public void sendEmail(String applicantEmail, String applicantName, ApplicationStatus status, 
						  String companyName, String jobTitle, LocalDateTime appliedAt) {
		String subject = "Your application for " +jobTitle.concat("at ").concat(companyName);
		String text = generateJobEmailContent(applicantName, status, companyName, jobTitle, appliedAt);
		sendEmail(applicantEmail, subject, text );
	}
	
	private void sendEmail(String applicantEmail, String subject, String text) {
		 MimeMessage message = javaMailSender.createMimeMessage();
		 
		 try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(mailFrom, mailName);
			helper.setTo(applicantEmail);
			helper.setSubject(subject);
			helper.setText(text, true);
			
			javaMailSender.send(message);
			
		} catch (Exception e) {
			//throw new EmailInvalidException("Failed to create email message", e);
			log.error("Email failed to send : {}", e.getMessage());
		}
	}
	
	private String generateJobEmailContent(String applicantName, ApplicationStatus status, String companyName, 
										   String jobTitle, LocalDateTime appliedAt) {
		 
		String statusMessage = switch (status) {
	
		    case APPLIED ->
		            "We’ve received your application and it is now in our system for review.";
	
		    case REVIEWING ->
		            "Your application is currently being reviewed by our recruitment team.";
	
		    case SHORTLISTED ->
		            "Great news! You have been shortlisted for the next stage of the selection process.";
	
		    case INTERVIEW ->
		            "You have been selected for an interview. Our team will contact you with the details soon.";
	
		    case OFFERED ->
		            "Congratulations! You have received a job offer. Please check your email for further instructions.";
	
		    case HIRED ->
		            "Welcome aboard! We’re excited to have you join our team.";
	
		    case REJECTED ->
		            "After careful consideration, we regret to inform you that your application was not successful this time.";
		};

	    return """
	            <div style="font-family: Arial, sans-serif; line-height: 1.6;">
	                <p>Dear %s,</p>

	                <p>Thank you for applying for the position of <strong>%s</strong> at <strong>%s</strong>.</p>

	                <p>%s</p>

	                <p>
	                    <strong>Application Details:</strong><br>
	                    Applied on: %s
	                </p>

	                <p>We appreciate your interest in joining our team and encourage you to apply for future opportunities if this one isn’t the right fit.</p>

	                <br>

	                <p>Best regards,<br>
	                %s Team</p>
	            </div>
	            """.formatted(
	            applicantName,
	            jobTitle,
	            companyName,
	            statusMessage,
	            appliedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
	            companyName
	    );
	}

}
