package com.mysite.sbb.question;

import com.mysite.sbb.user.SiteUser;
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

    // 새 질문을 저장(글쓴이 추가)
    public void createQuestion(String subject, String content, SiteUser siteUser) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(siteUser);
        qRepo.save(q);
    }
    
    //수정하기
    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());    //수정시간 업데이트
        qRepo.save(question);
    }

    //삭제하기
    public void deleteQuestion(Question question) {
        qRepo.delete(question);
    }

    //추천하기
    public void vote(Question question, SiteUser siteUser) {
        question.getVoter().add(siteUser);
        qRepo.save(question);
    }
}
