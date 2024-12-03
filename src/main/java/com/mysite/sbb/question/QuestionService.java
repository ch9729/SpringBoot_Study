package com.mysite.sbb.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository qRepo;

    //질문을 가져오는 메소드
    public List<Question> getList() {
        return qRepo.findAll();
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
}
