public class CocheEnMontaje {

    private int progMotor, progChasis, progRuedas;
    private boolean motorListo, chasisListo, ruedasListo;




    public synchronized void esperarPiezas(){

        while (true){
            wait(1000);
        }
    }

}


