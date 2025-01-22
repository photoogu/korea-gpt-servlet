package com.korit.servlet_study.dto;

import com.korit.servlet_study.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupDto {
    private String username;
    private String password;
    private String name;
    private String email;

    public User toUser() {
        return User.builder()
                .username(username)
                // BCrypt: 비밀번호 암호화
                // gensalt() -> 안의 숫자가 클 수록 암호화의 복잡도가 커짐. 너무 크면 암호화에 시간이 오래 걸리는 등의 문제가 발생
                .password(BCrypt.hashpw(password, BCrypt.gensalt(10)))
                .name(name)
                .email(email)
                .build();
    }
}
