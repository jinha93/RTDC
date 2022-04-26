package com.rtdc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rtdc.model.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>{

}
