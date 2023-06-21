package com.ssafy.enjoytrip.model.service;

import java.util.List;

import com.ssafy.enjoytrip.model.entity.Festival;

public interface  FestivalService {
	List<Festival> selectAll();
	Festival selectDetail(int festivalid);
}
