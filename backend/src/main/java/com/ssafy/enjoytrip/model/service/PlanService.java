package com.ssafy.enjoytrip.model.service;

import com.ssafy.enjoytrip.model.dto.PlaceDto;
import com.ssafy.enjoytrip.model.dto.PlanDayDto;
import com.ssafy.enjoytrip.model.dto.PlanDto;
import com.ssafy.enjoytrip.model.dto.PlanSimpleDto;

import java.util.List;

public interface PlanService {
    public void write(PlanDto planDto);
    public PlanDto selectPlan(int planid);
    public List<PlanSimpleDto> selectSimplePlan(String userid);
    public void inviteMember(int planid, String memberid);
    public void deleteMember(int planid, String memberid);
    public void deletePlan(int planid);
    public void modify(PlanDto planDto);
    
}
