package com.ssafy.enjoytrip.model.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlanSimpleDto {
    int planid;
    String title;
    Date startdate;
    Date enddate;
}
