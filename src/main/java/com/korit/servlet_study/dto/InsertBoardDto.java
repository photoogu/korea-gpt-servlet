package com.korit.servlet_study.dto;

import com.korit.servlet_study.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsertBoardDto {
    // JSON 의 key 값과 이름을 동일하게 해야함
    private String title;
    private String content;

    public Board toBoard() {
        return Board.builder()
                .title(title)
                .content(content)
                .build();
    }
}
