@echo off
rem ----- Verify and Set Required Environment Variables -----------------------

if "%K_WAS_ROOT%" == "" (
  echo You must set K_WAS_ROOT to point to the base of your WebSphere Application Server installation
  goto cleanup
)
if "%K_WPS_ROOT%" == "" (
  echo You must set K_WPS_ROOT to point to the base of your WebSphere Portal Server installation
  goto cleanup
)
if "%K_TAS_ROOT%" == "" (
  echo You must set K_TAS_ROOT to point to the base of your Themes and Skins directory
  goto cleanup
)

goto gotJavaHome


:gotJavaHome

set _CP_=.\lib\ant\ant.jar
set _CP_=%_CP_%;.\lib\ant\ant-launcher.jar
set _CP_=%_CP_%;.\lib\ant\ant-contrib-0.6.jar
set _CP_=%_CP_%;.\lib\ant\ant-nodeps-1.6.4.jar

"%K_WAS_ROOT%\java\bin\java.exe" -Xmx150m -classpath "%K_WAS_ROOT%\java\lib\tools.jar;%_CP_%" org.apache.tools.ant.Main %1 %2 %3 %4 %5


:cleanup
pause