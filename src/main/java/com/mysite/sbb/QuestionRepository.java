package com.mysite.sbb;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// JPA Repository 상속 <엔티티 클래스명, 기본키 타입>
// jsp 할때는 dao crud 메소드를 직접 만들었으나 JPA는 CRUD 자동완성
// 인터페이스 implement 클래스 즉 구현클래스를 자동으로 만들어짐
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    // 질문
    Question findBySubject(String subject);
    Question findBySubjectAndContent(String subject, String content);
    List<Question> findBySubjectContaining(String subject);

}
