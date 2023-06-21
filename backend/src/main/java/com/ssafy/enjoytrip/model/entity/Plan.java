package com.ssafy.enjoytrip.model.entity;

import com.ssafy.enjoytrip.model.dto.PlanDayDto;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Plan {
    int id;
    String title;
    String writer;
    Date startDate;
    Date endDate;
    List<PlanDayDto> days;
    List<String> members;
}
