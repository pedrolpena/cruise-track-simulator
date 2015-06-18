echo off

javac -source 1.7 -target 1.7 -d .\ -cp .\lib\RXTXcomm.jar .\src\cruisetracksimulator\*.java   
jar cfm CruiseTrackSimulator.jar manifest.txt cruisetracksimulator\*.class

IF EXIST .\dist goto deletedist

:deletedist
del /q /s .\dist  > nul
rmdir /q /s .\dist  > nul
:exit

mkdir .\dist
mkdir .\dist\lib
move /y CruiseTrackSimulator.jar .\dist > nul
copy /y .\lib .\dist\lib > nul
del /s /q .\cruisetracksimulator  > nul
rmdir /s /q .\cruisetracksimulator  > nul


