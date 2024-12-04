package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String create(@PathVariable int id, @Valid AnswerForm answerForm, BindingResult bindingResult, Model model) {
        // 질문을 가져온다.
        Question q = qService.getQuestion(id);
        // 답변을 저장하는 서비스
        if(bindingResult.hasErrors()) {
            model.addAttribute("q", q);
            return "question_detail";
        }
        aService.create(q, answerForm.getContent());
        // 답변을 저장한 후 다시 상세화면으로 전환
        return "redirect:/question/detail/" + id;
    }
}
