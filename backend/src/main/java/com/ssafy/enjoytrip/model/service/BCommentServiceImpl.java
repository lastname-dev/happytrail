package com.ssafy.enjoytrip.model.service;

import com.ssafy.enjoytrip.model.dto.BCommentWriteDto;
import com.ssafy.enjoytrip.model.entity.BComment;
import com.ssafy.enjoytrip.model.repository.BCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BCommentServiceImpl implements BCommentService{
    @Autowired
    private BCommentRepository repo;

    @Override
    public int writeComment(BCommentWriteDto commentDto)  {
        return repo.insert(commentDto);
    }

    @Override
    public int deleteComment(int commentid) {
        return repo.delete(commentid);
    }
    @Override
    public List<BComment> selectAll(int boardid){
        return repo.selectAll(boardid);
    }
}
