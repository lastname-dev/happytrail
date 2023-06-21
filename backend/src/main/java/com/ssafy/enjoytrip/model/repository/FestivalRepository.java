package com.ssafy.enjoytrip.model.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.model.entity.Festival;

@Mapper
public interface FestivalRepository {
	List<Festival> selectAll();
	Festival selectDetail(int festivalid);
}
