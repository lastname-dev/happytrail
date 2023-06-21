package com.ssafy.enjoytrip.model.service;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.model.dto.UserDto;

import java.sql.SQLException;

public interface UserService {
	public UserDto login(UserDto userDto) throws Exception;
	public UserDto userInfo(String userid) throws Exception;
	public void saveRefreshToken(String userid, String refreshToken) throws Exception;
	public Object getRefreshToken(String userid) throws Exception;
	public void deleRefreshToken(String userid) throws Exception;
	public void join(UserDto userDto) throws Exception;
	public void modify(UserDto userDto) throws Exception;
	public void delete(String userid) throws Exception;
	public void follow(String followeeid,String followerid) throws Exception;
	public void unfollow(String followeeid,String followerid) throws Exception;
	public int checkFollow(String followeeid,String followerid) throws SQLException;
}
