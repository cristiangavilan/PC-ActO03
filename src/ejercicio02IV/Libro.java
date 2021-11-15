/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio02IV;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cristian
 */
public class Libro {

  private final Lock mutex;
  private final Condition lectores;
  private final Condition escritores;
  private boolean escribiendo;
  private int n_lectores;

  public Libro() {
    this.mutex = new ReentrantLock(true);
    this.lectores = this.mutex.newCondition();
    this.escritores = this.mutex.newCondition();
    this.n_lectores = 0;
  }

  public void empezarLeer() {
    this.mutex.lock();
    try {
      while (this.escribiendo) {
        this.lectores.await();
      }
      this.n_lectores += 1;
    } catch (InterruptedException ex) {
      Logger.getLogger(Libro.class.getName()).log(Level.SEVERE, null, ex);
    }
    this.mutex.unlock();
  }

  public void terminarLeer() {
    this.mutex.lock();
    this.n_lectores -= 1;
    if (this.n_lectores == 0) {
      this.escritores.signal();
    } else {
      this.lectores.signal();
    }
    this.mutex.unlock();
  }

  public void empezarEscribir() {
    this.mutex.lock();
    try {
      while (this.escribiendo) {
        this.escritores.await();
      }
      this.escribiendo = true;
    } catch (InterruptedException ex) {
      Logger.getLogger(Libro.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public void terminarEscribir() {
    this.escribiendo = false;
    this.lectores.signalAll();
    this.escritores.signalAll();
    this.mutex.unlock();
  }

}
