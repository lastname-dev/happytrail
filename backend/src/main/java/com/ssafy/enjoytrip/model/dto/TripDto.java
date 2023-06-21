package com.ssafy.enjoytrip.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TripDto {
	int sido;
	int gugun;
	int contentType;
	String title;
	double latitude;
	double longitude;
	String first_image;
	String first_image2;
	String addr1;
	double distance;
	String tel;
}
