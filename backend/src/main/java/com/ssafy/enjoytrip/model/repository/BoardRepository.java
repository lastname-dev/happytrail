package com.ssafy.enjoytrip.model.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.model.dto.BoardDto;
import com.ssafy.enjoytrip.model.entity.Board;

@Mapper
public interface BoardRepository {
	List<Board> selectAll();
	Board select(int boardid);
	List<Board> search(String keyword);

	int view(int boardid);
	int delete(int boardid);
	int insert(Board board);
	int update(Board board);
}
