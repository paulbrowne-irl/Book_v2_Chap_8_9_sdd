@echo off
echo Building Application...
call mvn clean install -DskipTests

if %ERRORLEVEL% NEQ 0 (
    echo Build failed.
    pause
    exit /b %ERRORLEVEL%
)

echo Starting Irish Children's Triage System GUI...
call mvn spring-boot:run
pause
