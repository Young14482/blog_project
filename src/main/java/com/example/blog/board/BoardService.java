package com.example.blog.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public List<BoardResponse.DTO> 게시글목록보기() {
        List<BoardResponse.DTO> dtos = new ArrayList<>();

        List<Board> boardList = boardRepository.findAll();

        for (Board board : boardList) {
            BoardResponse.DTO dto = new BoardResponse.DTO(board);
            dtos.add(dto);
        }
        return dtos;
    }

    public BoardResponse.DetailDTO 게시글상세보기(int id) {
        Board board = boardRepository.findById(id);
        return new BoardResponse.DetailDTO(board);
    }

    @Transactional // 커밋용
    public void 게시글쓰기(BoardRequest.SaveDTO saveDTO) {
        boardRepository.save(saveDTO.getTitle(), saveDTO.getContent());
    }

    @Transactional
    public void 게시글삭제(int id) {
        boardRepository.delete(id);
    }

    @Transactional
    // 내부 필드 값을 꺼내서 쓰는 이유? >> repository의 쿼리문은 여러군데에서 쓰일 수 있음 >> 의존적이면 좋지않음
    public void 게시글수정하기(int id, BoardRequest.UpdateDTO updateDTO) {
        boardRepository.update(id, updateDTO.getTitle(), updateDTO.getContent());
    }

    public BoardResponse.UpdateFormDTO 게시글수정화면보기(int id) {
        Board board = boardRepository.findById(id);
        return new BoardResponse.UpdateFormDTO(board);
    }
}
