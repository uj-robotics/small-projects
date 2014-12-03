import ddf.minim.analysis.*;
import ddf.minim.*;
import ddf.minim.signals.*;
import processing.serial.*;

Minim minim;
AudioOutput out;

Serial myPort;

int[] noteArray = new int[8];
int[] toPlay = new int[8];
int[] toPlayOld = new int[8];
int[] incoming = new int[8];

//note definitions
int A3= 220;
int B3= 247;
int C4= 262;
int D4= 294;
int E4= 330;
int F4= 349;
int G4= 392;
int A4= 440;
int B4= 494;
int C5= 523;
int D5= 587;

SineWave mySine;
MyNote newNote;

void setup()
{
  size(512, 200, P3D);
  myPort = new Serial(this, "COM5", 9600);

  minim = new Minim(this);
  out = minim.getLineOut(Minim.STEREO);

  noteArray[0]=C4;
  noteArray[1]=D4;
  noteArray[2]=E4;
  noteArray[3]=F4;
  noteArray[4]=G4;
  noteArray[5]=A4;
  noteArray[6]=B4;
  noteArray[7]=C5;
}

void draw()
{
  background(0);
  stroke(255);

  for(int i = 0; i < out.bufferSize() - 1; i++)
  {
    float x1 = map(i, 0, out.bufferSize(), 0, width);
    float x2 = map(i+1, 0, out.bufferSize(), 0, width);
    line(x1, 125 + out.left.get(i)*50, x2, 125 + out.left.get(i+1)*50);
    line(x1, 375 + out.right.get(i)*50, x2, 375 + out.right.get(i+1)*50);
  }
}

void serialEvent (Serial myPort) {
  int wejscie = myPort.read();
 
  for (int i = 0; i < 8; i++){
    incoming[i] = Character.digit(binary(wejscie,8).charAt(7-i),10);
  }
  
  for(int i = 0; i < 8; i++){
    toPlayOld[i] = toPlay[i];
    toPlay[i] = incoming[i];
  }

  float pitch = 0;

  for (int i=0;i<8;i++)
  {
    if (toPlay[i]>0 & (toPlay[i]!=toPlayOld[i]))
    { 
      pitch = noteArray[i];
      newNote = new MyNote(pitch, 0.3);
    }
  }
  

}

void stop()
{
  out.close();
  minim.stop();
  super.stop();
}

class MyNote implements AudioSignal
{
  private float freq;
  private float level;
  private float decay;
  private SineWave dzwiek;

MyNote(float pitch, float amplitude)
{
  freq = pitch;
  level = amplitude;
  dzwiek = new SineWave(freq, level, out.sampleRate());
  decay = .9;
  out.addSignal(this);
}

void aktualizuj()
{
  level = level * decay;
  dzwiek.setAmp(level);

  if (level < 0.01) {
  out.removeSignal(this);
  }
}

void generate(float [] samp)
{
  dzwiek.generate(samp);
  aktualizuj();
}

void generate(float [] sampL, float [] sampR)
{
  dzwiek.generate(sampL, sampR);
  aktualizuj();
}

}
