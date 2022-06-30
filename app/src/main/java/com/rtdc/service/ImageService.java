package com.rtdc.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.rtdc.model.UploadFile;
import com.rtdc.repository.UploadFileRepository;

@Service
public class ImageService {

	@Autowired
	UploadFileRepository uploadFileRepository;
	
	private Path rootLocation;
	
	private final Path uploadFilePath;
	private final Path raffleFilePath;
	private final Path profileFilePath;
	
		
	public ImageService(
			@Value("${custom.upload-img-path}") String uploadFilePath
			, @Value("${custom.raffle-img-path}") String raffleFilePath
			, @Value("${custom.profile-img-path}") String profileFilePath
			) {
		this.uploadFilePath = Paths.get(uploadFilePath);
		this.raffleFilePath = Paths.get(raffleFilePath);
		this.profileFilePath = Paths.get(profileFilePath);
	}
	
	private void FilePathSetting(String gb) {
		if("post".equals(gb)) {
			this.rootLocation = this.uploadFilePath;
		}else if("raffle".equals(gb)) {
			this.rootLocation = this.raffleFilePath;
		}else if("profile".equals(gb)) {
			this.rootLocation = this.profileFilePath;
		}
	}
	
	public UploadFile store(MultipartFile file, String gb) throws Exception {
		
		try {
			if(file.isEmpty()) {
				throw new Exception("Failed to store empty file " + file.getOriginalFilename());
			}
			if(gb == null || "".equals(gb)) {
				throw new Exception("gb is null");
			}
			FilePathSetting(gb);
			
			String saveFileName = fileSave(rootLocation.toString(), file);
			UploadFile saveFile = new UploadFile();
			saveFile.setFileName(file.getOriginalFilename());
			saveFile.setSaveFileName(saveFileName);
			saveFile.setContentType(file.getContentType());
			saveFile.setSize(file.getResource().contentLength());
			saveFile.setRegisterDate(LocalDateTime.now());
			saveFile.setFilePath(rootLocation.toString().replace(File.separatorChar, '/') +'/' + saveFileName);   
			uploadFileRepository.save(saveFile);
			return saveFile;
			
		} catch(IOException e) {
			throw new Exception("Failed to store file " + file.getOriginalFilename(), e);
		}
		
		
	}

	public UploadFile load(Long fileId) {
		return uploadFileRepository.findById(fileId).get();
	}
	
	public String fileSave(String rootLocation, MultipartFile file) throws IOException {
		File uploadDir = new File(rootLocation);
		
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		
		// saveFileName 생성
		UUID uuid = UUID.randomUUID();
		String saveFileName = uuid.toString() + file.getOriginalFilename();
		File saveFile = new File(rootLocation, saveFileName);
//		File saveFile = new File(saveFileName);
		FileCopyUtils.copy(file.getBytes(), saveFile);
		
		return saveFileName;
	}
}