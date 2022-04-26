package com.rtdc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rtdc.model.Board;
import com.rtdc.repository.BoardRepository;


@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	public Board getBoard(Long boardId) {
		return boardRepository.findById(boardId).orElse(null);
	}

	
}
