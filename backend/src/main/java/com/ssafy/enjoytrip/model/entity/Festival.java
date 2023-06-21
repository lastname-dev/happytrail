package com.ssafy.enjoytrip.model.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Festival {
	int festivalid;
	String title;
	String img;
	String address;
	String place;
	String desc;
	String startdate;
	String enddate;
}
