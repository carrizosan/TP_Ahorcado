# TP_Ahorcado


* Diferencias entre Runnable y Thread:
	
	Thread es una clase de la que podemos extender para utilizar hilos. Mientras que Runnable es una
	interfaz que utilizamos con el mismo fin, y que nos obliga a implementar el método run().
	La principal diferencia es que si extendemos nuestra clase de Thread, perdemos la posibilidad de
	extender de alguna otra clase, y los beneficios de la herencia ya que Java no permite herencia
	múltiple. En cambio si implementamos la interfaz Runnable, todavia conservamos la posibilidad de
	usar la herencia, y ademas podemos implementar múltiples interfaces.
	Otra diferencia es que cuando extendemos de Thread, cada hilo de ejecución crea un objeto único.
	En cambio al implementar Runnable, todos los hilos van a compartir un mismo objeto.
	
* Ciclo de vida de un Thread:

	Los threads pueden tener varios estados:
	
	- New: Ni bien se crea el hilo, el estado que toma es new. Todavia no se ha ejecutado el método
	start(). En este punto el hilo no esta vivo. Se produce una excepción si se intenta utilizar un
	método diferente de start().
	
	- Runnable: El thread puede estar ejecutandose siempre que se le haya asignado tiempo en la CPU.
	Se pone en este estado luego de ejecutar el método start(). Los S.O. encapsulan los estados de 
	"ready" y "running" dentro de este estado.

	- Blocked: Un hilo en estado Runnable, pasa a estado bloqueado cuando intenta realizar una tarea
	por la cual tiene que esperar a que se complete otra.

	- Waiting: Un hilo pasa al estado waiting por varias razones, la más común es por que se llamó al
	método wait(). Comunmente cuando aplicamos algoritmos de exclusión mutua. Una vez que sale de
	este estado, pasa nuevamente a Runnable.

	- Timed Waiting: Cuando se le proporciona al hilo un intervalo de espera determinado, por ejemplo
	al llamar a los metodos wait(long millis) o sleep(long millis).

	- Terminated: Tambien llamado Dead. Sucede cuando el hilo completa el método run(), o es
	interrumpido intencionalmente con métodos como stop() o interrupt().


* Explique los métodos : start, sleep, yield, join:
	
	- start(): Dá la orden para que se cree un contexto de hilo del sistema, y comience su ejecución.
	Inmediatamente luego del método start, se ejecutará el método run(). El hilo pasa del estado new
	al estado Runnable.

	- sleep(): Pone un hilo a dormir por un tiempo determinado, es decir, el hilo se pone en estado
	Timed Waiting. Recibe por parametro un long de los milisegundos que se quiere dormir el hilo. 
	Una vez que pase el tiempo indicado, el hilo volvera al estado Runnable. Existe una sobrecarga del
	método que permite recibir nanosegundos también.

	- yield(): Este método hace que la maquina virtual de java cambie de contexto entre el hilo actual
	y el siguiente hilo ejecutable disponible. Es una manera de asegurar que los hilos con menor
	prioridad no sufran inanición.

	- join(): Provoca que el hilo actual espere al hilo sobre el que se hace join, una vez que termina
	se unifican ambos hilos en uno.



############ Las reglas de este ahorcado: ############ 
	
	• Se puede ejecutar la aplicación con tantos jugadores como se quiera, basta con agregarlos en el 
	asList, al momento de cargar la lista de jugadores.

	• Se han implementado hilos como es requerido para la ejecución de los turnos de los jugadores.
	Cada jugador hará la misma cantidad de turnos que los demás, excepto que el juego termine e impida
	que algunos jugadores completen su turno.

	• Se aplican métodos sincronizados para garantizar la exclusión mutua en zonas críticas: Obtención
	de letras (Evita que dos hilos vayan a buscar la misma letra al mismo tiempo y mantiene el orden de
	los turnos), y formación parcial de la palabra junto con obtención de la palabra objetivo (Evita que
	un hilo quiera acceder a una palabra mientras que otro la está modificando).
	Puede variar el orden en el cuál los hilos ejecutan su turno, pero no habrá inanición. Ej: turnos
	de hilos A, B y C :  A-B-C / A-B-C / B-C-A / C-A-B. Pero como se ve, todos ejecutan la misma cantidad
	de turnos.

	• La palabra se obtiene aleatoriamente de la base de datos.

	• Cada Jugador inicia con 7 vidas y 0 puntos. En su turno ira a buscar una letra aleatoria, y se
	 verificará si la letra está en la palabra objetivo. Si acierta la letra, suma un punto, si no
	acierta pierde una vida. La letra es eliminada del abecedario para que otro jugador no pueda repetirla
	
	• Tanto el abecedario, la palabra objetivo, y la palabra parcial (que se está formando), son 
	compartidas por la totalidad de los jugadores.

	• El juego termina cuando:
		- Un jugador coloca la ultima letra faltante para completar la palabra (no significa que ganó)
		- Un jugador se queda sin vidas, por mas que los demás sigan teniendo vidas.
		- Se terminan las letras del abecedario (No deberia pasar si estan todas las letras
		cargadas, pero está contemplada la posibilidad).

	• Una vez terminado el juego, ganará el jugador que más puntos haya conseguido. En caso de empate,
	el ganador será el que más vidas tenga entre los jugadores que hayan empatado. Si la igualdad 
	persiste, habrá un empate (2 o más jugadores con la misma cantidad de puntos y vidas). Se mostrarán
	el/los ganador/es por consola.

	• El/los ganador/es persistiran en base de datos.

	

	











