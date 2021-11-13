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
public class ChePibe extends Empleado implements Runnable {
  private final Parte parte;
  private final LineaEnsamble lineaEnsamble;
  public static final String ANSI_GREEN = "\u001B[32m";

  public ChePibe(String name, Parte parte, LineaEnsamble lineaEnsamble) {
    super(name);
    this.parte = parte;
    this.lineaEnsamble = lineaEnsamble;
  }
   @Override
  public void run() {
   while (true) {
     try {
       this.parte.iniciarDisponerPieza();
       System.out.println(ANSI_GREEN + this.name + " dispone de: " + this.parte.toString());
       Thread.sleep((int) Math.floor(Math.random() * 2000));
       this.parte.finalizarDisponerPieza();
       this.lineaEnsamble.iniciarSubirParte(this.parte.toString());
       System.out.println(ANSI_GREEN + this.name + " sube a linea de produccion " + this.parte.toString());
       Thread.sleep((int) Math.floor(Math.random() * 2000));
       this.lineaEnsamble.finalizarSubirParte();
     } catch (InterruptedException ex) {
       Logger.getLogger(ChePibe.class.getName()).log(Level.SEVERE, null, ex);
     }
   }
  }
}
