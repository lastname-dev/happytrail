package com.ssafy.enjoytrip.model.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BComment {
	int commentid;
	String writer;
	String content;
	String writtendate;
	int boardid;
}
