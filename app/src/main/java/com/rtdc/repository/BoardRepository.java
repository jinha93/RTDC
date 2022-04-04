package com.rtdc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rtdc.model.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{

}
