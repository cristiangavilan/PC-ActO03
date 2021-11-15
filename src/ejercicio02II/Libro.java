/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio02II;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cristian
 */
public class Libro {

  public Semaphore mutex, lectores, escritores;
  int n_lectores;

  public Libro() {
    this.mutex = new Semaphore(1, true);
    this.lectores = new Semaphore(1, true);
    this.escritores = new Semaphore(1, true);
    this.n_lectores = 0;
  }

  public void empezarLeer() {
    try {
      this.mutex.acquire();
      this.lectores.acquire();
      this.n_lectores++;
      if (this.n_lectores == 1) {
        this.escritores.acquire();
      }
    } catch (InterruptedException ex) {
      Logger.getLogger(Libro.class.getName()).log(Level.SEVERE, null, ex);
    }
    this.lectores.release();
    this.mutex.release();
  }

  public void terminarLeer() {
    try {
      this.lectores.acquire();
    } catch (InterruptedException ex) {
      Logger.getLogger(Libro.class.getName()).log(Level.SEVERE, null, ex);
    }
    this.n_lectores--;
    if (this.n_lectores == 0) {
      this.escritores.release();
    }
    this.lectores.release();
  }

  public void empezarEscribir() {
    try {
      this.mutex.acquire();
      this.escritores.acquire();
    } catch (InterruptedException ex) {
      Logger.getLogger(Libro.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public void terminarEscribir() {
    this.escritores.release();
    this.mutex.release();
  }

}