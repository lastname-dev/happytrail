package com.ssafy.enjoytrip.controller;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.enjoytrip.model.dto.BCommentWriteDto;
import com.ssafy.enjoytrip.model.dto.HCommentDto;
import com.ssafy.enjoytrip.model.service.HCommentService;
import com.ssafy.enjoytrip.model.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.enjoytrip.model.dto.HotplaceDto;
import com.ssafy.enjoytrip.model.dto.HotplaceModifyDto;
import com.ssafy.enjoytrip.model.service.HotplaceService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/hotplaces")
public class HotplaceController {

	@Autowired
	private HotplaceService hs;
	@Autowired
	private HCommentService hcs;
	@Autowired
	private JwtService js;

	// ################ 조회 ################

	@GetMapping()
	public ResponseEntity<?> selectAll() {
		List<HotplaceDto> hotplaces = hs.selectAll();
		for(HotplaceDto hotplaceDto: hotplaces){
			hotplaceDto.setCommentcnt(hcs.countComment(hotplaceDto.getHotplaceid()));
		}
		if (hotplaces == null || hotplaces.size() == 0)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<HotplaceDto>>(hotplaces, HttpStatus.OK);
	}

	@GetMapping("/user/{userid}")
	public ResponseEntity<?> selectUser(@PathVariable String userid) {
		List<HotplaceDto> hotplaces = hs.selectUser(userid);

		if (hotplaces == null || hotplaces.size() == 0)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<HotplaceDto>>(hotplaces, HttpStatus.OK);
	}

	@GetMapping("/follow/{userid}")
	public ResponseEntity<?> selectFollow(@PathVariable String userid) {
		List<HotplaceDto> hotplaces = hs.selectFollow(userid);

		if (hotplaces == null || hotplaces.size() == 0)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<HotplaceDto>>(hotplaces, HttpStatus.OK);
	}

	@GetMapping("/like/{userid}")
	public ResponseEntity<?> selectLike(@PathVariable String userid) {
		List<HotplaceDto> hotplaces = hs.selectLike(userid);

		if (hotplaces == null || hotplaces.size() == 0)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<HotplaceDto>>(hotplaces, HttpStatus.OK);
	}

	@GetMapping("/search/{keyword}")
	public ResponseEntity<?> search(@PathVariable String keyword) {
		System.out.println(keyword);
		List<HotplaceDto> hotplaces = hs.search(keyword);

		if (hotplaces == null || hotplaces.size() == 0)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<HotplaceDto>>(hotplaces, HttpStatus.OK);
	}
	
	@GetMapping("/search/follow/{keyword}")
	public ResponseEntity<?> searchFollow(@PathVariable String keyword) {
		System.out.println(keyword);
		List<HotplaceDto> hotplaces = hs.searchFollow(keyword);

		if (hotplaces == null || hotplaces.size() == 0)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<HotplaceDto>>(hotplaces, HttpStatus.OK);
	}
	
	@GetMapping("/search/like/{keyword}")
	public ResponseEntity<?> searchLike(@PathVariable String keyword) {
		System.out.println("안녕");
		System.out.println(keyword);
		List<HotplaceDto> hotplaces = hs.searchLike(keyword);

		if (hotplaces == null || hotplaces.size() == 0)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<HotplaceDto>>(hotplaces, HttpStatus.OK);
	}

	@GetMapping("/{hotplaceid}")
	public ResponseEntity<?> selectDetail(@PathVariable String hotplaceid) {
		HotplaceDto hotplaces = hs.selectDetail(hotplaceid);
		hotplaces.setComments(hcs.selectAll(Integer.parseInt(hotplaceid)));
		if (hotplaces == null)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<HotplaceDto>(hotplaces, HttpStatus.OK);
	}

	// ################ 좋아요 ################

	@GetMapping("/{hotplaceid}/like/{userid}")
	public ResponseEntity<?> isLike(@PathVariable String userid, @PathVariable int hotplaceid) {
		Integer r = hs.isLike(userid, hotplaceid);
		return new ResponseEntity<Integer>(r, HttpStatus.OK);
	}

	@PostMapping("/{hotplaceid}/like/{userid}")
	public ResponseEntity<?> likePost(@PathVariable String userid, @PathVariable int hotplaceid) {
		int r = hs.likePost(userid, hotplaceid);
		hs.updateLike(hotplaceid, 1); // 좋아요 수 증가
		if (r == 0)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<Integer>(r, HttpStatus.OK);
	}

	@DeleteMapping("/{hotplaceid}/like/{userid}")
	public ResponseEntity<?> unlikePost(@PathVariable String userid, @PathVariable int hotplaceid) {
		int r = hs.unlikePost(userid, hotplaceid);
		hs.updateLike(hotplaceid, -1); // 좋아요 수 감소
		if (r == 0) return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<Integer>(r, HttpStatus.OK);
	}

	// ################ 등록 수정 삭제 ################

	@PostMapping()
	public ResponseEntity<?> registHotplace(@RequestParam("hotplaceDto") String hotplaceDtoJson,
			@RequestParam("images") MultipartFile[] images) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			HotplaceDto hotplaceDto = objectMapper.readValue(hotplaceDtoJson, HotplaceDto.class);

			// HotplaceDto와 이미지 파일들을 사용하여 등록 작업 수행
			int result = hs.registHotplace(hotplaceDto, images);

			if (result == 0) return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			else return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping()
	public ResponseEntity<?> modifyHotplace(@RequestBody HotplaceModifyDto hotplaceModifyDto) {

	    System.out.println("deleted");
	    System.out.println(hotplaceModifyDto);

	    int r = hs.modifyHotplace(hotplaceModifyDto);
	    if (r == 0) return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	    return new ResponseEntity<Integer>(r, HttpStatus.OK);
	}


	@DeleteMapping("/{hotplaceid}")
	public ResponseEntity<?> delete(@PathVariable int hotplaceid) {
		int r = hs.deleteHotplace(hotplaceid);
		if (r == 0) return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<Integer>(r, HttpStatus.OK);
	}

	@PostMapping("/{hotplaceid}/comments")
	public ResponseEntity<?> writeHComment(@PathVariable int hotplaceid, @RequestBody HCommentDto commentDto){
		commentDto.setHotplaceid(hotplaceid);
		commentDto.setWriter(js.getUserId());
		int r = hcs.writeComment(commentDto);

		if(r==0) return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	@DeleteMapping("comments/{commentid}")
	public ResponseEntity<?> deleteHComment(@PathVariable int commentid){
		int r = hcs.deleteComment(commentid);
		if(r==0) return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}



}
