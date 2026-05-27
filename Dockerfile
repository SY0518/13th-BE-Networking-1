# 1. 빌드 스테이지 (Java 17 환경)
FROM gradle:8.5-jdk17 AS builder
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew build -x test

# 2. 실행 스테이지 (Java 17 실행 환경)
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
# 빌드 스테이지에서 생성된 jar 파일만 복사
COPY --from=builder /app/build/libs/*-SNAPSHOT.jar app.jar

# 운영 환경 프로필 지정 및 타임존 설정
ENV SPRING_PROFILES_ACTIVE=prod
ENV TZ=Asia/Seoul

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]