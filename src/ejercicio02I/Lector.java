/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio02I;

import ejercicio02.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cristian
 */
public class Lector implements Runnable {

  private final String name;
  private Libro libro;

  public Lector(String name, Libro libro) {
    this.name = name;
    this.libro = libro;
  }

  @Override
  public void run() {
    while (true) {
      try {

        this.libro.mutex.acquire();
        this.libro.lectores.acquire();
          this.libro.n_lectores ++;
          if (this.libro.n_lectores == 1) {
            this.libro.escritores.acquire();
          }
        this.libro.lectores.release();
        this.libro.mutex.release();

        System.out.println(this.name + " leyendo en el recurso ");
        Thread.sleep((int) Math.floor(Math.random() * 1000));

        this.libro.lectores.acquire();
          this.libro.n_lectores --;
          if (this.libro.n_lectores == 0) {
            this.libro.escritores.release();
          }
        this.libro.lectores.release();

      } catch (InterruptedException ex) {
        Logger.getLogger(Lector.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
}
