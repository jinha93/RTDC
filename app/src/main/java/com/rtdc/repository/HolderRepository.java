package com.rtdc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rtdc.model.Holder;
import com.rtdc.model.HolderId;
import com.rtdc.model.User;

@Repository
public interface HolderRepository extends JpaRepository<Holder, HolderId>{

	Holder findByGbAndTokenIdAndDelYn(String gb, String tokenId, String string);

	List<Holder> findByGbAndUserAndDelYn(String gb, User user, String string);
	
}
