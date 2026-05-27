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
<img width="979" height="920" alt="image" src="https://github.com/user-attachments/assets/860ab493-6f8e-4266-a351-bc5e7fb9486a" />

# 성공 이미지 캡처
<img width="979" height="920" alt="image" src="https://github.com/user-attachments/assets/8730f1e5-ec61-44af-97b0-be66a1269b05" />

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
