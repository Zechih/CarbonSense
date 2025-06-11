# Use Tomcat 9 with JDK 17
FROM tomcat:9.0-jdk17-openjdk

# Clean out default apps
RUN rm -rf /usr/local/tomcat/webapps/*

# Deploy your WAR as the ROOT app (i.e. at /)
COPY target/CarbonSense-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Make Tomcat’s default port accessible
EXPOSE 8080

# Start the Tomcat server
CMD ["catalina.sh", "run"]
