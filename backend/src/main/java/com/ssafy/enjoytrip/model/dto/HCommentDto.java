package com.ssafy.enjoytrip.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HCommentDto {
    int commentid;
    String writer;
    String content;
    String writtendate;
    int hotplaceid;
}
