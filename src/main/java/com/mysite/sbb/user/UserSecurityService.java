package com.mysite.sbb.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserSecurityService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 시큐리티의 인증을 하기 위해서 메서드를 완성한다.
        // 이때 유저를 DB검색하여 유저객체와 유저권한을 함께 UserDetails로 리턴
        Optional<SiteUser> _siteUser = userRepo.findByUsername(username);
        //만약 이름으로 찾았을경우 없을때는 오류 메시지
        if(_siteUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        //이름으로 찾았을경우 있을때
        SiteUser siteUser = _siteUser.get();    //유저객체를 찾음
        
        //유저의 권한을 임의로 설정(이름이 "admin"이면 관리자 아니면 다 일반유저)
        List<GrantedAuthority> authorities = new ArrayList<>(); //시큐리티 권한 타입 리스트
        if("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getRole()));
        }else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getRole()));
            }// 유저 권한 완료
        // 유저네임, 패스워드, 권한리스트로 유저객체를 만들어 리턴
        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
    }
}
