![title](/assets/001.png "Uncoma")

# **Programación Concurrente**

## **Actividad Obligatoria: Pensar, interactuar, colaborar y proponer soluciones.**

### **Desarrollo: 10/11/2021 .. 15/11/2021**

> Integrantes: Cristian Gavilan, ...

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

II. La sincronización se trabaja desde los hilos. Reestructurarlo para que la sincronización se trabaje desde el recurso compartido, manteniendo los semáforos binarios y la idea general. (tomando esto como base, NO empezarlo de 0)

III. Se logra una buena distribución de trabajo entre los lectores y escritores? Justificar y mejorar en caso de ser necesario.

IV. Proponer otra solución con otro mecanismo de sincronización trabajado hasta ahora.
