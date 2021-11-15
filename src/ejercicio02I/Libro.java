/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio02I;

import ejercicio02.*;
import java.util.concurrent.Semaphore;

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
  
}
