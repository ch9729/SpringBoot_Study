package com.mysite.sbb.question;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/question")
@Controller
public class QuestionController {

    @Autowired
    private QuestionService qService;

    @RequestMapping("/list")
    public String list(Model model) {
        List<Question> qList = qService.getList();
        model.addAttribute("qList", qList);
        return "question_list";
    }

    @RequestMapping("/detail/{id}")
    public String detail(@PathVariable int id, Model model) {
        Question q = qService.getQuestion(id);
        model.addAttribute("q", q);
        return "question_detail";
    }

    @GetMapping("/create")
    public String create(QuestionForm questionForm) {
        return "question_form";
    }

    @PostMapping("/create")
    public String qCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
        //@Valid로 해당 객체를 검사한다. 결과는 두번째 나오는 바인딩리절트에 저장됨
        if(bindingResult.hasErrors()) {
            return "question_form"; //되돌아감
        }
        return "redirect:/question/list";
    }

}
