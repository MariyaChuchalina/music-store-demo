FROM openjdk:8
ARG artifactid
ARG version
ENV artifact ${artifactid}-${version}.jar
EXPOSE 8080
ADD target/{artifact}.jar {artifact}.jar
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar ${artifact}"]