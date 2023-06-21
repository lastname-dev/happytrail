package com.ssafy.enjoytrip.model.service;

import com.ssafy.enjoytrip.model.dto.HCommentDto;
import com.ssafy.enjoytrip.model.entity.BComment;
import com.ssafy.enjoytrip.model.repository.HCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HCommentServiceImpl implements HCommentService{

   @Autowired
    HCommentRepository repo;
    @Override
    public int writeComment(HCommentDto commentDto) {
        return repo.insert(commentDto);
    }

    @Override
    public int deleteComment(int commentid) {
        return repo.delete(commentid);
    }

    @Override
    public List<HCommentDto> selectAll(int hotplaceid) {
        return repo.selectAll(hotplaceid);
    }

    @Override
    public int countComment(int hotplaceid) {
        return repo.countComment(hotplaceid);
    }

}
