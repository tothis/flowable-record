FROM openjdk:8-jre-alpine

MAINTAINER lilei <tothis@qq.com>

# 指定了临时文件目录为/tmp
VOLUME /tmp
# ${JAR_FILE}为pom中属性
# 复制打包后的jar文件到docker容器目录下app.jar
ADD ${JAR_FILE} app.jar
# 启动容器时执行
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
# 指定端口号
EXPOSE 8080