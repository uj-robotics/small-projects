#!/bin/bash
rm festiwal/Sterowanie.class 
rm festiwal/JoystickFestiwal.class
nxjpcc  -cp ./Joystick.jar festiwal/*.java  -encoding UTF8 
