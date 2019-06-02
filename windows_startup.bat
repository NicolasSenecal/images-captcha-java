@echo off
for /f %%G in ('forfiles /s /m *.java /c "cmd /c echo @relpath"') do @echo %%~G >> sources.txt
javac @sources.txt -d bin
xcopy .\src\* .\bin\ /S /I /C /y /Exclude:sources.txt > nul
del sources.txt
java -cp bin fr.upem.captcha.ui.MainUi