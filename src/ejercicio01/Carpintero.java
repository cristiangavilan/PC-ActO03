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
public class Carpintero extends Empleado implements Runnable {

  private final Parte parte;
  public static final String ANSI_CYAN = "\u001B[36m";

  public Carpintero(String name, Parte parte) {
    super(name);
    this.parte = parte;
  }

  @Override
  public void run() {

    while (true) {
      try {
        this.parte.iniciarAvance();
        System.out.println(ANSI_CYAN + this.name + " trabaja en: " + this.parte.toString() + " " + this.parte.getAvance() + "%");
        Thread.sleep((int) Math.floor(Math.random() * 500));
        this.parte.finalizarAvance();
      } catch (InterruptedException ex) {
        Logger.getLogger(Carpintero.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

  }
}
