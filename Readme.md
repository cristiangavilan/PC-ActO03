![title](/assets/001.png "Uncoma")

# **Programación Concurrente**

## **Actividad Obligatoria: Pensar, interactuar, colaborar y proponer soluciones.**

### **Desarrollo: 10/11/2021 .. 15/11/2021**

> Integrantes: Cristian Gavilan, Nicolás Blanco, Javier A. Egidi Vega

> [Repositorio](https://github.com/cristiangavilan/PC-ActO03)

---

![002](/assets/002.png "Pensar") ![003](/assets/003.png "Colaborar")

1 - Dado el siguiente enunciado determinar, si existen:

a) Los objetos activos y pasivos.

> **Objetos pasivos:**
>
> - Parte (Esta clase es el objeto compartido de carpinteros y ChePibes para trabajar en una parte determinada )
> - LineaEnsamble ( Esta clase es el objeto compartido entre los ChePibes y Ensambladores para colocar y usar las partes para armar el mueble )
> - Empleado (Para determinar el name de todos los empleados)
>
> **Objetos Activos**
>
> - Carpintero (Encargado de trabajar en las partes)
> - ChePibe (Encargado de tomar las partes generadas por los carpinteros y subirlas a la linea de Ensamble)
> - Ensamblador (Toman las partes de la linea de ensamble y arman el mueble)
> - Main (Clase para lanzar el sistema)
>
> _NOTA: (Carpintero, ChePibe y Ensamblador heredan de Empleado e implementan Runnable)_

b) Los puntos de sincronización entre los hilos.

> _Carpintero_ -> **Parte** <- _ChePibe_ -> **LineaEnsable** <- _Ensamblador_

c) En qué estado se pueden encontrar los hilos.: indicar específicamente para cada hilo en particular

> **Carpintero**:
>
> - Activo: [Creando una parte]
> - Bloqueado: [Parte terminada y esperando que ChePibe la traslade a Linea de Ensamble]
>
> **ChePibe**:
>
> - Activo: [trasladando una parte a linea de ensamble]
> - Bloqueado: [Parte terminada en su poder y Linea de Ensamble completa]
>
> **Ensamblador**:
>
> - Activo: [Armando un mueble]
> - Bloqueado: [Esperando todas las partes necesarias en Linea de Ensamble para armar el mueble]

d) Sugerir un método de sincronización adecuado para el modelado de la situación.

> Se sugiere una solución con Locks y variables de condición para lograr la sincronización.

En una carpintería se realizan muebles ensamblando 3 partes, existen N1 carpinteros para realizar la parte 1, N2 carpinteros para realizar la parte 2, N3 carpinteros para la parte 3; mas N carpinteros que se encargan de ensamblar las 3 partes para armar el mueble.

Los encargados de ensamblar deben tomar una pieza de cada parte, juntar las mismas y **una vez ensambladas** los carpinteros que le dieron la parte pueden seguir trabajando (no antes).

En la carpintería sólo se armarán M muebles y no se podrán producir piezas de más. es decir deben producirse M piezas de parte 1, M piezas de parte 2 y M piezas de parte 3.

El armado debe hacerse en forma **concurrente**, sólo pueden esperarse entre los carpinteros de un mismo tipo cuando terminan una pieza hasta que los tome un ensamblador (es decir puede pensar que los ensambladores toman las piezas de a uno)

Todos los procesos deben terminar correctamente, no pueden quedar procesos colgados.

e) Qué sucede con la solución propuesta si ahora hay que considerar más concurrencia entre los carpinteros?. Es decir si los carpinteros que realizan las partes no necesitan esperar a que el mueble sea ensamblado para continuar con la producción de piezas.

> Para lograr este aumento de concurrencia entre los carpinteros se podría pensar que esta solución pudiera almacenar mas de un tipo de parte en la linea de ensamble o bien que la linea de ensamble tenga la posibilidad de contar con un buffer específico para cada tipo de partes.
>
> (Nota: _No implementado este apartado_)

---

2 - Considerar el problema de los Lectores / escritores y el pseudocodigo java siguiente, que utiliza semáforos binarios:

Variables compartidas:

