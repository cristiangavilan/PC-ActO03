/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio01;

import java.util.concurrent.Semaphore;

/**
 *
 * @author Kolia
 */
public class LineaEnsamble {
    
    private Semaphore semParte1;
    private Semaphore semParte2;
    private Semaphore semParte3;
    private Semaphore semEspera1;
    private Semaphore semEspera2;
    private Semaphore semEspera3;
    private Semaphore semEnsambladores;
    
    private Partes  parteA;
    private Partes  parteB;
    private Partes  parteC;
    
    private int mueblesArmados;
    private int carpinterosA;
    private int carpinterosB;
    private int carpinterosC;
    private int ensambladores;

    public LineaEnsamble(int carpinterosA,int carpinterosB,int carpinterosC,int ensambladores) {
        this.semParte1 = new Semaphore(carpinterosA);
        this.semParte2 = new Semaphore(carpinterosB);
        this.semParte3 = new Semaphore(carpinterosC);
        this.semEnsambladores = new Semaphore(0);;
        this.parteA = null;
        this.parteB = null;
        this.parteC = null;
        this.mueblesArmados = 0;
        this.carpinterosA= carpinterosA;
        this.carpinterosA= carpinterosB;
        this.carpinterosA= carpinterosC;
        this.ensambladores= ensambladores;
    }
    
    
    
    
    
    
}
