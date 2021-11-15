/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio02IV;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cristian
 */
public class Escritor implements Runnable {
  private final String name;
  private final Libro libro;

  public Escritor(String name, Libro libro) {
    this.name = name;
    this.libro = libro;
  }
  
  @Override
  public void run() {
    while (true) {
      try {
        
        this.libro.empezarEscribir();
        
        System.out.println(this.name + " escribiendo en el recurso");
        Thread.sleep((int) Math.floor(Math.random() * 2000));
        
        this.libro.terminarEscribir();
        
      } catch (InterruptedException ex) {
        Logger.getLogger(Escritor.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

  }
}
