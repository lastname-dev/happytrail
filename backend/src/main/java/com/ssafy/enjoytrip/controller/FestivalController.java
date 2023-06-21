package com.ssafy.enjoytrip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.model.entity.Festival;
import com.ssafy.enjoytrip.model.service.FestivalService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/festivals")
public class FestivalController {
	
	@Autowired
	FestivalService fs;
	
	@GetMapping()
	public ResponseEntity<?> selectAll() {
		List<Festival> festivals = fs.selectAll();
		if (festivals == null || festivals.size() == 0) return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<Festival>>(festivals, HttpStatus.OK);
	}
	
	@GetMapping("/{festivalid}")
	public ResponseEntity<?> selectDetail(@PathVariable int festivalid) {
		Festival festival = fs.selectDetail(festivalid);

		if (festival == null) return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<Festival>(festival, HttpStatus.OK);
	}

}
