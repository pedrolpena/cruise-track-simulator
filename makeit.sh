
#!/bin/bash
javac -source 1.7 -target 1.7 -d ./ -cp ./lib/RXTXcomm.jar src/cruisetracksimulator/*.java 
jar cfm CruiseTrackSimulator.jar manifest.txt cruisetracksimulator/*.class 
if [ -d "dist" ]; then
    rm -r dist
fi
mkdir ./dist
rm -r ./cruisetracksimulator
mv ./CruiseTrackSimulator.jar ./dist
cp -r ./lib ./dist
