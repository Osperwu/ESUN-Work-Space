# 使用官方 MySQL 8.0 基底映像
FROM mysql:8.0

# 設定環境變數
ENV MYSQL_ROOT_PASSWORD=321cba06
ENV MYSQL_DATABASE=esunDB
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=321cba06

# 複製初始化 SQL 腳本到容器
COPY init.sql /docker-entrypoint-initdb.d/

# 開放 MySQL 埠 3306
EXPOSE 3307