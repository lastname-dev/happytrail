package com.ssafy.enjoytrip.model.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlanDayDto {
    int cost;
    int number;
    int id;
    int planid;
    int dayid;
    int day;
    String content;
    PlaceDto places;
}
