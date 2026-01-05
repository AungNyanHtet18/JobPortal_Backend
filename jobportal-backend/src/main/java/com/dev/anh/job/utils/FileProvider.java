package com.dev.anh.job.utils;

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
	
	public  String generateFileName(String username, MultipartFile file) {
				
		var fileName = file.getOriginalFilename(); //Retrieving original file name 
		var array = fileName.split("\\."); //Split with .(dot) 
		var extension = array[array.length -1]; //Retrieving latest extension(eg.jpg)
		
		return "%s.%s".formatted(username.replace(" ",""),extension);    // username.replace(" ","") Removing space in username
	} 
	
}
