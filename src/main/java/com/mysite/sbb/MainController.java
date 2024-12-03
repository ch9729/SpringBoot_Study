package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @RequestMapping("/")
    public String root() {
        // 리다이렉트는 새로운 요청을 하는 것(질문 컨트롤러)
        return "redirect:/question/list";
    }
    
    @RequestMapping("/sbb")
    @ResponseBody
    public String index() {
        return "안녕하세요";
    }
}
