package com.ssafy.enjoytrip.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlaceResponseDto {
    int placeid;
    String name;
    String address;
    double lat;
    double lng;
    int dayid;
}
