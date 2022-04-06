package com.rtdc.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rtdc.model.Board;
import com.rtdc.repository.BoardRepository;


@Service
public class BoardService {
	
	private BoardRepository boardRepository;
	
	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}
	
	public Page<Board> getBoardList(Pageable pageable){
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); //page는 0부터 시작
		pageable = PageRequest.of(page, 10, Sort.by("id").descending());
		
		return boardRepository.findAll(pageable);
	}
	
	public Board getBoard(Long id) {
		return boardRepository.findById(id).orElse(null);
	}
	
	public Board save(Board board) {
		return boardRepository.save(board);
	}
	
}
