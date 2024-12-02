package com.mysite.sbb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter //lombok 설치 시 자동으로 가능
@Setter
@RequiredArgsConstructor    //기본생성자
@AllArgsConstructor // 모든 필드 생성자
public class HelloLombok {
    private String hello;
    private int lombok;

    public static void main(String[] args) {
        HelloLombok hello = new HelloLombok();  //10행
        HelloLombok hello2 = new HelloLombok("헬로",456); //11행
        hello.setHello("Hello");
        hello.setLombok(123);
        System.out.println(hello.getHello());
        System.out.println(hello.getLombok());
        System.out.println(hello2.getHello() + hello2.getLombok());
    }
}
