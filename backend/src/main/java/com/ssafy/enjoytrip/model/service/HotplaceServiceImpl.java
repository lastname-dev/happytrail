package com.ssafy.enjoytrip.model.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.enjoytrip.model.dto.HotplaceDto;
import com.ssafy.enjoytrip.model.dto.HotplaceModifyDto;
import com.ssafy.enjoytrip.model.dto.ImageDto;
import com.ssafy.enjoytrip.model.entity.Hotplace;
import com.ssafy.enjoytrip.model.repository.HotplaceRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HotplaceServiceImpl implements HotplaceService {
	
	@Autowired 
	private HotplaceRepository repo;
	
	@Autowired
	private ResourceLoader resLoader;
	
	@Autowired
	private JwtService jwtService;


	@Override
	public List<HotplaceDto> selectAll() {
		return repo.selectAll();
	}

	@Override
	public List<HotplaceDto> selectUser(String userid) {
		return repo.selectUser(userid);
	}

	@Override
	public List<HotplaceDto> selectFollow(String follower) {
		return repo.selectFollow(follower);
	}
	
	@Override
	public List<HotplaceDto> selectLike(String userid) {
		return repo.selectLike(userid);
	}
	
	@Override
	public HotplaceDto selectDetail(String hotplaceid) {
		return repo.selectDetail(hotplaceid);
	}
	
	@Override
	public List<HotplaceDto> search(String keyword) {
		return repo.search(keyword);
	}
	
	@Override
	public List<HotplaceDto> searchFollow(String keyword) {
		return repo.searchFollow(keyword, jwtService.getUserId());
	}

	@Override
	public List<HotplaceDto> searchLike(String keyword) {
		return repo.searchLike(keyword, jwtService.getUserId());
	}
	
	@Override
	@Transactional
	public int deleteAllImages(int hotplaceid) {
		return repo.deleteAllImages(hotplaceid);
	}

	@Override
	@Transactional
	public int registImage(ImageDto image) {
		return repo.registImage(image);
	}

	@Override
	@Transactional
	public int deleteImages(ImageDto image) {
		return repo.deleteImages(image);
	}
	
	@Override
	@Transactional
	public int updateLike(int hotplaceid, int cnt) {
		return repo.updateLike(hotplaceid, cnt);
	}
	
	@Override
	@Transactional
	public int deleteAllLike(int hotplaceid) {
		return repo.deleteAllLike(hotplaceid);
	}
	
	@Override
	public int isLike(String userid, int hotplaceid) {
		return repo.isLike(userid, hotplaceid);
	}

	@Override
	@Transactional
	public int likePost(String userid, int hotplaceid) {
		return repo.likePost(userid, hotplaceid);
	}

	@Override
	@Transactional
	public int unlikePost(String userid, int hotplaceid) {
		return repo.unlikePost(userid, hotplaceid);
	}

	@Override
	@Transactional
	public int registHotplace(HotplaceDto hotplaceDto, MultipartFile[] images) {
		
		Hotplace hotplace = new Hotplace();
		hotplace.setTitle(hotplaceDto.getTitle());
		hotplace.setContent(hotplaceDto.getContent());
		hotplace.setWriter(hotplaceDto.getWriter());
		hotplace.setNickname(hotplaceDto.getNickname());
		
		if( hotplaceDto.getPlacename()!=null) {			
			hotplace.setPlacename(hotplaceDto.getPlacename());
			hotplace.setPlaceaddress(hotplaceDto.getPlaceaddress());
			hotplace.setLat(hotplaceDto.getLat());
			hotplace.setLng(hotplaceDto.getLng());
		}
		repo.registHotplace(hotplace);
		int hotplaceId =  hotplace.getHotplaceid(); // hotplaceid 값

	    for (MultipartFile image : images) {
	        if (image != null && !image.isEmpty()) {
	            try {
	                // 파일을 저장할 위치 지정
	                Resource res = resLoader.getResource("classpath:static/resources/upload");
	                String imageName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
	                String imagePath = res.getFile().getCanonicalPath() + "/" + imageName;
	                File destination = new File(imagePath);
	                image.transferTo(destination);

	                // images 테이블에 등록
	                repo.registImage(new ImageDto(hotplaceId, imageName));
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
		return hotplaceId;
	}

	@Override
	@Transactional
	public int modifyHotplace(HotplaceModifyDto hotplaceModifyDto) {
		
		int hotplaceid =  hotplaceModifyDto.getHotplaceid(); 
		
		Hotplace hotplace = new Hotplace();
		hotplace.setHotplaceid(hotplaceid);
		hotplace.setTitle(hotplaceModifyDto.getTitle());
		hotplace.setContent(hotplaceModifyDto.getContent());
		hotplace.setNickname(hotplaceModifyDto.getNickname());
		
		if(hotplaceModifyDto.getPlacename()!=null) {			
			hotplace.setPlacename(hotplaceModifyDto.getPlacename());
			hotplace.setPlaceaddress(hotplaceModifyDto.getPlaceaddress());
			hotplace.setLat(hotplaceModifyDto.getLat());
			hotplace.setLng(hotplaceModifyDto.getLng());
		}
		
		List<String> deleted = hotplaceModifyDto.getDeleted();
		if(deleted!=null) { // 이미지 delete
			for (String url : deleted) {
				if(repo.isUrl(url)!=0) repo.deleteUrl(url);
			}
		}
		return repo.modifyHotplace(hotplaceModifyDto); // hotplace 정보 수정 (title content, 지역 등)
	}

	@Override
	@Transactional
	public int deleteHotplace(int hotplaceid) {
		
		// 관련된 이미지, 좋아요, 댓글 모두 삭제 후
		repo.deleteAllImages(hotplaceid);
		repo.deleteAllLike(hotplaceid);
		return repo.deleteHotplace(hotplaceid);
	}
}
