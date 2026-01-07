@echo off
echo Running Serenity BDD Tests...
call mvn clean verify serenity:aggregate
if %ERRORLEVEL% EQU 0 (
    echo.
    echo Tests Passed! Report available at target/site/serenity/index.html
    start target/site/serenity/index.html
) else (
    echo.
    echo Tests Failed. Check console output or report at target/site/serenity/index.html
    exit /b %ERRORLEVEL%
)
