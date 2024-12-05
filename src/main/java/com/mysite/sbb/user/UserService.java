package com.mysite.sbb.user;

import com.mysite.sbb.question.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder PasswordEncoder;

    public SiteUser create(String username, String email, String password) {
        SiteUser siteUser = new SiteUser();
        siteUser.setUsername(username);
        siteUser.setEmail(email);
        // 비밀번호 암호화
        siteUser.setPassword(PasswordEncoder.encode(password));
        //저장하기
        userRepo.save(siteUser);
        return siteUser;
    }

    // 유저name(id)로 유저 객체를 조회
    public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser= userRepo.findByUsername(username);
        if(siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("유저 정보가 없습니다.");
        }
    }
}
