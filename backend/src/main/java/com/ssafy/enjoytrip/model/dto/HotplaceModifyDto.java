package com.ssafy.enjoytrip.model.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HotplaceModifyDto {
	int hotplaceid;
	String title;
	String content;
	String nickname; // 현재 로그인 한 사용자의 nickname으로 update (nickname 수정했을 수 있음)
	String placename;
	String placeaddress;
	double lat;
	double lng;
	String urls;
	List<String> deleted;
}
