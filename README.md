# Images Captcha in Java
Images selection captcha in JAVA 

## To compile
Linux : 
```
find . -name "*.java"  -print | xargs javac -d bin 
```

Windows:
```
for /f %i in ('forfiles /s /m *.java /c "cmd /c echo @relpath"') do @echo %~i >> sources.txt
javac @sources.txt -d bin 
```

## To copy resources
You have to copy the resources from `/src` into `/bin` that the program can access to images :

Linux : 
```
rsync -avz --exclude '*.java' ./src/ ./bin/
```

Windows:
```
xcopy .\src\* .\bin\ /S /I /C /Exclude:sources.txt
```

## To execute
```
java -cp bin fr.upem.captcha.Main
```