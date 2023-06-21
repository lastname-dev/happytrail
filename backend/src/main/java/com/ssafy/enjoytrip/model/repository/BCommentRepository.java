package com.ssafy.enjoytrip.model.repository;

import java.util.List;

import com.ssafy.enjoytrip.model.dto.BCommentWriteDto;
import com.ssafy.enjoytrip.model.entity.BComment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BCommentRepository {
	List<BComment> selectAll(int boardid);
	
	int delete(int commentid);
	int insert(BCommentWriteDto bcomment);
	int update(int commentid, String content);
}
