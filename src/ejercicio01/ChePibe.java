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
public class ChePibe implements Runnable {
  private final String name;
  private Parte parte;

  public ChePibe(String name, Parte parte) {
    this.name = name;
    this.parte = parte;
  }
   @Override
  public void run() {
   while (true) {
     try {
       this.parte.iniciarDisponerPieza();
       System.out.println(this.name + " dispone de: " + this.parte.toString());
       Thread.sleep((int) Math.floor(Math.random() * 2000));
       this.parte.finalizarDisponerPieza();
     } catch (InterruptedException ex) {
       Logger.getLogger(ChePibe.class.getName()).log(Level.SEVERE, null, ex);
     }
   }
  }
}
