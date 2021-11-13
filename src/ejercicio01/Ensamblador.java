/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio01;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cristian
 */
public class Ensamblador extends Empleado implements Runnable {

  private final LineaEnsamble lineaEnsamble;
  public static final String ANSI_PURPLE = "\u001B[35m";
  public Ensamblador(String name, LineaEnsamble lineaEnsamble) {
    super(name);
    this.lineaEnsamble = lineaEnsamble;
  }

  @Override
  public void run() {
    while(true) {
      try {
        this.lineaEnsamble.iniciarMueble();
        if (this.lineaEnsamble.getAvance() == 100) {
          System.out.println(ANSI_PURPLE + this.name + " dice: Mueble terminado!");
        }else {
          System.out.println(ANSI_PURPLE + this.name + " dice: Mueble al " + this.lineaEnsamble.getAvance() + "%");
        }
        Thread.sleep((int) Math.floor(Math.random() * 2000));
        this.lineaEnsamble.finalizarMueble();
      } catch (InterruptedException ex) {
        Logger.getLogger(Ensamblador.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
  
}
