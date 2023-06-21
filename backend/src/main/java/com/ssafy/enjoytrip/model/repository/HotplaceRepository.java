package com.ssafy.enjoytrip.model.repository;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ssafy.enjoytrip.model.dto.HotplaceDto;
import com.ssafy.enjoytrip.model.dto.HotplaceModifyDto;
import com.ssafy.enjoytrip.model.dto.ImageDto;
import com.ssafy.enjoytrip.model.entity.Hotplace;

@Mapper
public interface HotplaceRepository {
	List<HotplaceDto> selectAll(); // hotplaces
	List<HotplaceDto> selectUser(String userid); // /hotplaces/user/{userid}
	List<HotplaceDto> selectFollow(String follower); // /hotplaces/follow/{userid}
	List<HotplaceDto> selectLike(String userid); // /hotplaces/like/{userid}
	HotplaceDto selectDetail(String hotplaceid); // /hotplaces/{hotplaceid}
	
	List<HotplaceDto> search(String keyword); // /hotplaces/search/{keyword}
	List<HotplaceDto> searchFollow(String keyword, String follower); // /hotplaces/search/follow/{keyword}
	List<HotplaceDto> searchLike(String keyword, String userid); // /hotplaces/search/like/{keyword}
	
	int deleteAllImages(int hotplaceid);
	int registImage(ImageDto image);
	int deleteImages(ImageDto image);
	
	int isUrl(String url);
	int deleteUrl(String url);
	
	int deleteAllLike(int hotplaceid);
	int updateLike(int hotplaceid, int cnt);
	int isLike(String userid, int hotplaceid);
	int likePost(String userid, int hotplaceid);
	int unlikePost(String userid, int hotplaceid);
	
	int registHotplace(Hotplace hotplace); // /hotplaces
	int modifyHotplace(HotplaceModifyDto hotplaceModifyDto); // /hotplaces/{hotplaceid}
	int deleteHotplace(int hotplaceid); // /hotplaces/{hotplaceid}
}
