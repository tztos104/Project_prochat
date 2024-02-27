# **프로젝트 소개**


## **서비스 기능**
<details>

마이크로 서비스(MSA)로 3개의 서비스로 분리
-docker-compose 환경구축

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
    -배치 멀티쓰레드를 이용한 대규모 트래픽 처리 
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
## **1.마이크로 서비스 아키택처를 선택한 이유**
- 각 서비스를 독립적으로 개발 및 배포할 수 있으므로 개발 속도가 향상
- 이전에는 전체 시스템에 문제가 발생하면 모든 서비스에 영향을 미쳤지만, 마이크로서비스 아키텍처를 도입하면 문제 발생 시 영향 범위를 쉽게 제한하고 해결 가능.
- 특정 서비스 트래픽 증가에 맞춰 개별 서비스를 확장하여 시스템 성능을 유지.
- 이번 프로젝트에서는 개인프로젝트로 쓰지않았지만 각 서비스를 서로 다른 기술 스택을 사용하여 개발할 수 있음.

  ## **2.확장성 및 유연성 향상**

- 문제
  - 특정 서비스 트래픽 증가 시 시스템 전체 성능 저하
  - 새로운 기능 추가 시 시스템 아키텍처 변경 어려움
 
  - 해결
    - 로드밸런서 나 api 게이트웨 중 api게이트웨이 도입
      -  로드밸런서는 사용자 인증 및 권한 관리하기가 어렵고 API 관리 기능, 기능 확장성이 좋아 api게이트웨이 선택
    - 서비스를 모듈화하여 각 모듈을 독립적으로 개발하고 배포하여 시스템 유연성을 향상시킵니다.
    - 재사용 가능한 코드를 개발하여 개발 및 유지 관리 효율성을 높입니다.
 ## **3.데이터 처리 속도 향상**
- 문제
  - 종목데이터는 엄청난 양이며, 실시간 처리가 중요.
  - 기존 단일 스레드 방식으로는 데이터 처리 속도가 느리고, 실시간 처리가 어려웠습니다.
  - 종목데이터 260만건을 처리는 하는 속도가 15분가량 나옴
- 해결
  - 스프링 배치를 통해 주가 데이터 자동화.
  - 수집된 데이터를 여러 스레드에서 분할하여 병렬 처리.
  - 8개의 멀티스레드를 동시에 실행하여 260만건 처리속도가 1분 40초로 10배이상 속도향상.
</details>
