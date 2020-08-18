:: DO NOT EDIT UNLESS YOU KNOW WHAT YOU'RE DOING
@ECHO OFF
SET BUDDY_JAR=Exile-Buddy.jar
SET JAVA_PARAMETERS=

:: these you can edit
SET JAVA_PATH="C:\Program Files\Java\jre1.8.0_251\bin\java.exe"
SET MIN_RAM=1G
SET MAX_RAM=1G

:: DO NOT EDIT ANYTHING PAST THIS LINE
echo Launching Exile Buddy...
echo.
echo ^> %JAVA_PATH% -version
echo.
%JAVA_PATH% -version
echo.
echo ^> %JAVA_PATH% -Xms%MIN_RAM% -Xmx%MAX_RAM% %JAVA_PARAMETERS% -jar %BUDDY_JAR%
echo.
%JAVA_PATH% -Xms%MIN_RAM% -Xmx%MAX_RAM% %JAVA_PARAMETERS% -jar %BUDDY_JAR%
echo.
echo ^> Exile Buddy has stopped. If it's a crash, please read the output above.
echo.
pause