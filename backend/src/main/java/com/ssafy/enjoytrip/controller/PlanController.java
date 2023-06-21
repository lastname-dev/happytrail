package com.ssafy.enjoytrip.controller;

import com.ssafy.enjoytrip.model.dto.PlanDayDto;
import com.ssafy.enjoytrip.model.dto.PlanDto;
import com.ssafy.enjoytrip.model.dto.PlanSimpleDto;
import com.ssafy.enjoytrip.model.service.PlanService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/plans")
public class PlanController {

    @Autowired
    PlanService planService;
    @PostMapping
    public ResponseEntity<?> write(@RequestBody PlanDto planDto){
        planService.write(planDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @GetMapping("/user/{userid}")
    public ResponseEntity<?> showSimplePlan(@PathVariable String userid){
        List<PlanSimpleDto> plans = planService.selectSimplePlan(userid);
        return new ResponseEntity<List<PlanSimpleDto>>(plans,HttpStatus.ACCEPTED);
    }

    @GetMapping("/{planid}")
    public ResponseEntity<?> showDetailPlan(@PathVariable int planid){
        PlanDto plan = planService.selectPlan(planid);
        return new ResponseEntity<PlanDto>(plan,HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/{planid}")
	public ResponseEntity<?> deletePlan(@PathVariable int planid){
    	log.info(planid+" 글 삭제 요청");
    	planService.deletePlan(planid);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
    
    @PutMapping("/{planid}")
    public ResponseEntity<?> modifyPlan(@PathVariable int planid,@RequestBody PlanDto planDto ){
    	log.info(planid+" 글 수정 요청");
    	planService.modify(planDto);	
    	return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    
    @PostMapping("/{planid}/member/{memberid}")
    public ResponseEntity<?> inviteMember(@PathVariable int planid,@PathVariable String memberid){
    	planService.inviteMember(planid, memberid);
    	return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    
    @DeleteMapping("/{planid}/member/{memberid}")
    public ResponseEntity<?> deleteMember(@PathVariable int planid,@PathVariable String memberid){
    	planService.deleteMember(planid, memberid);
    	return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    
}
