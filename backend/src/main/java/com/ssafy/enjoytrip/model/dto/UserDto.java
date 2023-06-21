package com.ssafy.enjoytrip.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {
	String userid;
	String userpw;
	String nickname;
	String phone;
	String email;
	Date birthday;
	String intro;
	String token;
	int follow;
	int follower;
	int posts;
	String salt;
	boolean deleted;
}
