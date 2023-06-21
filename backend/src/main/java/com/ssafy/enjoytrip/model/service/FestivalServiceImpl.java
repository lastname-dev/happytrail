package com.ssafy.enjoytrip.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.model.entity.Festival;
import com.ssafy.enjoytrip.model.repository.FestivalRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FestivalServiceImpl implements FestivalService {
	
	@Autowired
	private FestivalRepository repo;

	@Override
	public List<Festival> selectAll() {
		return repo.selectAll();
	}

	@Override
	public Festival selectDetail(int festivalid) {
		return repo.selectDetail(festivalid);
	}
	
}
