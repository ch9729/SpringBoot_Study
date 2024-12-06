package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    private QuestionService qService;

    @Autowired
    private AnswerService aService;

    @Autowired
    private UserService uService;

    //답변글쓰기 인증되지 않은 상태이면 요청이 안되게 접근이 불가
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String create(@PathVariable int id,
                         @Valid AnswerForm answerForm,
                         BindingResult bindingResult,
                         Model model,
                         Principal principal) {     //인증이 되면 User 정보가 Principal(username) 내 저장 되어 있다.
        // 질문을 가져온다.
        Question q = qService.getQuestion(id);
        // 답변을 저장하는 서비스
        SiteUser siteUser = uService.getUser(principal.getName());
        if(bindingResult.hasErrors()) {
            model.addAttribute("q", q);
            return "question_detail";
        }
        aService.create(q, answerForm.getContent(), siteUser);
        // 답변을 저장한 후 다시 상세화면으로 전환
        return "redirect:/question/detail/" + id;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modifyAnswer(@PathVariable int id,
                               Principal principal,
                               AnswerForm answerForm) {
        Answer a = aService.getAnswer(id);
        if(!a.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        answerForm.setContent(a.getContent());
        return "answer_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
        public String modifyAnswer(@Valid AnswerForm answerForm,
                                   BindingResult bindingResult,
                                   Principal principal,
                                   @PathVariable int id) {
        if(bindingResult.hasErrors()) {
            return "answer_form";
        }
        Answer a = aService.getAnswer(id);
        if(!a.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
        }
        aService.modify(a, answerForm.getContent());
        return "redirect:/question/detail/" + a.getQuestion().getId();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id,
                         Principal principal) {
        Answer a = aService.getAnswer(id);
        if(!a.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제권한이 없습니다.");
        }
        aService.deleteAnswer(a);
        return "redirect:/question/detail/" + a.getQuestion().getId();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String vote(@PathVariable int id,
                       Principal principal) {
        Answer a = aService.getAnswer(id);
        SiteUser siteUser = uService.getUser(principal.getName());
        aService.vote(a, siteUser);
        return "redirect:/question/detail/" + a.getQuestion().getId();
    }
}
