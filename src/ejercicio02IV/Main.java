/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio02IV;

/**
 *
 * @author Cristian
 */
public class Main {
  public static void main(String[] args) {
    Libro editorial = new Libro();
    int maxLectores = 5;
    int maxEscritores = 2;
    
    Lector[] lectores = new Lector[maxLectores];
    Escritor[] escritores = new Escritor[maxEscritores];
    
    for (int i = 0; i < maxEscritores; i++) {
      escritores[i] = new Escritor("Escritor[" + (i + 1) + "]", editorial);
      new Thread(escritores[i]).start();
    }
    
    for (int i = 0; i < maxLectores; i++) {
      lectores[i] = new Lector("Lector[" + (i + 1) + "]", editorial);
      new Thread(lectores[i]).start();
    }
    
  }
  
}
