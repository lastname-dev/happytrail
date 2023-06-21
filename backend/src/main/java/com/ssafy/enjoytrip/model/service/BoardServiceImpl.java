package com.ssafy.enjoytrip.model.service;

import java.util.List;

import com.ssafy.enjoytrip.model.dto.BCommentWriteDto;
import com.ssafy.enjoytrip.model.repository.BCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.model.dto.BoardDto;
import com.ssafy.enjoytrip.model.entity.Board;
import com.ssafy.enjoytrip.model.repository.BoardRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardRepository repo;

	@Override
	public List<Board> selectAll() {
		return repo.selectAll();
	}

	@Override
	public Board select(int boardid) {
		return repo.select(boardid);
	}
	
	@Override
	public List<Board> search(String keyword) {
		return repo.search(keyword);
	}

	@Override
	public int delete(int boardid) {
		return repo.delete(boardid);
	}

	@Override
	public int insert(Board board) {
		return repo.insert(board);
	}

	@Override
	public int update(Board board) {
		return repo.update(board);
	}



	@Override
	public int view(int boardid) {return repo.view(boardid);}
}
