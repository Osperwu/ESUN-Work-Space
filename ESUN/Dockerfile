# 使用 OpenJDK 17 作為基底映像
FROM openjdk:17-jdk-slim

# 定義維護者資訊
LABEL maintainer="osperwu"

# 添加 Spring Boot 的 jar 檔到容器
ADD target/ESUN-0.0.1-SNAPSHOT.jar ESUN.jar

# 設定容器啟動時執行的命令
ENTRYPOINT ["java", "-jar", "/ESUN.jar"]

# 開放 Spring Boot 預設埠 8080
EXPOSE 8081