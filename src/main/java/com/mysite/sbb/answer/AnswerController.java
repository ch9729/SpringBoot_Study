package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    private QuestionService qService;

    @Autowired
    private AnswerService aService;

    @PostMapping("/create/{id}")
    public String create(@PathVariable int id, @RequestParam String content) {
        // 질문을 가져온다.
        Question q = qService.getQuestion(id);
        // 답변을 저장하는 서비스
        aService.create(q, content);
        // 답변을 저장한 후 다시 상세화면으로 전환
        return "redirect:/question/detail/" + id;
    }
}
