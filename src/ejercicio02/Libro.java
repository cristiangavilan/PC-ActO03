/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio02;

import java.util.concurrent.Semaphore;

/**
 *
 * @author Cristian
 */
public class Libro {
  public Semaphore mutex1, mutex2, lectores, escritores;
  int n_lectores, n_escritores;

  public Libro() {
    this.mutex1 = new Semaphore(1);
    this.mutex2 = new Semaphore(1);
    this.lectores = new Semaphore(1);
    this.escritores = new Semaphore(1);
    
    this.n_lectores = 0;
    this.n_escritores = 0;
    
  }
  
}
