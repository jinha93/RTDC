package com.rtdc.service;

import org.springframework.stereotype.Service;

import com.rtdc.model.Board;
import com.rtdc.repository.BoardRepository;


@Service
public class BoardService {
	
	private BoardRepository boardRepository;
	
	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}
	
	public Board getBoard(Long boardId) {
		return boardRepository.findById(boardId).orElse(null);
	}

	
}
