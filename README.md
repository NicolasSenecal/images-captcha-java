# Images Captcha in Java
Images selection captcha in JAVA 

## To compile
Linux : 
```
find . -name "*.java"  -print | xargs javac -d classes 
```

Windows:
```
dir /s /B *.java > sources.txt
javac @sources.txt -d bin 
```

## To execute
```
java -cp classes fr.upem.captcha.ui.MainUi -d bin
```