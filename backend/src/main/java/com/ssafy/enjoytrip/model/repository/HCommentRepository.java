package com.ssafy.enjoytrip.model.repository;


import com.ssafy.enjoytrip.model.dto.HCommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HCommentRepository {
    List<HCommentDto> selectAll(int hotplaceid);
    int delete(int commentid);
    int insert(HCommentDto hcomment);
    int countComment(int hotplaceid) ;
}
