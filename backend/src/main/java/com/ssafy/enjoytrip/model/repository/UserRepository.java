package com.ssafy.enjoytrip.model.repository;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.model.dto.UserDto;

@Mapper
public interface UserRepository {
	public UserDto login(UserDto userDto) throws SQLException;
	public UserDto userInfo(String userid) throws SQLException;
	public void saveRefreshToken(Map<String, String> map) throws SQLException;
	public Object getRefreshToken(String userid) throws SQLException;
	public void deleteRefreshToken(Map<String, String> map) throws SQLException;
	public void join(UserDto userDto) throws SQLException;
	public void modify(UserDto userDto) throws SQLException;
	public void delete(String userid) throws SQLException;
	public void follow(String followeeid,String followerid) throws SQLException;
	public void unfollow(String followeeid,String followerid) throws SQLException;
	public int checkFollow(String followeeid,String followerid) throws SQLException;
	public int followCount(String userid);
	public int followerCount(String userid);
	public int postCount(String userid);
	public String selectSalt(String userid);
}
