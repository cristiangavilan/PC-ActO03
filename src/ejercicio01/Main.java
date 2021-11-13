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
    
    LineaEnsamble lineaEnsamble = new LineaEnsamble();
    
    int maxCarpinteros1 = 4;
    int maxCarpinteros2 = 6;
    int maxCarpinteros3 = 3;
    int maxChePibes = 3; // uno para cada parte "Siempre debe ser 3"
    int maxEnsambladores = 5;
    
    Carpintero[] carpinteros1 = new Carpintero[maxCarpinteros1];
    Carpintero[] carpinteros2 = new Carpintero[maxCarpinteros2];
    Carpintero[] carpinteros3 = new Carpintero[maxCarpinteros3];
    ChePibe[] chePibes = new ChePibe[maxChePibes];
    Ensamblador[] ensambladores = new Ensamblador[maxEnsambladores];
 
    for (int i = 0; i < maxCarpinteros1; i++) {
      carpinteros1[i] = new Carpintero("CarpinteroDeParte1N°" + (i+1), parte1);
      new Thread(carpinteros1[i]).start();
    }
    for (int i = 0; i < maxCarpinteros2; i++) {
      carpinteros2[i] = new Carpintero("CarpinteroDeParte2N°" + (i+1), parte2);
      new Thread(carpinteros2[i]).start();
    }
    for (int i = 0; i < maxCarpinteros3; i++) {
      carpinteros3[i] = new Carpintero("CarpinteroDeParte1N°" + (i+1), parte3);
      new Thread(carpinteros3[i]).start();
    }
    
    for (int i = 0; i < maxChePibes; i++) {
      switch(i) {
        case 0: 
          chePibes[i] = new ChePibe("ChePibeDeParte1", parte1, lineaEnsamble);
          break;
        case 1: 
          chePibes[i] = new ChePibe("ChePibeDeParte2", parte2, lineaEnsamble);
          break;
        default:
          chePibes[i] = new ChePibe("ChePibeDeParte3", parte3, lineaEnsamble);
          break;
      }
      new Thread(chePibes[i]).start();
    }
    
    for (int i = 0; i < maxEnsambladores; i++) {
      ensambladores[i] = new Ensamblador("EnsambladorDeMuebles" + (i+1), lineaEnsamble);
      new Thread(ensambladores[i]).start();
    }
    
  }

}
