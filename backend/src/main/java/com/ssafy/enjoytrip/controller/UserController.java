package com.ssafy.enjoytrip.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.enjoytrip.model.dto.UserDto;
import com.ssafy.enjoytrip.model.service.JwtServiceImpl;
import com.ssafy.enjoytrip.model.service.UserService;
import com.ssafy.enjoytrip.util.SHA512;

@RestController
@RequestMapping("/users")
public class UserController {

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private JwtServiceImpl jwtService;

	@Autowired
	private UserService userService;

	@PostMapping("/follow/{userid}")
	public ResponseEntity<?> follow(@PathVariable("userid") String userid){
		String username=jwtService.getUserId();
			try{
				logger.info(username+"가"+userid+"을 팔로우 시도");
				if(userService.checkFollow(userid,username)>0){
					logger.info("이미 팔로우 한 사람입니다."+username +"->" + userid);
					userService.unfollow(userid,username);
				}else{
					logger.info("팔로우 하지 않은 사람입니다 팔로우 합니다."+username +"->" + userid);
					userService.follow(userid,username);
				}
			}catch (Exception e){
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	@PostMapping()
	public ResponseEntity<?> join(@RequestBody UserDto userDto) {
		System.out.println(userDto);
		String salt = SHA512.Salt();
		userDto.setSalt(salt);
		String pw_encrypt = SHA512.SHA512(userDto.getUserpw(), salt);
		userDto.setUserpw(pw_encrypt);
		HttpStatus status = null;
		Map<String, Object> resultMap = new HashMap<>();
		try {
			userService.join(userDto);
			status = HttpStatus.ACCEPTED;
		}catch(Exception e) {
			logger.error("회원가입 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
	@DeleteMapping("/{userid}")
	public ResponseEntity<?> quit(@PathVariable("userid") String userid) throws Exception{
		try{
			userService.delete(userid);
		}catch(Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{userid}")
	public ResponseEntity<?> modify(@RequestBody UserDto userDto,@PathVariable("userid") String userid){
		try{
			userDto.setUserid(userid);
			System.out.println(userDto);
			userService.modify(userDto);
		}catch(Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody UserDto userDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {	
			UserDto loginUser = userService.login(userDto);
			if (loginUser != null && !loginUser.isDeleted()) {
				String accessToken = jwtService.createAccessToken("userid", loginUser.getUserid());// key, data
				String refreshToken = jwtService.createRefreshToken("userid", loginUser.getUserid());// key, data
				userService.saveRefreshToken(userDto.getUserid(), refreshToken);
				logger.info("로그인 accessToken 정보 : {}", accessToken);
				logger.info("로그인 refreshToken 정보 : {}", refreshToken);
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else if(loginUser.isDeleted()){
				resultMap.put("message","deleted");
				status = HttpStatus.UNAUTHORIZED;
			}
			else {
				resultMap.put("message", FAIL);
				status = HttpStatus.UNAUTHORIZED;
			}
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());	
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/info/{userid}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("userid") String userid,
			HttpServletRequest request) {
//		logger.debug("userid : {} ", userid);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		if (jwtService.checkToken(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				UserDto userDto = userService.userInfo(userid);
				boolean followState = userService.checkFollow(userid,jwtService.getUserId())>0? false : true;
				resultMap.put("userInfo", userDto);	
				resultMap.put("message", SUCCESS);
				resultMap.put("followstate",followState);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/logout/{userid}")
	public ResponseEntity<?> removeToken(@PathVariable("userid") String userid, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {

			userService.deleRefreshToken(userid);
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}

	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody UserDto userDto, HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refresh-token");
		logger.debug("token : {}, memberDto : {}", token, userDto);
		if (jwtService.checkToken(token)) {
			if (token.equals(userService.getRefreshToken(userDto.getUserid()))) {
				String accessToken = jwtService.createAccessToken("userid", userDto.getUserid());
				logger.debug("token : {}", accessToken);
				logger.debug("정상적으로 액세스토큰 재발급!!!");
				resultMap.put("access-token", accessToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			}
		} else {
			logger.debug("리프레쉬토큰도 사용불!!!!!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	

}
