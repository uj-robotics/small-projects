#include <CapSense.h>

const int sensorQuantity = 8;
CapSense* sensorDefinitions [sensorQuantity];

byte X=B0;
long start, sensor;

CapSense note1 = CapSense(2,3);
CapSense note2 = CapSense(2,4);
CapSense note3 = CapSense(2,5);
CapSense note4 = CapSense(2,6);
CapSense note5 = CapSense(2,7);
CapSense note6 = CapSense(2,8);
CapSense note7 = CapSense(2,9);
CapSense note8 = CapSense(2,10);

void setup()
{
  sensorDefinitions[0] = &note1;
  sensorDefinitions[1] = &note2;
  sensorDefinitions[2] = &note3;
  sensorDefinitions[3] = &note4;
  sensorDefinitions[4] = &note5;
  sensorDefinitions[5] = &note6;
  sensorDefinitions[6] = &note7;
  sensorDefinitions[7] = &note8;
  Serial.begin(9600);
}

void loop()
{
  start = millis();
  
  for (int i=0; i<sensorQuantity; i++)
  {
    sensor = (*sensorDefinitions[i]).capSense(10);
    if (sensor > 50)
      bitWrite(X,i,1);
    else
      bitWrite(X,i,0);
  }
  
  Serial.write((int)X);
}
