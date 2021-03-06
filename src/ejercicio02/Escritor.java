/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio02;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cristian
 */
public class Escritor implements Runnable {
  private final String name;
  private Libro libro;

  public Escritor(String name, Libro libro) {
    this.name = name;
    this.libro = libro;
  }
  
  @Override
  public void run() {
    while (true) {
      try {
        this.libro.mutex2.acquire();
        this.libro.n_escritores ++;
        if (this.libro.n_escritores == 1)
          this.libro.lectores.acquire();
        this.libro.mutex2.release();
        this.libro.escritores.release();
        
        System.out.println(this.name + " escribiendo en el recurso");
        Thread.sleep((int) Math.floor(Math.random() * 2000));
        
        this.libro.escritores.release();
        this.libro.mutex2.acquire();
        this.libro.n_escritores --;
        if (this.libro.n_escritores == 0)
          this.libro.lectores.release();
        this.libro.mutex2.release();
        
      } catch (InterruptedException ex) {
        Logger.getLogger(Escritor.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

  }
}
