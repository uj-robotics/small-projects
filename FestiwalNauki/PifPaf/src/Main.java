
import java.util.Random;
import javax.microedition.lcdui.Graphics;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.TouchSensor;
import lejos.util.Delay;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Michał
 */
public class Main {

    static int MAXSCORE = 5;
    static int LEFT = 1;
    static int RIGHT = 2;
//-------------------------------
    static int leftScore = 0;
    static int rightScore = 0;

    static void leftGoDown() {
        Motor.A.rotateTo(-90);
    }

    static void rightGoDown() {
        Motor.C.rotateTo(-90);
    }

    static void motorsUp() {
        Motor.A.rotateTo(0);
        Motor.C.rotateTo(0);
    }

    static void refreshScreen() {
        int width = 5;
        Graphics g = new Graphics();
        LCD.clear();
        for (int i = 0; i < leftScore; ++i) {
            g.fillRect(2 * width * i, 0, width, 60);
        }

        for (int i = 0; i < rightScore; ++i) {
            g.fillRect(95 - 2 * width * i, 0, width, 60);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TouchSensor leftGun = new TouchSensor(SensorPort.S1);
        TouchSensor rightGun = new TouchSensor(SensorPort.S4);

        Graphics g = new Graphics();
        refreshScreen();
        Random r = new Random();
        Button.waitForPress();
        while (true) {

            leftScore = 0;
            rightScore = 0;
            while (leftScore < MAXSCORE && rightScore < MAXSCORE) {
                refreshScreen();

                motorsUp();
                Delay.msDelay(300);
                Sound.beepSequenceUp();
                int winner = 0;

                int time = 2000 + (Math.abs(r.nextInt()) % 4000);

                long startTime = System.currentTimeMillis();
                while (System.currentTimeMillis() < startTime + time) {
                    if (leftGun.isPressed()) {
                        winner = RIGHT;
                        Sound.buzz();
                        break;
                    }

                    if (rightGun.isPressed()) {
                        winner = LEFT;
                        Sound.buzz();
                        break;
                    }
                }
                if (winner == 0) {
                    Sound.beep();
                    while (true) {
                        if (leftGun.isPressed()) {
                            winner = LEFT;
                            break;
                        }

                        if (rightGun.isPressed()) {
                            winner = RIGHT;
                            break;
                        }
                    }


                }

                if (winner == RIGHT) {
                    leftGoDown();
                    rightScore++;
                } else {
                    rightGoDown();
                    leftScore++;
                }
                Delay.msDelay(2000);
            }

            motorsUp();
            Sound.beepSequence();
            refreshScreen();
            Delay.msDelay(1000);
            if (leftScore==MAXSCORE) {
                rightGoDown();
            } else {
                leftGoDown();
            }
            Button.waitForPress();
        }
    }
}

