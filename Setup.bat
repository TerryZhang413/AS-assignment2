md Ozlympic
javac -d .\Ozlympic -classpath hsqldb.jar src\Ozlympic\*.java src\Exception\*.java src\Data\*.java src\gui\*.java
xcopy src\image Ozlympic\image\*.* /e
xcopy src\Data\participants.txt Ozlympic\Data\ /e
pause