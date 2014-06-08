@echo off
E:
CD E:\mysql
start mysql_start.bat
if errorlevel 1 pause

CD E:\h2\bin
start javaw -cp "h2-1.3.174.jar;%H2DRIVERS%;%CLASSPATH%" org.h2.tools.Console %*
if errorlevel 1 pause

start E:\eclipse\indigo\eclipse\eclipse.exe -data D:\workspace\workspace37\jivejdon
start explorer D:\workspace\workspace37\jdon\jivejdon
start explorer E:\jivejdon-tomcat\apache-tomcat-6.0.26\logs
start explorer http://localhost:8080/jivejdon/

CD E:\jivejdon-tomcat\apache-tomcat-6.0.26\bin
cls
startup.bat
