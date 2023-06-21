package com.ssafy.enjoytrip.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.enjoytrip.model.dto.HotplaceDto;
import com.ssafy.enjoytrip.model.dto.HotplaceModifyDto;
import com.ssafy.enjoytrip.model.dto.ImageDto;

public interface HotplaceService {
	List<HotplaceDto> selectAll(); // hotplaces
	List<HotplaceDto> selectUser(String userid); // /hotplaces/user/{userid}
	List<HotplaceDto> selectFollow(String follower); // /hotplaces/follow/{userid}
	List<HotplaceDto> selectLike(String userid); // /hotplaces/like/{userid}
	HotplaceDto selectDetail(String hotplaceid); // /hotplaces/{hotplaceid}
	
	List<HotplaceDto> search(String keyword); // /hotplaces/search/{keyword}
	List<HotplaceDto> searchFollow(String keyword); // /hotplaces/search/follow/{keyword}
	List<HotplaceDto> searchLike(String keyword); // /hotplaces/search/like/{keyword}
	
	int deleteAllImages(int hotplaceid);
	int registImage(ImageDto image);
	int deleteImages(ImageDto image);
	
	int deleteAllLike(int hotplaceid);
	int updateLike(int hotplaceid, int cnt);
	int isLike(String userid, int hotplaceid);
	int likePost(String userid, int hotplaceid);
	int unlikePost(String userid, int hotplaceid);
	
	int registHotplace(HotplaceDto hotplaceDto, MultipartFile[] images); // /hotplaces
	int modifyHotplace(HotplaceModifyDto hotplaceModifyDto); // /hotplaces/{hotplaceid}
	int deleteHotplace(int hotplaceid); // /hotplaces/{hotplaceid}
}
