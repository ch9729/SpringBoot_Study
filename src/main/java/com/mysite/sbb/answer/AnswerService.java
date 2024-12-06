package com.mysite.sbb.answer;

import com.mysite.sbb.question.DataNotFoundException;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository aRepo;

    // 해당 질문에 답변을 저장하는 메소드(유저 추가)
    public void create(Question q, String content, SiteUser author) {
        Answer a = new Answer();
        a.setQuestion(q);
        a.setAuthor(author);
        a.setContent(content);
        a.setCreateDate(LocalDateTime.now());
        aRepo.save(a);
    }

    //답변 조회
    public Answer getAnswer(int id) {
        Optional<Answer> a = aRepo.findById(id);
        if(a.isPresent()) {
            return a.get();
        } else {
            throw new DataNotFoundException("답변을 찾을수 없음 : " + id);
        }
    }

    //수정
    public void modify(Answer a, String content) {
        a.setContent(content);
        a.setModifyDate(LocalDateTime.now());
        aRepo.save(a);
    }

    //삭제하기
    public void deleteAnswer(Answer answer) {
        aRepo.delete(answer);
    }

    //추천하기
    public void vote(Answer answer, SiteUser user) {
        answer.getVoter().add(user);
        aRepo.save(answer);
    }
}
