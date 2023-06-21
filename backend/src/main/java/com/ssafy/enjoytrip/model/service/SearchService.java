package com.ssafy.enjoytrip.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.model.dto.TripDto;
import com.ssafy.enjoytrip.model.dto.TripSearchDto;

@Service
public interface SearchService {
	public List<TripDto> select(TripSearchDto trip);
}
