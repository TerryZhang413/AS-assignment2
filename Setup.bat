md Ozlympic
javac -d .\Ozlympic -classpath src\hsqldb.jar src\Ozlympic\*.java src\Exception\*.java src\Data\*.java src\gui\*.java
xcopy src\image Ozlympic\image\*.* /e
xcopy src\Data\participants.txt Ozlympic\Data\ /e
xcopy src\*.jar Ozlympic\*.jar /e
xcopy src\*.bat Ozlympic\*.bat /e
pause