@echo off
if exist app.pid (
    for /f "tokens=*" %%i in (app.pid) do (
        taskkill /PID %%i /F
    )
    del app.pid
)
call .\mvnw clean