package com.rtdc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rtdc.model.UploadFile;

@Repository
public interface UploadFileRepository extends JpaRepository<UploadFile, Long>{
	
}
