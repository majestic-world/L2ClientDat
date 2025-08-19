@echo off
@title Majestic-World Client Dat
@path="C:\Users\Dev\Documents\java\jdk-24.0.2\bin\"
@java -Dfile.encoding=UTF-8 -Djava.util.logging.manager=com.majestic.log.AppLogManager -Xms1g -Xmx2g -jar .\libs\L2ClientDat.jar -debug
@pause