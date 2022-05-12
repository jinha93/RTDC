package com.rtdc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rtdc.model.PointHistory;
import com.rtdc.model.User;
import com.rtdc.repository.PointHistoryRepository;


@Service
public class PointHistoryService {
	
	@Autowired
	private PointHistoryRepository pointHistoryRepository;
	
	public int savePoint(User user, int point, String content) {
		//포인트 이력 저장
		PointHistory pointHistory = new PointHistory();
		pointHistory.setPoint(point);
		pointHistory.setContent(content);
		pointHistory.setUser(user);
		pointHistoryRepository.save(pointHistory);
		
		return pointHistoryRepository.sumPointByUser(user);
	}
	
	
}
