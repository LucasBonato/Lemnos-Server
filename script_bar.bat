@echo off
echo Setting environment variables...
set POSTGRES_USERNAME=
set POSTGRES_PASSWORD=
set FIREBASE_CREDENTIALS=
set DB_HOST=
set DB_PORT=
set DB_NAME=

echo Building the project...
call .\mvnw clean package
call .\mvnw dependency:resolve

if %errorlevel% neq 0 (
    echo Build failed. Exiting.
    exit /b %errorlevel%
)

echo Running the project...
start java -jar target\Lemnos-API-0.0.2-SNAPSHOT.jar

for /f "tokens=2 delims==" %%i in ('wmic process where "caption='java.exe'" get processid /value') do (
    echo %%i > app.pid
)