package com.rtdc;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rtdc.model.Board;
import com.rtdc.model.Event;
import com.rtdc.model.Post;
import com.rtdc.model.UploadFile;
import com.rtdc.model.User;
import com.rtdc.repository.BoardRepository;
import com.rtdc.repository.EventRepository;
import com.rtdc.repository.PostRepository;
import com.rtdc.repository.UploadFileRepository;
import com.rtdc.repository.UserRepository;

@SpringBootApplication
public class RtdcApplication {

	public static void main(String[] args) {
		SpringApplication.run(RtdcApplication.class, args);
	}
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
    @Bean
    public CommandLineRunner initData(BoardRepository boardRepository, PostRepository postRepository, EventRepository eventRepository, UploadFileRepository uploadFileRepository, UserRepository userRepository) {
        return args -> 
            IntStream.rangeClosed(1, 154).forEach(i -> {
//            	if(i == 1) {
//            		Board board = Board.builder()
//            				.boardId(0)
//            				.boardNm("공지사항")
//            				.build();
//            		boardRepository.save(board);
//            		board = Board.builder()
//            				.boardId(1)
//            				.boardNm("P2E(MTDZ THE GAME)")
//            				.build();
//            		boardRepository.save(board);
//            		board = Board.builder()
//            				.boardId(2)
//            				.boardNm("P2E(META TOY CITY)")
//            				.build();
//            		boardRepository.save(board);
//            		board = Board.builder()
//            				.boardId(3)
//            				.boardNm("자유게시판")
//            				.build();
//            		boardRepository.save(board);
//            		board = Board.builder()
//            				.boardId(4)
//            				.boardNm("건의/신고")
//            				.build();
//            		boardRepository.save(board);
//            		
//            		User user = new User();
//            		user = User.builder()
//            				.username("admin")
//            				.password("admin99!")
//            				.nickname("관리자")
//            				.role("ADMIN")
//            				.build();
//            		user.encodePassword(passwordEncoder);
//            		userRepository.save(user);
//            	}
//            	
//            	if(i < 13) {
//            		UploadFile file = UploadFile.builder()
//            				.contentType("image/jpeg")
//            				.fileName("mbti.jpg")
//            				.filePath("c:/dev/WORKSPACE/RTDC/upload-file/21752a67-7efb-43b3-8cb9-903fd9ffa574mbti.jpg")
//            				.registerDate(LocalDateTime.now())
//            				.saveFileName("21752a67-7efb-43b3-8cb9-903fd9ffa574mbti.jpg")
//            				.size(414862)
//            				.build();
//            		uploadFileRepository.save(file);
//            		
//            		User user = new User();
//            		user.setUsername("admin");
//            		
//            		Event event = Event.builder()
//            				.title("이벤트테스트" + i)
//            				.cardImg("<p><img src=\"/image/1\" class=\"card-img-top\"><br></p>")
//            				.content("이벤트내용" + i)
//            				.startDateTime(LocalDateTime.now())
//            				.endDateTime(LocalDateTime.now().plusDays(i))
//            				.status("0")
//            				.user(user)
//            				.build();
//            		
//            		eventRepository.save(event);
//            	}
            	
//            	Board board = new Board();
//            	board.setBoardId(3);
//            	User user = new User();
//            	user.setUsername("admin");
//            	Post post = Post.builder()
//            			.board(board)
//                        .title("title" + i)
//                        .content("content" + i)
//                        .regDateTime(LocalDateTime.now())
//                        .readCnt(0)
//                        .user(user)
//                        .build();
//
//            	postRepository.save(post);
            });
    }

}
