package com.mysite.sbb.question;

import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RequestMapping("/question")
@Controller
public class QuestionController {

    @Autowired
    private QuestionService qService;

    @Autowired
    private UserService userService;

    @RequestMapping("/list")
    public String list(Model model,
                       @RequestParam(value = "page", defaultValue = "0")int page) {

        Page<Question> paging = qService.getList(page);
        model.addAttribute("paging", paging);
        return "question_list";
    }

    @RequestMapping("/detail/{id}")
    public String detail(@PathVariable int id,
                         Model model,
                         AnswerForm answerForm) {
        Question q = qService.getQuestion(id);
        model.addAttribute("q", q);
        return "question_detail";
    }
    
    //질문 글 작성시 인증되지 않은 상태이면 요청이 안되게 접근이 불가
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String create(QuestionForm questionForm) {
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String qCreate(@Valid QuestionForm questionForm,
                          BindingResult bindingResult,
                          Principal principal) {
        SiteUser siteUser = userService.getUser(principal.getName());

        //@Valid로 해당 객체를 검사한다. 결과는 두번째 나오는 바인딩리절트에 저장됨
        if(bindingResult.hasErrors()) {
            return "question_form"; //되돌아감
        }
        qService.createQuestion(questionForm.getSubject(), questionForm.getContent(), siteUser);
        return "redirect:/question/list";
    }

    //수정 : 인증된 유저
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modify(@PathVariable int id,
                         Principal principal,
                         QuestionForm questionForm) {
        Question q = qService.getQuestion(id);
        if(!q.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
        }
        questionForm.setSubject(q.getSubject());
        questionForm.setContent(q.getContent());
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modify(@Valid QuestionForm questionForm,
                         BindingResult bindingResult,
                         Principal principal,
                         @PathVariable int id) {
        if(bindingResult.hasErrors()) {
            return "question_form"; //되돌아간다.
        }
        Question q = qService.getQuestion(id);
        if(!q.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
        }
        qService.modify(q, questionForm.getSubject(), questionForm.getContent());
        return "redirect:/question/detail/" + id;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id,
                         Principal principal) {
        Question q = qService.getQuestion(id);
        if(!q.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제권한이 없습니다.");
        }
        qService.deleteQuestion(q);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String vote(@PathVariable int id,
                       Principal principal) {
        Question question = qService.getQuestion(id);
        SiteUser siteUser = userService.getUser(principal.getName());

        qService.vote(question, siteUser);
        return "redirect:/question/detail/" + id;
    }
}
