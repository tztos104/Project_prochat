# **프로젝트 소개**

## **서비스 기능**
<details>

마이크로 서비스(MSA)로 3개의 서비스로 분리
-API Gateway
  -해당 API로 들어온 요청을 내부의 마이크로 서비스로 전달
-회복탄력성
  -Java Resilience 4j를 이용한 Circuit Breaker 회복탄력성 구축.

- User-service 
  - 가입
  - 아이디 및 닉네임 중복체크
  - 비밀번호 암호화, 업데이트
  - jwt 토큰를 활용한 로그인, 로그아웃
  - 프로필 관리
- Activity-service 

  - 게시글 관리
    - 게시글 & 파일 추가, 삭제, 수정, 조회
    - 유저 정보, 게시글 제목, 게시글 내용 등
  - 게시글 검색 기능
    - 작성 유정 아이디
    - 게시글 제목, 게시글 내용 등을 통해 검색
  - 댓글 작성 및 상호작용 기능
  - 팔로우 기능
  - 알람 기능

- Newsfeed-service 
  - 팔로우한 사용자들의 활동 확인
  - 뉴스피드 정렬
- Stock-sevice
  - 스프링 배치 작업을 통하여 매일 정해진 시간에 주기적으로 데이터 업데이트
    - 도커와 젠킨스로 작업 관리자로 스케쥴 관리
  - 종목 리스트 및 데이터 관리
  - 주가 차트 확인
  - 이동평균선, 볼린전 밴드, MACD 기술적 지표
 

</details>
# **개발환경**

![Java](https://img.shields.io/badge/Java17-007396.svg?&style=for-the-badge&logo=Java&logoColor=white)
![SpringBoot](https://img.shields.io/badge/Spring_Boot(3.2)-6DB33F.svg?&style=for-the-badge&logo=SpringBoot&logoColor=white)
![SpringCloud](https://img.shields.io/badge/Spring_Cloud(3.2)-6DB33F.svg?&style=for-the-badge&logo=SpringBoot&logoColor=white)
![SpringSecurity](https://img.shields.io/badge/spring_security-6DB33F.svg?&style=for-the-badge&logo=springsecurity&logoColor=white)
![SpringBatch](https://img.shields.io/badge/Spring_Batch-6DB33F.svg?&style=for-the-badge&logo=SpringBoot&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?&style=for-the-badge&logo=mysql&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-FF6C2C.svg?&style=for-the-badge&logo=JPA&logoColor=white)
![Redis](https://img.shields.io/badge/redis-DC382D.svg?&style=for-the-badge&logo=redis&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-6DB33F.svg?&style=for-the-badge&logo=Swagger&logoColor=white)
![github](https://img.shields.io/badge/github-2088FF.svg?&style=for-the-badge&logo=githubactions&logoColor=white)
![docker](https://img.shields.io/badge/docker-2496ED.svg?&style=for-the-badge&logo=docker&logoColor=white)
![jenkins](https://img.shields.io/badge/jenkins-D24939.svg?&style=for-the-badge&logo=jenkins&logoColor=white)
![postman](https://img.shields.io/badge/postman-FF6C37.svg?&style=for-the-badge&logo=postman&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032.svg?&style=for-the-badge&logo=Git&logoColor=white)
![Intelli J](https://img.shields.io/badge/Intellijidea%20IDE-2C2255.svg?&style=for-the-badge&logo=intellijidea%20IDE&logoColor=white)
# **Docker Compose 사용 가이드**
<details>
<summary>클릭하여 섹션 열기/닫기</summary>

## **1. 컴포즈 실행**

### **1.1 기본 실행**

컴포즈 파일이 존재하는 디렉터리에서 실행합니다.

```bash
docker-compose up -d
```

### **1.2 특정 파일 사용**

다른 컴포즈 파일을 사용하려면 파일 경로를 지정합니다.

```bash
docker-compose -f 컴포즈파일_경로 up
```

### **1.3 백그라운드 실행**

컴포즈를 백그라운드에서 실행합니다.

```bash
docker-compose up -d
```

### **1.4 서비스 스케일 조정**

특정 서비스의 컨테이너 개수를 조정합니다.

```bash
docker-compose --scale 서비스_명=서비스수 up
```

## **2. 컴포즈 종료**

### **2.1 모든 컨테이너 종료 및 삭제**

모든 컴포즈 컨테이너를 종료하고 삭제합니다.

```bash
docker-compose down
```

## **3. 컴포즈 정지**

### **3.1 모든 컨테이너 정지**

모든 컴포즈 컨테이너를 정지합니다.

```bash
docker-compose stop
```

## **4. 컴포즈 컨테이너 확인**

컴포즈로 실행 중인 컨테이너의 상태를 확인합니다.

```bash
docker-compose ps
```

## **5. 로그 확인**

### **5.1 특정 서비스의 로그 확인**

특정 서비스의 로그를 확인합니다.

```bash
docker-compose logs 서비스_이름 -f
```

### **5.2 실시간 로그 확인**

실시간으로 로그를 확인합니다.

## **6. 컨테이너 조작**

### **6.1 컨테이너 실행**

서비스에 지정된 컨테이너를 실행합니다.

```bash
docker-compose run 서비스_명
```

### **6.2 컨테이너 시작 / 정지 / 일시정지 / 재개**

서비스에 지정된 컨테이너를 시작, 정지, 일시정지, 재개합니다.

```bash
docker-compose start 서비스_명
docker-compose stop 서비스_명
docker-compose pause 서비스_명
docker-compose unpause 서비스_명
```

## **7. 공개된 포트 표시**

컴포즈로 실행 중인 서비스의 공개된 포트를 표시합니다.

```bash
docker-compose port
```

</details>

# **Api 명세**
<details>
<summary>클릭하여 섹션 열기/닫기</summary>
![스크린샷 2024-02-27 213625](https://github.com/tztos104/Project_prochat/assets/128444192/b41319fe-0322-46ca-b11c-89ce57de69a9)
![스크린샷 2024-02-27 213658](https://github.com/tztos104/Project_prochat/assets/128444192/d7ee66ec-0aa2-4682-918a-c09635ad5915)
![스크린샷 2024-02-27 213731](https://github.com/tztos104/Project_prochat/assets/128444192/c5ce9943-c4bf-40d2-9def-d55c8179108d)
![스크린샷 2024-02-27 213739](https://github.com/tztos104/Project_prochat/assets/128444192/797ce050-2eb8-4ec1-b58f-ae93f48768e4)



</details>

# **ERD 설계**
<details>
<summary>클릭하여 섹션 열기/닫기</summary>
  
![스크린샷 2024-02-27 175141](https://github.com/tztos104/Project_prochat/assets/128444192/3ef8265c-ef39-4393-91ce-9b475cee87aa)

</details>

# **기술적 이슈와 해결과정**
<details>
<summary>클릭하여 섹션 열기/닫기</summary>
  
![스크린샷 2024-02-27 175141](https://github.com/tztos104/Project_prochat/assets/128444192/3ef8265c-ef39-4393-91ce-9b475cee87aa)

</details>
