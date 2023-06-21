package com.ssafy.enjoytrip.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.model.dto.BCommentDto;
import com.ssafy.enjoytrip.model.dto.BCommentWriteDto;
import com.ssafy.enjoytrip.model.entity.BComment;
import com.ssafy.enjoytrip.model.service.BCommentService;
import com.ssafy.enjoytrip.model.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.model.entity.Board;
import com.ssafy.enjoytrip.model.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/boards")
public class BoardController {
	
	@Autowired
	private BoardService bs;
	@Autowired
	private BCommentService bcs;
	@Autowired
	private JwtService js;
	
	@GetMapping()
	public ResponseEntity<?> selectAll(){
		List<Board> boards = bs.selectAll();
		if(boards == null || boards.size()==0) return new ResponseEntity<Void>(HttpStatus.NO_CONTENT); 
		return new ResponseEntity<List<Board>>(boards, HttpStatus.OK);
	}

	// todo: 수정을 위해 select 하는 경우에도 조회수가 올라감. 수정 필요
	@GetMapping("/detail/{boardid}")
	public ResponseEntity<?> select(@PathVariable int boardid){
		Board board = bs.select(boardid);
		if(board == null) return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		bs.view(boardid);
		board.setComments(bcs.selectAll(boardid));
		return new ResponseEntity<Board>(board, HttpStatus.OK);
	}

	
	@GetMapping("/search/{keyword}")
	public ResponseEntity<?> search(@PathVariable String keyword){
		List<Board> boards = bs.search(keyword);
		if(boards == null || boards.size()==0) return new ResponseEntity<Void>(HttpStatus.NO_CONTENT); 
		return new ResponseEntity<List<Board>>(boards, HttpStatus.OK);
	}
	
	@DeleteMapping("/{boardid}")
	public ResponseEntity<?> delete(@PathVariable int boardid){
		int r = bs.delete(boardid);
		if(r == 0) return new ResponseEntity<Void>(HttpStatus.NO_CONTENT); 
		return new ResponseEntity<Integer>(r, HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<?> insert(@RequestBody Board board){
		int r = bs.insert(board);
		if(r==0) return new ResponseEntity<Void>(HttpStatus.NO_CONTENT); 
		return new ResponseEntity<Integer>(r,HttpStatus.OK);
	}
	
	@PutMapping()
	public ResponseEntity<?> update(@RequestBody Board board){
		int r = bs.update(board);
		if(r == 0) return new ResponseEntity<Void>(HttpStatus.NO_CONTENT); 
		return new ResponseEntity<Integer>(r, HttpStatus.CREATED);
	}

	@PostMapping("/{boardid}/comments")
	public ResponseEntity<?> writeBComment(@PathVariable int boardid, @RequestBody BCommentWriteDto commentDto){
		commentDto.setBoardid(boardid);
		commentDto.setWriter(js.getUserId());
		int r = bcs.writeComment(commentDto);

		if(r==0) return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	@DeleteMapping("comments/{commentid}")
	public ResponseEntity<?> deleteBComment(@PathVariable int commentid){
		int r = bcs.deleteComment(commentid);
		if(r==0) return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
