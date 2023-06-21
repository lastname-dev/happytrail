package com.ssafy.enjoytrip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.model.dto.TripDto;
import com.ssafy.enjoytrip.model.dto.TripSearchDto;
import com.ssafy.enjoytrip.model.service.SearchService;

@RestController
@RequestMapping("/search")
public class SearchController {
	
	@Autowired
	SearchService searchService;
	
	@GetMapping()
	public ResponseEntity<?> search(@RequestParam("sido") int sido,@RequestParam("contentType") int contentType,
			@RequestParam("title") String title,@RequestParam("lat") double lat, @RequestParam("lng") double lng){
		TripSearchDto trip = new TripSearchDto();
		trip.setSido(sido);
		trip.setContentType(contentType);
		trip.setTitle(title);
		System.out.println("시도 "+sido);
		System.out.println("타입 "+contentType);
		System.out.println("제목 "+title);
		List<TripDto> trips = searchService.select(trip);
		return new ResponseEntity<List<TripDto>>(trips,HttpStatus.ACCEPTED);
	}
}
