FROM open-liberty:kernel-java11
COPY --chown=1001:0  target/usermanagement.war /config/dropins/
COPY --chown=1001:0  mysql-connector-java-8.0.19.jar /config
COPY --chown=1001:0  src/main/liberty/config/server.xml /config