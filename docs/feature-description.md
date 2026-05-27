# 아키텍처 다이어그램
[ 사용자 ]
|
(HTTPS/443 Port)
|
[ 13.209.5.165.nip.io (도메인) ]
|
[ Nginx (Reverse Proxy) ]
|
[ Spring Boot 컨테이너 (8080 포트) ]
|
[ MySQL 컨테이너 (3306 포트) ]

# CI/CD 배포 파이프라인

[ GitHub 레포지토리 (push) ]
|
v
[ GitHub Actions (빌드 및 배포) ]
|--------------------------+
|                          |
(이미지 푸시)              (SSH 접속)
|                          |
v                          v
[ Docker Hub ]           [ EC2 인스턴스 ]
(Docker Compose 실행)

# 배포 URL
https://13.209.5.165.nip.io
https://13.209.5.165.nip.io/swagger-ui/swagger-ui/index.html

# 배포 이미지
<img width="979" height="920" alt="image" src="https://github.com/user-attachments/assets/a54b1ec9-5bfd-4c18-8587-2427832c6594" />

# 성공 이미지 캡처
<img width="979" height="920" alt="image" src="https://github.com/user-attachments/assets/d0e2dd4b-d8a6-44cd-ac69-2c2726e69ece" />

# Dockerfile.md
## JDK 17 베이스 이미지 사용
FROM openjdk:17-jdk-slim

## JAR 파일 복사
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

## 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]

# Nginx.md
server {
listen 80;
server_name 13.209.5.165.nip.io;

    # Certbot을 통한 HTTPS 리다이렉트 설정 등 추가 가능
    location / {
        proxy_pass http://cotato-app-container:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}

# 트러블 슈팅 노트
## 이슈 1: Swagger UI 접속 시 500 에러 발생
문제: Swagger UI 접속 시 500 Internal Server Error 발생.
원인: 브라우저가 Swagger 페이지 로딩 시 부수적으로 호출하는 favicon.ico 파일을 찾지 못해 발생한 NoResourceFoundException이 전역 예외 처리기(GlobalExceptionHandler)에 의해 500 에러로 오인 처리됨.
해결: src/main/resources/static/ 디렉토리에 favicon.ico 파일을 추가하여 리소스 부재 문제를 근본적으로 해결함.

## 이슈 2: GitHub Actions 자동 배포 시 Permission denied 에러
문제: Build with Gradle 단계에서 gradlew 실행 권한 부족으로 빌드 프로세스가 중단됨.
원인: GitHub Actions 환경으로 소스 코드가 체크아웃되는 과정에서 파일의 실행 권한(executable) 정보가 초기화됨.
해결: deploy.yml 워크플로우의 빌드 단계 명령어를 chmod +x ./gradlew && ./gradlew build -x test로 수정하여 실행 권한을 명시적으로 부여함.

## 이슈 3: GitHub Actions 배포 단계에서 SSH Authentication failed 에러
문제: EC2로 SSH 접속을 시도하는 단계에서 인증 실패 발생.
원인: 
1. .pem 키 파일 내용을 GitHub Secrets에 등록할 때 전체 내용(시작/종료 태그 포함)이 누락되거나 일부가 유실됨.
2. EC2_USERNAME 설정값에 명령어 옵션(-u)이 잘못 포함됨.
해결: .pem 파일의 전체 내용을 다시 복사하여 등록하고, EC2_USERNAME 값을 ubuntu로 정확히 수정함.

## 이슈 4: 컨테이너 빌드 후 Swagger UI 404/500 에러
문제: 배포 후 /swagger-ui/index.html 접근 시 404 발생, 이후 /v3/api-docs 호출 시 500 에러 발생.
원인:
1. 의존성 누락: build.gradle에 Swagger 의존성(springdoc-openapi)이 선언되지 않아 API 명세 생성 라이브러리가 빌드에 포함되지 않음.
2. 버전 호환성: Spring Boot 3.4.x 환경에서 구버전(2.3.0) 라이브러리를 사용하여 내부 API 충돌(NoSuchMethodError) 발생.
해결: springdoc-openapi-starter-webmvc-ui 버전을 2.8.5로 업그레이드하고, application-prod.yml에 패키지 스캔 경로를 명시하여 정상적으로 API를 수집하도록 수정.

## 이슈 5: 브랜치 병합 중 코드 누락 및 충돌
문제: feat/ApplicntManagement 브랜치 병합 중 코드 유실 및 build.gradle 충돌 발생.
원인: 자동 병합 시 충돌(Conflict)이 발생했으나 해결하지 않고 진행하여 파일 시스템과 코드 상태 간 불일치 발생.
해결: git merge --abort로 병합 상태를 초기화한 후, 충돌 파일을 직접 열어 중복 기호를 제거하고 의존성 코드를 수동 병합(Manual Resolve)하여 정합성 확보.

## 이슈 6: 서버 환경 오염으로 인한 배포 버전 불일치
문제: 최신 코드를 배포했음에도 이전 버전의 설정이 로드되거나 Swagger 문서에 API가 나타나지 않음.
원인: Docker 컨테이너 재배포 시 이전 빌드의 이미지와 설정 파일이 캐시되어 서버 환경에 남아있음.
해결: docker compose down 및 docker image prune -a -f 명령어를 통해 기존 이미지와 컨테이너를 완전히 삭제한 후 재배포하여 환경 클린업 수행.
