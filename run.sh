#!/bin/bash
mkdir -p out
javac -d out */*.java
java -cp out userinterface.ConsoleMain