package com.mysite.sbb.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SiteUser {

    @Id //PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) //시퀀스 1씩 증가
    private Long id;

    @Column(unique = true)  //중복이 안된다.
    private String username;

    private String password;

    @Column(unique = true)  //중복이 안된다.
    private String email;
}
