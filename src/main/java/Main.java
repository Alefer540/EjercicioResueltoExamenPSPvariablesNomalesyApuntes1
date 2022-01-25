import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    static AtomicInteger listos = new AtomicInteger(0);
    static AtomicInteger puestos = new AtomicInteger(2);
    static Semaphore s22=new Semaphore(0,true);

    public static void main (String[]args){
        Semaphore semaforo10primeros = new Semaphore (10,true);
        Semaphore semaforofase2 =Main.s22;
        ReentrantLock Ganador =new ReentrantLock();//semaforo para restringuir un valor abajo se pone tryLock
        //creacion de los 20 jugadores
        for (int i=1;i<=20;i++){
            Jugadores j= new Jugadores(i,semaforo10primeros,semaforofase2,Ganador);
            j.start();
        }
    }
    public synchronized static void posiciondel2al5 (Jugadores jugadores){//posiciones del 2 al 5 y los demas muere
        int posicion=Main.puestos.get();

        if(posicion<=5){
            System.out.println("El jugador"+jugadores.nomjugador+" ha quedado en la POSICION: "+posicion+"\n");
        }else{
            System.out.println("“El jugador "+jugadores.nomjugador+ " no ha llegado a tiempo a la prueba 2 y ha sido descalificado\n");
        }
        Main.puestos.set(posicion+1);
    }
    public synchronized static void jugadoressemaforo2() {//introducir datos en el semaforofase2
        Main.listos.set(Main.listos.get()+1);//sumamos jugadores
        if (Main.listos.get() == 10){//cuando es igual a 10 entrar todos de golpe
            Main.s22.release(10);//mediante el release entra de golpe
        }

    }

}
