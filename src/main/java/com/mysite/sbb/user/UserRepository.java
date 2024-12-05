package com.mysite.sbb.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
    // 기본 CRUD 메소드 가능 id로 조회 가능
    // 그러나 id가 아닌것으로 조회할 경우 직접 메소드를 구현한다.

    Optional<SiteUser> findByUsername(String username);
}
