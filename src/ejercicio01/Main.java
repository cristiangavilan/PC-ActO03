/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio01;

/**
 *
 * @author Cristian
 */
public class Main {

  public static void main(String[] args) {
//    System.out.println("Hello World");

    Parte parte1 = new Parte(1);
    Parte parte2 = new Parte(2);
    Parte parte3 = new Parte(3);
    
    int maxCarpinteros1 = 4;
    int maxCarpinteros2 = 5;
    int maxCarpinteros3 = 3;
    
    Carpintero[] carpinteros1 = new Carpintero[maxCarpinteros1];
    Carpintero[] carpinteros2 = new Carpintero[maxCarpinteros2];
    Carpintero[] carpinteros3 = new Carpintero[maxCarpinteros3];
 
    for (int i = 0; i < maxCarpinteros1; i++) {
      carpinteros1[i] = new Carpintero("Carpintero:1-" + (i+1), parte1);
      new Thread(carpinteros1[i]).start();
    }
    for (int i = 0; i < maxCarpinteros2; i++) {
      carpinteros2[i] = new Carpintero("Carpintero:2-" + (i+1), parte2);
      new Thread(carpinteros2[i]).start();
    }
    for (int i = 0; i < maxCarpinteros3; i++) {
      carpinteros3[i] = new Carpintero("Carpintero:3-" + (i+1), parte3);
      new Thread(carpinteros3[i]).start();
    }
    
    ChePibe chepibe1 = new ChePibe("ChePibe1", parte1);
    ChePibe chepibe2 = new ChePibe("ChePibe2", parte2);
    ChePibe chepibe3 = new ChePibe("ChePibe3", parte3);
    
    new Thread(chepibe1).start();
    new Thread(chepibe2).start();
    new Thread(chepibe3).start();

  }

}
