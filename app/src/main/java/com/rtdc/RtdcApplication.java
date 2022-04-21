package com.rtdc;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.rtdc.model.Board;
import com.rtdc.model.Post;
import com.rtdc.repository.BoardRepository;
import com.rtdc.repository.PostRepository;

@SpringBootApplication
public class RtdcApplication {

	public static void main(String[] args) {
		SpringApplication.run(RtdcApplication.class, args);
	}
	
    @Bean
    public CommandLineRunner initData(BoardRepository boardRepository, PostRepository postRepository) {
        return args -> 
            IntStream.rangeClosed(1, 154).forEach(i -> {
            	if(i == 1) {
            		Board board = Board.builder()
            				.boardId(1)
            				.BoardNm("공지사항")
            				.build();
            		boardRepository.save(board);
            		board = Board.builder()
            				.boardId(2)
            				.BoardNm("이벤트")
            				.build();
            		boardRepository.save(board);
            		board = Board.builder()
            				.boardId(3)
            				.BoardNm("명예의전당")
            				.build();
            		boardRepository.save(board);
            		board = Board.builder()
            				.boardId(4)
            				.BoardNm("자유게시판")
            				.build();
            		
            		boardRepository.save(board);
            	}
            	Board board = new Board();
            	board.setBoardId(4);
            	Post post = Post.builder()
            			.board(board)
                        .title("title" + i)
                        .content("content" + i)
                        .regDateTime(LocalDateTime.now())
                        .readCnt(0)
                        .build();

            	postRepository.save(post);
            });
    }

}
