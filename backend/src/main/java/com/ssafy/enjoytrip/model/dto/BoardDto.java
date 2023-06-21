package com.ssafy.enjoytrip.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardDto {
	int boardid;
	String title;
	String content;
	String writtendate;
	int view;
	String writer;
	String nickname;
}
