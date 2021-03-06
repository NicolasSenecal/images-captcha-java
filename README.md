# Images Captcha in Java - Pasta, rice and potatoes (fr)

![Images Captcha YPreview](./preview.png)

Images selection captcha in JAVA with difficulty management (image accuracy).
Text in French only.

## TO BUILD

### 1. Compile it
#### Linux
```
find . -name "*.java"  -print | xargs javac -d bin 
```

#### Windows
```
for /f %i in ('forfiles /s /m *.java /c "cmd /c echo @relpath"') do @echo %~i >> sources.txt
javac @sources.txt -d bin 
```

### 2. Copy resources
You have to copy the resources from `/src` into `/bin` that the program can access to images :

#### Linux
```
rsync -avz --exclude '*.java' ./src/ ./bin/
```

#### Windows
```
xcopy .\src\* .\bin\ /S /I /C /Exclude:sources.txt
```

## TO RUN
```
java -cp bin fr.upem.captcha.ui.MainUi
```

OR

If you want to run the software without doing all this stuff, you have to execute 
the `windows_startup.bat` or `linux_startup.sh` file by double clicking on it or 
by executing the following command:

Windows:
```
./windows_startup.bat
```

Linux:
```
./linux_startup.sh
```
