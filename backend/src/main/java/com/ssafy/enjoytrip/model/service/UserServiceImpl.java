package com.ssafy.enjoytrip.model.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.model.dto.UserDto;
import com.ssafy.enjoytrip.model.repository.UserRepository;
import com.ssafy.enjoytrip.util.SHA512;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDto login(UserDto userDto) throws Exception {
		if (userDto.getUserid() == null || userDto.getUserpw() == null)
			return null;
		String pw_encrypt= SHA512.SHA512(userDto.getUserpw(), userRepository.selectSalt(userDto.getUserid()));
		System.out.println(pw_encrypt);
		userDto.setUserpw(pw_encrypt);
		return sqlSession.getMapper(UserRepository.class).login(userDto);
	}

	@Override
	public UserDto userInfo(String userid) throws Exception {
		UserDto user = sqlSession.getMapper(UserRepository.class).userInfo(userid);
		user.setFollow(userRepository.followCount(userid));
		user.setFollower(userRepository.followerCount(userid));
		user.setPosts(userRepository.postCount(userid));
		return user;
	}

	@Override
	public void saveRefreshToken(String userid, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("token", refreshToken);
		sqlSession.getMapper(UserRepository.class).saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String userid) throws Exception {
		return sqlSession.getMapper(UserRepository.class).getRefreshToken(userid);
	}

	@Override
	public void deleRefreshToken(String userid) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("token", null);
		sqlSession.getMapper(UserRepository.class).deleteRefreshToken(map);
	}
	@Override
	public void join(UserDto userDto) throws Exception{
		sqlSession.getMapper(UserRepository.class).join(userDto);
	}

	@Override
	public void modify(UserDto userDto) throws Exception {
		sqlSession.getMapper(UserRepository.class).modify(userDto);
	}

	@Override
	public void delete(String userid) throws Exception {
		sqlSession.getMapper(UserRepository.class).delete(userid);
	}

	@Override
	public void follow(String followeeid,String followerid) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("followeeid", followeeid);
		map.put("followerid", followerid);
		sqlSession.getMapper(UserRepository.class).follow(followeeid,followerid);
	}

	@Override
	public void unfollow(String followeeid, String followerid) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("followeeid", followeeid);
		map.put("followerid", followerid);
		sqlSession.getMapper(UserRepository.class).unfollow(followeeid,followerid);
	}

	@Override
	public int checkFollow(String followeeid, String followerid) throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("followeeid", followeeid);
		map.put("followerid", followerid);
		return sqlSession.getMapper(UserRepository.class).checkFollow(followeeid,followerid);
	}

}