```java
Semaphore mutex1(1), mutex2(1);
Semaphore lectores (1), escritores(1);
int n_lectores=0;
int n_escritores=0;
```

```java
Lector() {
    while (true) {
        lectores.wait();
        mutex1.acquire();
        n_lectores++;
        if (n_lectores==1)
            escritores.acquire();
        mutex1.release();
        lectores.release();

        Lectura del recurso

        mutex1.acquire();
        n_lectores--;
        if (n_lectores==0)
            escritores.release();
        mutex1.release();
    }
}
```

```java
Escritores() {
    while (true) {
        mutex2.acquire();
        n_escritores++;
        if (n_escritores==1)
            lectores.acquire();
        mutex2.release();
        escritores.release();

        Escritura en el recurso

        escritores.release();
        mutex2.acquire();
        n_escritores--;
        if (n_escritores==0)
            lectores.release();
        mutex2.release();
    }
}

```

I. Analizar si está libre de bloqueos. Justificar la respuesta. Corregir en caso de ser necesario

> La solución que se propone por para este ejercicio cuenta con cosas por corregir para poder poder realizar el trabajo sincronizado correctamente, No posee bloqueos, en el caso de ser un solo lector y uno solo escritor, pero si se produce starvation, con lo cual algunos hilos permanecen en inanición del uso de CPU. Por otra parte se están usando recursos innecesarios para hacer este modelo de sincronización: 4 semáforos y 2 variables contadoras de hilos. Ésto se pude lograr con 3 semáforos y una sola variable contadora de hilos.
>
> ~~lectores.wait();~~ ( no se puede usar **wait()** dado que no se tiene sincronizado el método **Lector()**. IlegallMonitorException )
>
> Con esto la solución de lector empezaría así:

```java
Lector() {
    while (true) {
        lector.acquire(); // se cambia por un acquire()
        ...

```

> Por otra parte en el método de los escritores las liberaciones de los semáforos antes de: _"la escritura en el recurso.."_ produce que no se contemple la exclusión mutua para los escritores, con lo cual difícilmente los lectores puedan progresar en su ejecución.
>
> Nuestra solución contempla la necesidad de usar semáforos de tal forma que el conjunto de los lectores en su totalidad, tengan la misma prioridad que pueda tomar cada escritor en su uso particular.

```java

Libro() {
    mutex = new Semaphore(1, true);
    lectores = new Semaphore(1, true);
    escritores = new Semaphore(1, true);
    n_lectores = 0;
}

Lector() {
    while (true) {
        mutex.acquire();            // semáforo Mutex para uso grupal contra cada escritor
        lectores.acquire();         // semáforo de uso para controlar la cantidad de hilos lectores
         n_lectores ++;             // contabilidad de hilos lectores
         if (n_lectores == 1)       // Necesito verificar el primer lector que igresa
            escritores.acquire();   // para no dejar entrar a ningun escritor
        lectores.release();         // libero para permitir dejar pasar a otros lectores
        mutex.release();            // libero para dejar trabajar a todos los hilos

        Lectura del recurso

        lectores.acquire();         // ingreso controlado de lectores para su contabilidad
         n_lectores --;             // decremento los hilos que ya pasaron por la lectura del recurso
         if (n_lectores == 0)       // busco el último lector
            escritores.release();   // para liberar a los escritores
        lectores.release();         // libero a los hilos lectores
      }
}

Escritores() {
    while (true) {
        mutex.acquire();            // semáforo Mutex para uso grupal contra el grupo de lectores
        escritores.acquire();       // semáforo de uso para controlar el uso en modo escritura

        Escritura en el recurso

        escritores.release();       // para liberar a otros escritores
        mutex.release();            // para liberar a todos los hilos (lectores / escritores)
    }
}

```

II. La sincronización se trabaja desde los hilos. Reestructurarlo para que la sincronización se trabaje desde el recurso compartido, manteniendo los semáforos binarios y la idea general. (tomando esto como base, NO empezarlo de 0)

> Se entrega la solución de acuerdo a los solicitado:

