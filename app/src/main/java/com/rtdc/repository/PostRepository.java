package com.rtdc.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rtdc.model.Board;
import com.rtdc.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
	
	@Query(
			value = "SELECT * FROM POST WHERE BOARD_ID = :#{#post.board.boardId} AND DEL_YN = 'N' AND (TITLE LIKE %:#{#post.title}% OR CONTENT LIKE %:#{#post.content}%) ORDER BY POST_ID DESC",
			countQuery = "SELECT COUNT(*) FROM POST WHERE BOARD_ID = :#{#post.board.boardId} AND DEL_YN = 'N' AND (TITLE LIKE %:#{#post.title}% OR CONTENT LIKE %:#{#post.content}%)",
			nativeQuery = true
			)
	Page<Post> findByBoardAndTitleContainingOrContentContaining(@Param("post")Post post, Pageable pageable);
	
	Post findByBoardAndPostId(Board board, Long postId);

	/**
	 * 인기글 목록 조회
	 * @return
	 */
	List<Post> findTop8ByOrderByReadCntDesc();
	/**
	 * 최신글 목록 조회
	 * @return
	 */
	List<Post> findTop8ByOrderByRegDateTimeDesc();

}
