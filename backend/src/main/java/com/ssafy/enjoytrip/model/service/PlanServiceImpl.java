package com.ssafy.enjoytrip.model.service;

import com.ssafy.enjoytrip.model.dto.*;
import com.ssafy.enjoytrip.model.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanServiceImpl implements PlanService{

    @Autowired
    PlanRepository planRepository;
    @Autowired
    JwtService jwtService;
    @Override
    public void write(PlanDto planDto) {
        planDto.setWriter(jwtService.getUserId());
        planRepository.insertPlan(planDto);
        int planid = planDto.getId();
        List<PlanDayDto> planDayDtos = planDto.getDays();
        for(PlanDayDto planDayDto : planDayDtos){
           planDayDto.setPlanid(planid);
           planRepository.insertDay(planDayDto);
           int dayid = planDayDto.getId();
           PlaceDto placeDto = planDayDto.getPlaces();
           placeDto.setDayid(dayid);
           planRepository.insertPlace(placeDto);
        }
    }

    @Override
    public PlanDto selectPlan(int planid) {
        PlanDto plan =planRepository.selectPlan(planid);
        List<PlanDayDto> days = planRepository.selectDay(planid);
        for(PlanDayDto day : days){
            PlaceResponseDto placeDto = planRepository.selectPlace(day.getDayid());
            PlaceDto place = new PlaceDto();
            place.setDayid(placeDto.getDayid());
            place.setPlace_name(placeDto.getName());
            place.setAddress_name(placeDto.getAddress());
            place.setX(placeDto.getLat());
            place.setY(placeDto.getLng());
            place.setPlaceid(placeDto.getPlaceid());
            day.setPlaces(place);
        }
        plan.setDays(days);
        plan.setMembers(planRepository.selectMembers(planid));
        return plan;
    }

    @Override
    public List<PlanSimpleDto> selectSimplePlan(String userid){
        return planRepository.selectSimplePlan(userid);
    }

	@Override
	public void inviteMember(int planid, String memberid) {
		planRepository.inviteMember(planid,memberid);
	}

	@Override
	public void deleteMember(int planid, String memberid) {
		planRepository.deleteMember(planid,memberid);
	}

	@Override
	public void deletePlan(int planid) {
		List<PlanDayDto> days= planRepository.selectDay(planid);
		for(PlanDayDto day: days) {
			System.out.println("dayid "+day.getDayid());
			planRepository.deletePlace(day.getDayid());
		}
		planRepository.deleteDay(planid);
		planRepository.deletePlan(planid);
		planRepository.deleteShare(planid);
	}

	@Override
	public void modify(PlanDto planDto) {
		  planDto.setWriter(jwtService.getUserId());
	        planRepository.updatePlan(planDto);
	        List<PlanDayDto> planDayDtos = planDto.getDays();
	        int day = planRepository.selectDay(planDto.getId()).size();
	        
	        //todo: 날짜 개수 다를때?????????????????
	        if(planDayDtos.size()>day) {
	        	
	        }
	        else if(planDayDtos.size()==day) {
	        	for(PlanDayDto planDayDto : planDayDtos){
	 	           planRepository.updateDay(planDayDto);
	 	           int dayid = planDayDto.getDayid();
	 	           PlaceDto placeDto = planDayDto.getPlaces();
	 	           placeDto.setDayid(dayid);
	 	           planRepository.updatePlace(placeDto);
	 	        }
	        }else {
	        	
	        }	        
	        
	}

}
