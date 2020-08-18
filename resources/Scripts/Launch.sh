#!/bin/sh -x

echo $'Launching Exile Buddy'
echo '> "C:\Program Files\Java\jre1.8.0_251\bin\java.exe" -version\n'
"C:\Program Files\Java\jre1.8.0_251\bin\java.exe" -version
echo '> "C:\Program Files\Java\jre1.8.0_251\bin\java.exe" -jar Exile-Buddy.jar'
"C:\Program Files\Java\jre1.8.0_251\bin\java.exe" -jar Exile-Buddy.jar

read -p $'Exile Buddy has stopped. If it\'s a crash, please read the output above.\n\n- Press Return to exit...'