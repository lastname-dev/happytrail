package com.ssafy.enjoytrip.model.service;

import com.ssafy.enjoytrip.model.dto.BCommentWriteDto;
import com.ssafy.enjoytrip.model.entity.BComment;

import java.util.List;

public interface BCommentService {
    int writeComment(BCommentWriteDto commentDto);
    int deleteComment(int commentid);
    List<BComment> selectAll(int boardid);
}
