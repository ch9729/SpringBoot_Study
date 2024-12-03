package com.mysite.sbb.question;

import com.mysite.sbb.answer.Answer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity // JPA의 테이블과 같은 클래스
@Getter
@Setter
@ToString
public class Question {

    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 1씩 자동으로 증가
    private Integer id;

    @Column(length = 200)   //제목열은 200자까지
    private String subject;

    @Column(columnDefinition = "TEXT")  // "내용"처럼 글자 수를 제한할 수 없는 경우에 사용
    private String content;

    private LocalDateTime createDate;

    // 반대로 이 질문에 해당 답변들
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;
}
