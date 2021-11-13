/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio01;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kolia
 */
public class LineaEnsamble {
    private int avance;
    
    private ArrayList<String> lineaEmsamble;
    private final Lock mutex = new ReentrantLock(true);
    private final Condition agregarParte = this.mutex.newCondition();
    private final Condition hacerMueble = this.mutex.newCondition();

    public int getAvance() {
      return avance;
    }
    
    public LineaEnsamble() {
      this.lineaEmsamble = new ArrayList<String>();
      this.avance = 0;
    }
    
    public void iniciarSubirParte(String parte) {
      this.mutex.lock();
      while(this.lineaEmsamble.contains(parte)) {
        try {
          this.agregarParte.await();
        } catch (InterruptedException ex) {
          Logger.getLogger(LineaEnsamble.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      this.lineaEmsamble.add(parte);
    }
    
    public void finalizarSubirParte() {
      if (this.lineaEmsamble.size() == 3) {
        this.hacerMueble.signal();
      } else {
        this.agregarParte.signal();
      }
      this.mutex.unlock();
    }
    
    public void iniciarMueble() {
      this.mutex.lock();
      while(this.lineaEmsamble.size() < 3) {
        try {
          this.hacerMueble.await();
        } catch (InterruptedException ex) {
          Logger.getLogger(LineaEnsamble.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      this.avance += 10;
    }
    public void finalizarMueble() {
      if (this.avance < 100) {
        this.hacerMueble.signal();
      } else {
        this.lineaEmsamble.clear();
        this.avance = 0;
        this.agregarParte.signal();
      }
      this.mutex.unlock();
    }
    
}
