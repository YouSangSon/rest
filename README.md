## Java와 Spring boot 공부 겸 개인 서버입니다.

### 1. Client에서는 요청만 하고 spring boot에서 oauth2를 이용해 소셜로그인 처리

구현 방법:

1. Spring Boot 설정:  
   1. Spring Boot Security OAuth2를 설정합니다.  
   2. 지원할 소셜 로그인 제공자 (예: Google, Facebook, Kakao)를 등록합니다.  
   3. 로그인 성공 후 처리할 핸들러를 작성합니다.  

2. 플러터 앱:  
   1. 소셜 로그인 제공자를 선택하는 UI를 구현합니다.  
   2. 선택된 제공자에 따라 OAuth2 인증 요청을 수행합니다.  
   3. 인증 성공 후 받은 코드를 Spring Boot 서버로 전송합니다.  
  
3. Spring Boot 서버:  
   1. 플러터 앱에서 전송된 코드를 사용하여 Access Token 및 Refresh Token을 발급합니다.  
   2. Access Token을 사용하여 사용자 정보를 가져옵니다.  
   3. 로그인 성공 여부를 플러터 앱에 응답합니다.  