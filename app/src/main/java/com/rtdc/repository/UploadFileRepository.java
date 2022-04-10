package com.rtdc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rtdc.model.UploadFile;

public interface UploadFileRepository extends JpaRepository<UploadFile, Long>{
	
}
