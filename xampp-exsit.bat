@echo off
if exist "C:\xampp\xampp_start.exe" (goto found) else (goto notfound)
:found 
echo "xampp exsits"
goto end
:notfound 
call "xampp-win32-7.0.9-0-VC14-installer.exe"
goto end
:end 