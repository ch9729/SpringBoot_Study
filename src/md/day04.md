## Spring boot
> 1. 스프링 시큐리티
> 2. 회원가입1
> 3. 회원가입2

---
###스프링 시큐리티
- 스프링 기반 애플리케이션의 인증과 구너한을 담당하는 스프링의 하위 프레임워크
- 인증(Authenticate)은 로그인을 의미한다.
- 권한(Authorize)은 인증된 사용자가 어떤 것을 할 수 있는지를 의미한다. 
- 스프링 시큐리티 디펜던시 라이브러리 설치
  - pom.xml > dependencies에 `Spring Security` 추가
- 설치 이후에 화면에 접속하면 로그인 화면이 구현
<img src="../md/images/image19.png" width="700px">
  
  - 기본적으로 인증되지 않은 사용자는 서비스를 사용할수 없게 된다.
-시큐리티 설정
  - 로그인없이 조회할 수 있도록 SecurityConfig.java 파일 작성
  ```java
  @Configuration
  @EnableWebSecurity
  public class SecurityConfig {
  
      @Bean
      SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
          http
                  .authorizeHttpRequests(
                          authorizeRequests -> authorizeRequests
                                  .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
                  .csrf((csrf) -> csrf
                          .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
                  .headers((headers) -> headers
                          .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                  XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
          ;
          return http.build();
      }
  }
  ```
  - `@Configuration`은 스프링의 환결설정 파일임을 의미하는 어너테이션
  - `@EnableWebSecurity` 모든 요청 URL이 스프링 시큐리티의 제어를 받오록 만드는 어노테이션