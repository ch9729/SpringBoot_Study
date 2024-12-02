package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //Servlet 즉 컨트롤러 역할
public class HelloController {
    
    @RequestMapping("/hello")   //localhost8080/hello
    @ResponseBody   // 기본 MVC는 컨트롤러가 화면을 전달하는데 여기서는 바로 표시
    public String hello() {
        return "Hello World";
    }

    @RequestMapping("/hi")
    @ResponseBody
    public String hi() {
        return "안녕!";
    }

    @RequestMapping("/hi4")
    @ResponseBody
    public String hi4() {
        return "안녕하세요4!";
    }
}
