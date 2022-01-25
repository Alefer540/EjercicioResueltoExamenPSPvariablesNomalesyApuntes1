import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

class Jugadores extends Thread {
 int nomjugador;
 Semaphore semaforo10primeros;
 Semaphore semaforofase2;
 ReentrantLock Ganador;

 Jugadores(int nomjugador, Semaphore semaforo10primeros, Semaphore semaforofase2, ReentrantLock Ganador) {
  this.nomjugador = nomjugador;
  this.semaforo10primeros = semaforo10primeros;
  this.semaforofase2 = semaforofase2;
  this.Ganador = Ganador;
 }

 @Override
 public void run() {

  try {
   Thread.sleep((new Random().nextInt(3) + 1) * 1000);//tiempo aleatorio de entre 1 a 3 segundos
   int pmuerte = new Random().nextInt(10) + 1; //creacion de random de probabilidad de muerta
   if (pmuerte > 9) {//10% posibilidades de morir
    System.out.println("El jugador" + this.nomjugador + " ha sido descalificado en la prueba 1 \n");
   } else {
    System.out.println("El jugador" + this.nomjugador + " ha superado la prueba 1 \n");
    if (semaforo10primeros.tryAcquire())//deja pasar la cantidad de jugadores puestos en el permits en la declaracion en este caso 10 y los que no se eliminan
    {
     System.out.println("El jugador " + this.nomjugador + " ha completado a tiempo la prueba 1 y pasa a la PRUEBA 2  \n");
     Main.jugadoressemaforo2();//cada vez que un jugador pasa el if de muerte suma uno en la funcion si llega a 10 pasa
     semaforofase2.acquire();//cuando main.jugadoressemaforo2()tenga 10 entran 10 todos de golpe
     //FASE2
     Thread.sleep(((new Random().nextInt(3)) + 1) * 1000);//tiempo aleatorio de entre 1 a 3 segundos
     int pmuerte2 = new Random().nextInt(10) + 1;//creacion de random de probabilidad de muerta
     if (pmuerte2 > 9) {//10% posibilidades de morir
      System.out.println("El jugador " + this.nomjugador + " ha sido descalificado en la prueba 2  \n");
     } else {
      System.out.println("El jugador " + this.nomjugador + " ha superado la prueba 2  \n");
      if (Ganador.tryLock()) {//deja entrar al ganador y los demas a la funcion main.posiciondel2al5
       System.out.println("El jugador " + this.nomjugador + " HA GANADO LA PARTIDA  \n ");
      } else {
       Main.posiciondel2al5(this);
      }
     }

    } else {
     System.out.println("El jugador " + this.nomjugador + " no ha completado a tiempo la prueba 1 y ha sido descalificado  \n");
    }
   }

  } catch (InterruptedException e) {
   e.printStackTrace();
  }

 }
}


