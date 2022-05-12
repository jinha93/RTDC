package com.rtdc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rtdc.model.PointHistory;
import com.rtdc.model.User;

@Repository
public interface PointHistoryRepository extends JpaRepository<PointHistory, Long>{
	
	@Query(value = "SELECT SUM(POINT) FROM POINT_HISTORY WHERE USERNAME = :#{#user.username}", nativeQuery = true)
	public int sumPointByUser(@Param("user")User user);
}
