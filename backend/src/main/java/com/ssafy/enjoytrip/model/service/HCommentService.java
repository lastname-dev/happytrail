package com.ssafy.enjoytrip.model.service;

import com.ssafy.enjoytrip.model.dto.BCommentWriteDto;
import com.ssafy.enjoytrip.model.dto.HCommentDto;
import com.ssafy.enjoytrip.model.entity.BComment;

import java.util.List;

public interface HCommentService {
    int writeComment(HCommentDto commentDto);
    int deleteComment(int commentid);
    List<HCommentDto> selectAll(int hotplaceid);
    int countComment(int hotplaceid);
}
