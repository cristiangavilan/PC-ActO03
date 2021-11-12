/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio01;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cristian
 */
public class Parte {

  private final int id;
  private int avance;
  private final Lock mutex = new ReentrantLock(true);
  private final Condition colaboraciones = this.mutex.newCondition();
  private final Condition llevarAensamble = this.mutex.newCondition();
  private boolean terminada;

  public int getAvance() {
    return avance;
  }

  public boolean isTerminada() {
    return terminada;
  }

  public Parte(int id) {
    this.id = id;
    this.avance = 0;
    this.terminada = false;
  }

  @Override
  public String toString() {
    return "Parte N°:" + this.id;
  }

  public void iniciarAvance() {
    this.mutex.lock();
    while (this.isTerminada()) {
      try {
        this.colaboraciones.await();
      } catch (InterruptedException ex) {
        Logger.getLogger(Parte.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    this.avance += 1;
  }

  public void finalizarAvance() {
    if (this.avance == 100) {
      this.terminada = true;
      this.llevarAensamble.signal();
    } else {
      this.colaboraciones.signal();
    }
    this.mutex.unlock();
  }

  public void iniciarDisponerPieza() {
    this.mutex.lock();
    while (!this.isTerminada()) {
      try {
        this.llevarAensamble.await();
      } catch (InterruptedException ex) {
        Logger.getLogger(Parte.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    this.avance = 0;
    this.terminada = false;
  }

  public void finalizarDisponerPieza() {
    this.colaboraciones.signal();
    this.mutex.unlock();
  }

}
