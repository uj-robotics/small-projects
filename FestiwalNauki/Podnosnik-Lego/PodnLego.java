import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;

public class PodnLego
{
	private ZgloszeniaBluetooth modul;
	private NXTRegulatedMotor lewy = Motor.B;
	private NXTRegulatedMotor prawy = Motor.C;
        private NXTRegulatedMotor podnoszenie = Motor.A;
        private Swiatelko swiatelko;
        private Bibczenie bibczenie;
	private final int MIN_PREDKOSC = 30;
        private int ostatniLewy, ostatniPrawy, ostatniPodnoszenie;
        
	public PodnLego() throws Exception{
            boolean  czyUSB= false;
            modul = new ZgloszeniaBluetooth(czyUSB, this);
            swiatelko = new Swiatelko();
            bibczenie = new Bibczenie();
            new Thread(swiatelko).start();
            new Thread(bibczenie).start();
            
	}
        public void przelaczSwiatelko(){
            swiatelko.przelacz();
        }
         public void przelaczBibczenie(){
            bibczenie.przelacz();
        }
	
	public void uruchom(){
            new Thread(modul).start();
	}
        private int sign(int x){
            if(x>0) return 1;
            if(x<0) return -1;
            return 0;
        }
        public void aktualizuj(int zadanyLewy, int zadanyPrawy, int zadanyPodnoszenie) {
            if(Math.abs(zadanyLewy)<MIN_PREDKOSC) zadanyLewy = 0;
            if(Math.abs(zadanyPrawy)<MIN_PREDKOSC) zadanyPrawy = 0;
            if(Math.abs(zadanyPodnoszenie)<MIN_PREDKOSC) zadanyPodnoszenie = 0;
            
            lewy.setSpeed(zadanyLewy);
            prawy.setSpeed(zadanyPrawy);
            podnoszenie.setSpeed(zadanyPodnoszenie);
            if(sign(ostatniLewy)*sign(zadanyLewy)<=0) {
                if(ostatniLewy!=0) prawy.stop();
                if(zadanyLewy>0)  lewy.forward(); 
                if(zadanyLewy<0) lewy.backward(); 
            }
            if(sign(ostatniPrawy)*sign(zadanyPrawy)<=0) {
                if(ostatniPrawy!=0) prawy.stop();
                if(zadanyPrawy>0) prawy.forward(); 
                if(zadanyPrawy<0) prawy.backward();
            }
            if(sign(ostatniPodnoszenie)*sign(zadanyPodnoszenie)<=0) {
                if(ostatniPodnoszenie!=0) podnoszenie.stop();
                if(zadanyPodnoszenie>0) podnoszenie.forward(); 
                if(zadanyPodnoszenie<0) podnoszenie.backward();
            }
            ostatniLewy = zadanyLewy;
            ostatniPrawy = zadanyPrawy;
            ostatniPodnoszenie = zadanyPodnoszenie;
        }
        
	
	public static void main(String [] args) throws Exception
	{
            PodnLego podnosnik = new PodnLego();
            podnosnik.uruchom();
	}
}
