# deploy in tomcat
    modify tomcat configuration
        conf/web.xml
        
#with spring boot:
    spring boot 1.x:
        server.session.timeout=
    spring boot 2.x:
        server.servlet.session.timeout=
        
#with spring session & specified annotation(@EnableXxxxHttpSession)
    use properties in annotation:
        maxInactiveIntervalInSeconds = xxx
    use XxxxOperationSessionRepository
        sessionRepository.setDefaultMaxInactiveInterval(xxx)
