package com.mysite.sbb.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {   //회원가입 시 폼에 매핑하는 개체

    @Size(min = 3, max=25, message = "최소 3글자 이상 25자 이하로 입력해야합니다.")
    @NotBlank(message = "id는 필수 입력사항입니다.")
    private String username;

    @NotBlank(message = "password는 필수 입력사항입니다.")
    private String password1;

    @NotBlank(message = "password 재확인은 필수 입력사항입니다.")
    private String password2;

    @NotBlank(message = "email은 필수 입력사항입니다.")
    @Email
    private String email;
}
