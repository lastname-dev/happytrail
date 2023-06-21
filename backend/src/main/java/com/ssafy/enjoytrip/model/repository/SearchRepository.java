package com.ssafy.enjoytrip.model.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.model.dto.TripDto;
import com.ssafy.enjoytrip.model.dto.TripSearchDto;

@Mapper
public interface SearchRepository {
	public List<TripDto> select(TripSearchDto trip);
}
