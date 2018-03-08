@echo off
Start "java-setPath.bat" /secondary /minimized
if exist %JAVA_HOME%\bin\java.exe (goto found) else (goto notfound)
:found
Start "xampp-exsit.bat" /secondary /minimized
Start "Xampp.bat" /secondary /minimized
call "Resort.exe" /secondary /minimized
goto end
:notfound
Start "install-java.bat" /secondary /minimized
Start "xampp-exsit.bat" /secondary /minimized
Start "Xampp.bat" /secondary /minimized
call "Resort.exe" /secondary /minimized
goto end
:end
exit 0