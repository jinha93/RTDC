package com.rtdc;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.rtdc.model.Board;
import com.rtdc.repository.BoardRepository;

@SpringBootApplication
public class RtdcApplication {

	public static void main(String[] args) {
		SpringApplication.run(RtdcApplication.class, args);
	}
	
	 /**
     * 154 개 게시물 생성
     * @param boardRepository
     * @return
     */
    @Bean
    public CommandLineRunner initData(BoardRepository boardRepository) {
        return args -> 
            IntStream.rangeClosed(1, 154).forEach(i -> {
                Board board = Board.builder()
                        .title("title" + i)
                        .content("content" + i)
                        .regDateTime(LocalDateTime.now())
                        .readCnt(0)
                        .build();

                boardRepository.save(board);
            });
    }

}
