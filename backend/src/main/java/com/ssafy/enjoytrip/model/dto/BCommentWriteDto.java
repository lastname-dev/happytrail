package com.ssafy.enjoytrip.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BCommentWriteDto {
    String content;
    String writer;
    int boardid;
}
