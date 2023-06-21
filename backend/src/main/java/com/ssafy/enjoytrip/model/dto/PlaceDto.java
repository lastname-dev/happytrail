package com.ssafy.enjoytrip.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlaceDto {
	int placeid;
    String address_name;
//    String category_group_code;
//    String category_group_name;
//    String category_name
//    String distance
//    String id
    String phone;
    String place_name;
    String place_url;
    String road_address_name;
    double x;
    double y;
    int dayid;
}
