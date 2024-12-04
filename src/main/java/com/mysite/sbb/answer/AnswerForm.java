package com.mysite.sbb.answer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerForm {

    @NotBlank(message = "내용은 필수입니다.")
    @Size(min= 3, message = "3자 이상이 필수입니다.")
    private String content;

}
