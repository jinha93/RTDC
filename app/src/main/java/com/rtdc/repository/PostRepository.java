package com.rtdc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rtdc.model.Board;
import com.rtdc.model.Post;

public interface PostRepository extends JpaRepository<Post, Long>{
	
	Page<Post> findByBoard(Board board, Pageable pageable);
	
	@Query(
			value = "SELECT * FROM POST WHERE BOARD_ID = :#{#post.board.boardId} AND DEL_YN = 'N' AND (TITLE LIKE %:#{#post.title}% OR CONTENT LIKE %:#{#post.content}%) ORDER BY POST_ID DESC",
			countQuery = "SELECT COUNT(*) FROM POST WHERE BOARD_ID = :#{#post.board.boardId} AND DEL_YN = 'N' AND (TITLE LIKE %:#{#post.title}% OR CONTENT LIKE %:#{#post.content}%)",
			nativeQuery = true
			)
	Page<Post> findByBoardAndTitleContainingOrContentContaining(@Param("post")Post post, Pageable pageable);
	
	Post findByBoardAndPostId(Board board, Long postId);

}
