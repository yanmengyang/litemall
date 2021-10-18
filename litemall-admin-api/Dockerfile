FROM java:8
RUN rm -f /etc/localtime \
&& ln -sv /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
&& echo "Asia/Shanghai" > /etc/timezone
ADD ./target/ /java/src/app
WORKDIR /java/src/app
RUN ls -a /java/src/app
RUN cp /java/src/app/*.jar /tmp/target.jar
RUN rm -rf /java/src/app
RUN sh -c 'touch /tmp/target.jar'
EXPOSE 8080
ENTRYPOINT ["java","-XX:MetaspaceSize=128m","-Xms2048m","-Xmx2048m","-Xmn512m","-Xss256k","-XX:SurvivorRatio=8","-XX:+UseConcMarkSweepGC","-jar","/tmp/target.jar"]

#-XX:+PrintGCDetails 打印GC日志
