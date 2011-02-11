@echo off

setlocal enabledelayedexpansion

set LIB=
for %%f in (..\lib\*.jar) do set LIB=!LIB!;%%f
rem echo libs: %LIB%

set CP=%LIB%;%CP%

java -classpath "%CP%" -Djava.net.preferIPv4Stack=true -Dlog4j.configuration=..\etc\log4j.xml org.jboss.devcon.HotRodConsole %*

:fileEnd
