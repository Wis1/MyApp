call runcrud
if "%ERRORLEVEL%"== "0" goto startbrowser
echo.
echo Cannot start runcrud
goto fail

:startbrowser
start firefox localhost:8080/crud/v1/tasks/

goto end

:fail
echo.
echo There were errors

:end
echo.
echo Work is finished