```java
public class Libro {
  public Semaphore mutex, lectores, escritores;
  int n_lectores;

  public Libro() {
    mutex = new Semaphore(1, true);
    lectores = new Semaphore(1, true);
    escritores = new Semaphore(1, true);
    n_lectores = 0;
  }

  public void empezarLeer() {
    mutex.acquire();
    lectores.acquire();
    n_lectores++;
    if (n_lectores == 1) {
        escritores.acquire();
    }
    lectores.release();
    mutex.release();
  }

  public void terminarLeer() {
    lectores.acquire();
    n_lectores--;
    if (n_lectores == 0) {
      escritores.release();
    }
    lectores.release();
  }

  public void empezarEscribir() {
    mutex.acquire();
    escritores.acquire();
  }

  public void terminarEscribir() {
    escritores.release();
    mutex.release();
  }

}
public class Lector {

  private String name;
  private Libro libro;

  public Lector(String name, Libro libro) {
    name = name;
    libro = libro;
  }

  public void run() {
    while (true) {
        libro.empezarLeer();

        System.out.println(name + " leyendo en el recurso ");
        Thread.sleep((int) Math.floor(Math.random() * 1000));

        libro.terminarLeer();

    }
  }
}

public class Escritor {
  private String name;
  private Libro libro;

  public Escritor(String name, Libro libro) {
    name = name;
    libro = libro;
  }

  public void run() {
    while (true) {

        libro.empezarEscribir();

        System.out.println(name + " escribiendo en el recurso");
        Thread.sleep((int) Math.floor(Math.random() * 2000));

        libro.terminarEscribir();

    }
  }
}


```

III. Se logra una buena distribución de trabajo entre los lectores y escritores? Justificar y mejorar en caso de ser necesario.

> Con esta solución se logra una buena distribución de trabajo dado que los semáforos son inicializados en true para garantizar la equidad en quien los solicita.

IV. Proponer otra solución con otro mecanismo de sincronización trabajado hasta ahora.

> Se propone una solución usando Locks con condicionales:

```java
public class Libro {

  private final Lock mutex;
  private final Condition lectores;
  private final Condition escritores;
  private boolean escribiendo;
  private int n_lectores;

  public Libro() {
    this.mutex = new ReentrantLock(true);
    this.lectores = this.mutex.newCondition();
    this.escritores = this.mutex.newCondition();
    this.n_lectores = 0;
  }

  public void empezarLeer() {
    this.mutex.lock();
    try {
      while (this.escribiendo) {
        this.lectores.await();
      }
      this.n_lectores += 1;
    } catch (InterruptedException ex) {
      Logger.getLogger(Libro.class.getName()).log(Level.SEVERE, null, ex);
    }
    this.mutex.unlock();
  }

  public void terminarLeer() {
    this.mutex.lock();
    this.n_lectores -= 1;
    if (this.n_lectores == 0) {
      this.escritores.signal();
    } else {
      this.lectores.signal();
    }
    this.mutex.unlock();
  }

  public void empezarEscribir() {
    this.mutex.lock();
    try {
      while (this.escribiendo) {
        this.escritores.await();
      }
      this.escribiendo = true;
    } catch (InterruptedException ex) {
      Logger.getLogger(Libro.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public void terminarEscribir() {
    this.escribiendo = false;
    this.lectores.signalAll();
    this.escritores.signalAll();
    this.mutex.unlock();
  }

}
public class Escritor implements Runnable {
  private final String name;
  private final Libro libro;

  public Escritor(String name, Libro libro) {
    this.name = name;
    this.libro = libro;
  }

  @Override
  public void run() {
    while (true) {
      try {

        this.libro.empezarEscribir();

        System.out.println(this.name + " escribiendo en el recurso");
        Thread.sleep((int) Math.floor(Math.random() * 2000));

        this.libro.terminarEscribir();

      } catch (InterruptedException ex) {
        Logger.getLogger(Escritor.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

  }
}

public class Lector implements Runnable {

  private final String name;
  private final Libro libro;

  public Lector(String name, Libro libro) {
    this.name = name;
    this.libro = libro;
  }

  @Override
  public void run() {
    while (true) {
      try {

        this.libro.empezarLeer();

        System.out.println(this.name + " leyendo en el recurso ");
        Thread.sleep((int) Math.floor(Math.random() * 1000));

        this.libro.terminarLeer();

      } catch (InterruptedException ex) {
        Logger.getLogger(Lector.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
}

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
```
