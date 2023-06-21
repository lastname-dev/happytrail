package com.ssafy.enjoytrip.model.service;

import java.util.List;

import com.ssafy.enjoytrip.model.dto.BCommentWriteDto;
import com.ssafy.enjoytrip.model.dto.BoardDto;
import com.ssafy.enjoytrip.model.entity.Board;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface BoardService {
	List<Board> selectAll();
	Board select(int boardid);
	List<Board> search(String keyword);

	int view(int boardid);
	int delete(int boardid);
	int insert(Board board);
	int update(Board board);
}
