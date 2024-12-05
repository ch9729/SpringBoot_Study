package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Answer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    
    @Column(columnDefinition = "TEXT")
    private String  content;
    
    private LocalDateTime createDate;

    //수정시간
    private LocalDateTime modifyDate;

    @ManyToOne
    private SiteUser author;

    // 질문엔티티 참조 (외래키)
    @ManyToOne
    private Question question;
}
