package com.ssafy.enjoytrip.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.model.dto.TripDto;
import com.ssafy.enjoytrip.model.dto.TripSearchDto;
import com.ssafy.enjoytrip.model.repository.SearchRepository;

@Service
public class SearchServiceImpl implements SearchService {
	
	@Autowired
	SearchRepository repo;
	
	@Override
	public List<TripDto> select(TripSearchDto trip) {
		List<TripDto> trips = repo.select(trip);
		System.out.println(trips);
		return trips;
	}

}
