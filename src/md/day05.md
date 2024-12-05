## Spring boot
> 1. 로그인
> 2. 로그아웃
> 3. 엔티티 변경 글쓴이 추가

---
### 로그인
- 게시판은 질문한 사람, 답변한 사람을 구별하는 로그인, 로그아웃 필수 기능
- SecurityConfig.java 수정
    ```java
    .formLogin((formLogin) -> formLogin
                            .loginPage("/user/login")
                            .defaultSuccessUrl("/"))
    ```
  - 로그인 페이지의  URL 주소를 `/user/login`으로 성공 시 디폴트 페이지는 `/`로 설정
- 컨트롤러 수정
    ```java
      @GetMapping("/login")
        public String login() {
            return "login_form";
        }
    ```
  - 랜더링 할수 있는 메서드 생성
- 템플릿 생성
    ```java
    <form th:action="@{/user/login}" method="post">
            <div th:if="${param.error}">
              <div class="alert alert-danger">사용자ID 또는 비밀번호를 확인해 주세요.</div>
            </div>
            <div class="mb-3">
              <label for="username" class="form-label">사용자ID</label>
              <input type="text" name="username" id="username" class="form-control" />
            </div>
            <div class="mb-3">
              <label for="password" class="form-label">비밀번호</label>
              <input type="password" name="password" id="password" class="form-control" />
            </div>
            <button type="submit" class="btn btn-primary">로그인</button>
          </form>
    ```
  - 로그인 실패시 로그인 페이지로 리다이렉트 된다.
  - error과 함께 전달
  - 로그인 실패시 파라미터로 error가 전달되는 것은 스프링 시큐리티의 규칙
    <img src="../md/images/image25.png" width="700px">

- 화면은 출력이 되지만 무엇을 기준으로 로그인 할지에 대한 설정이 없다
- 데이터베이스에서 사용자를 조회하는 서비스를 만든다.
- UserRepository 추가
  ```java
  public interface UserRepository extends JpaRepository<SiteUser, Long> {
      Optional<SiteUser> findByusername(String username);
  }
  ```
  
- UserRole 추가
  - 시큐리티는 인증 뿐 아니라 권한도 관리
  - 인증 후 사용자에게 부여할 권한이 필요
  - ADMIN, USER 권한을 갖는 열거형 생성
  ```java
  @Getter
  public enum UserRole {
      ADMIN("ROLE_ADMIN"),
      USER("ROLE_USER");
  
      UserRole(String value) {
          this.value = value;
      }
  
      private String value;
  }
  ```
- 시큐리티 서비스 추가
  ```java
  @Service
  public class UserSecurityService implements UserDetailsService {
  
      @Autowired
      private UserRepository userRepo;
  
      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          Optional<SiteUser> _siteUser = this.userRepo.findByusername(username);
          if (_siteUser.isEmpty()) {
              throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
          }
          SiteUser siteUser = _siteUser.get();
          
          List<GrantedAuthority> authorities = new ArrayList<>();
          if ("admin".equals(username)) {
              authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
          } else {
              authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
          }
          return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
      }
          
  }
  ```
  - `UserDetailsService`는 `loadUserBuUsername`메서드를 구현하도록 강제하는 인터페이스이다.
  - 사용자명으로 비밀번호를 조회하여 리턴하는 메시지
  - 사용자명으로 `SiteUser`객체를 조회하는 메서드
  - 사용자명이 `admin`인 경우 ADMIN 권한을 부여하고 그 외 경우는 USER 권한을 부여
- SecurityConfig 빈 추가
```java
  @Bean
      public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
          return authenticationConfiguration.getAuthenticationManager();
      }
  ```
  - 스프링 시큐리티 인증을 담당
  - 생성시 **스프링의 내부 동작**으로 작성한 `**UserSecurityService**`,`**PasswordEncoder**`가 자동으로 설정
- navbar 수정
  ```java
   <li class="nav-item">
                      <a class="nav-link" th:href="@{/user/login}">로그인</a>
   </li>
  ```
  - 로그인 링크에 로그인 페이지로 이동하도록 추가
    <img src="../md/images/image26.png" width="700px">

  - 데이터베이스에 없는 입력을 찾을시 오류 발생
    <img src="../md/images/image27.png" width="700px">

---
### 로그아웃
- 이전 까지는 네비게이션 바는 로그인이란 버튼이 항상 남아있었다
- 사용자 로그인 여부에 따라 로그아웃으로 변경되도록 수정
  ```java
  <li class="nav-item">
                <a class="nav-link" sec:authorize="isAnonymous()" th:href="@{/user/login}">로그인</a>
                <a class="nav-link" sec:authorize="isAuthenticated()" th:href="@{/user/logout}">로그아웃</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" th:href="@{/user/signup}">회원가입</a>
              </li>
  ```
  - sec:authorize="isAnonymous()" - 이 속성은 로그인 되지 않은 경우에만 해당 엘리먼트가 표시되게 한다.
  - sec:authorize="isAuthenticated()" - 이 속성은 로그인 된 경우에만 해당 엘리먼트가 표시되게 한다.
  - 따라서 다음과 같이 내비게이션바를 수정할수 있다.
    <img src="../md/images/image28.png" width="700px">

- 로그아웃 구현
- SecurityConfig 수정
  ```java
  .logout((logout) -> logout
                  .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                  .logoutSuccessUrl("/")
                  .invalidateHttpSession(true))
  ```
  - 로그아웃 URL을 `/user/logout`으로 설정하여 성공시 `/`페이지로 이동
  - 이후 로그아웃 시 생성된 세션도 삭제 처리

---
### 엔티티 변경 글쓴이 추가
- Question 클래스에 author(글쓴이) 속성 추가
  ```java
   @ManyToOne
      private SiteUser author;
  ```
  - 여러개의 질문이 한 명의 사용자에게 작성될수 있도록 `@ManyToOne`관계 성립
- Answer 클래스에 author(글쓴이) 속성 추가
  ```java
   @ManyToOne
      private SiteUser author;
  ```
- 테이블 확인
  <img src="../md/images/image29.png" width="700px">

