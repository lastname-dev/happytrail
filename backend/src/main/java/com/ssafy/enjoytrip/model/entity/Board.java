package com.ssafy.enjoytrip.model.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Board {
	int boardid;
	String title;
	String content;
	String writtendate;
	int view=0;
	String writer;
	String nickname;
	List<BComment> comments;
}
