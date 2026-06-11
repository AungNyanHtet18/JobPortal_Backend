package com.dev.anh.job.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dev.anh.job.utils.exception.BusinessException;

@Service
public class FileProvider {
	
	private final long MAX_SIZE = 10*1024*1024;
	
	public void validateFile(MultipartFile file,Set<String> allowedExtension) {
		
		if(file.isEmpty()) {
			 throw new BusinessException("File is empty");
		}
		
		if(file.getSize() > MAX_SIZE) {
			 throw new BusinessException("File size is too large");
		}
		
		String fileName = file.getOriginalFilename();
		
		if(fileName == null ||  !fileName.contains(".") ) {
			 throw new BusinessException("File Validation Exception");
		}
		
		String extension = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
		
		if(!allowedExtension.contains(extension)) {
			 throw new BusinessException("Unsupported File Type");
		}
	}
	
	public  String generateFileName(String email, MultipartFile file) {
				
		var fileName = file.getOriginalFilename(); //Retrieving original file name 
		var array = fileName.split("\\."); //Split with .(dot) eg. ["john_resume", "pdf"]
		var extension = array[array.length -1]; //Retrieving the last element ["john_resume","pdf"] from array and latest extension(eg.jpg)
		
		var username = email.substring(0, email.indexOf('@')); //Retrieving aung123@gmail.com to aung@123
		
		return "%s.%s".formatted(username,extension);    // username.replace(" ","") Removing space in username
	} 

	public String saveFile(String uploadPath, String ownerName, MultipartFile file) throws IOException {
		var fileName = generateFileName(ownerName, file);
		var filePath = Path.of(uploadPath, fileName);

		if(!Files.exists(filePath.getParent())) {
			Files.createDirectories(filePath.getParent());
		}

		Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

		return fileName;
	}
	
}
