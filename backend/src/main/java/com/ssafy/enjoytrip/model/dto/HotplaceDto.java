package com.ssafy.enjoytrip.model.dto;

import java.util.List;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HotplaceDto { // select , detail 시 사용
	
	int hotplaceid;
	String title;
	int likecnt;
	int commentcnt;
	String content;
	String writtendate;
	String writer;
	String nickname;
	String placename;
	String placeaddress;
	double lat;
	double lng;
	String urls;
	List<HCommentDto> comments;
}
