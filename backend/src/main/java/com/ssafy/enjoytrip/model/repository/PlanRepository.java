package com.ssafy.enjoytrip.model.repository;

import com.ssafy.enjoytrip.model.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlanRepository {
    public int insertPlan(PlanDto planDto);
    public int insertPlace(PlaceDto placeDto);
    public int insertDay(PlanDayDto planDayDto);
    public PlanDto selectPlan(int planid);
    public List<PlanDayDto> selectDay(int planid);
    public PlaceResponseDto selectPlace(int dayid);
    public List<PlanSimpleDto> selectSimplePlan(String userid);
    public void inviteMember(int planid, String memberid);
    public void deleteMember(int planid, String memberid);
    public void deletePlan(int planid);
    public void deleteShare(int planid);
    public void deletePlace(int dayid);
    public void deleteDay(int planid);
    public void updatePlan(PlanDto planDto);
    public void updateDay(PlanDayDto planDayDto);
    public void updatePlace(PlaceDto placeDto);
    public List<String> selectMembers(int planid);
}
