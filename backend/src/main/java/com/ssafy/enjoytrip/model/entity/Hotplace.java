package com.ssafy.enjoytrip.model.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Hotplace {
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
}
