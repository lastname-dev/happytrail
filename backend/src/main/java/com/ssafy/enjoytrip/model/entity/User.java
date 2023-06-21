package com.ssafy.enjoytrip.model.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
	String userid;
	String userpw;
	String nickname;
	String phone;
	String email;
	String birthday;
	String intro;
	String token;
	String img;
//  List<Plan> plans; 
//  List<Board> boards;
}
