package com.mysite.sbb.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository qRepo;

    //질문을 가져오는 메소드(페이지 적용)
    public Page<Question> getList(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("createDate").descending());
        return qRepo.findAll(pageable);
    }
    
    // 아이디로 질문 가져오기
    public Question getQuestion(int id) {
        Optional<Question> q = qRepo.findById(id);
        if(q.isPresent()) {
            return q.get();
        }else {
            // id로 질문을 찾지 못했을 경우 예외 발생
            throw new DataNotFoundException("question not found");
        }
    }

    // 새 질문을 저장
    public void createQuestion(String subject, String content) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        qRepo.save(q);
    }


}
