package com.mysite.sbb;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class Answertests {

    @Autowired
    private QuestionRepository qRepo;

    @Autowired
    private AnswerRepository aRepo;

    @Test
    public void input() {
        // 질문을 가져와서 그 질문에 해당 답변을 저장
        Optional<Question> oq = this.qRepo.findById(2); //id값이 2번
        Question q = oq.get();  //2번 질문 객체
        Answer a = new Answer();
        a.setQuestion(q);   //2번 질문에 답변
        a.setContent("잘 모르겠습니다.");
        a.setCreateDate(LocalDateTime.now());
        aRepo.save(a);
    }



    @Test
    public void 조회() {
        Optional<Answer> oa = this.aRepo.findById(1);
        Answer a = oa.get();
        System.out.println(a.getContent());
    }

    @Transactional
    @Test
    public void 질문해당답변() {
        Optional<Question> oq = this.qRepo.findById(2);
        Question q = oq.get();

        List<Answer> aList = q.getAnswerList();
        for (Answer answer : aList) {
            System.out.println(answer.getContent());

        }
    }
}
