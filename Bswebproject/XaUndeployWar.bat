call "%K_WPS_ROOT%\bin\xmlaccess.bat" -user wpsadmin -password admin -url http://localhost:10040/wps/config -in .\XaUndeployWar.xml
call .\baseBuild.bat clear_build
pause
