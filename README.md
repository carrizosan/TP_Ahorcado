# TP_Ahorcado


* Diferencias entre Runnable y Thread:
	
	Thread es una clase de la que podemos extender para utilizar hilos. Mientras que Runnable es una
	interfaz que utilizamos con el mismo fin, y que nos obliga a implementar el m�todo run().
	La principal diferencia es que si extendemos nuestra clase de Thread, perdemos la posibilidad de
	extender de alguna otra clase, y los beneficios de la herencia ya que Java no permite herencia
	m�ltiple. En cambio si implementamos la interfaz Runnable, todavia conservamos la posibilidad de
	usar la herencia, y ademas podemos implementar m�ltiples interfaces.
	Otra diferencia es que cuando extendemos de Thread, cada hilo de ejecuci�n crea un objeto �nico.
	En cambio al implementar Runnable, todos los hilos van a compartir un mismo objeto.
	
* Ciclo de vida de un Thread:

	Los threads pueden tener varios estados:
	
	- New: Ni bien se crea el hilo, el estado que toma es new. Todavia no se ha ejecutado el m�todo
	start(). En este punto el hilo no esta vivo. Se produce una excepci�n si se intenta utilizar un
	m�todo diferente de start().
	
	- Runnable: El thread puede estar ejecutandose siempre que se le haya asignado tiempo en la CPU.
	Se pone en este estado luego de ejecutar el m�todo start(). Los S.O. encapsulan los estados de 
	"ready" y "running" dentro de este estado.

	- Blocked: Un hilo en estado Runnable, pasa a estado bloqueado cuando intenta realizar una tarea
	por la cual tiene que esperar a que se complete otra.

	- Waiting: Un hilo pasa al estado waiting por varias razones, la m�s com�n es por que se llam� al
	m�todo wait(). Comunmente cuando aplicamos algoritmos de exclusi�n mutua. Una vez que sale de
	este estado, pasa nuevamente a Runnable.

	- Timed Waiting: Cuando se le proporciona al hilo un intervalo de espera determinado, por ejemplo
	al llamar a los metodos wait(long millis) o sleep(long millis).

	- Terminated: Tambien llamado Dead. Sucede cuando el hilo completa el m�todo run(), o es
	interrumpido intencionalmente con m�todos como stop() o interrupt().


* Explique los m�todos : start, sleep, yield, join:
	
	- start(): D� la orden para que se cree un contexto de hilo del sistema, y comience su ejecuci�n.
	Inmediatamente luego del m�todo start, se ejecutar� el m�todo run(). El hilo pasa del estado new
	al estado Runnable.

	- sleep(): Pone un hilo a dormir por un tiempo determinado, es decir, el hilo se pone en estado
	Timed Waiting. Recibe por parametro un long de los milisegundos que se quiere dormir el hilo. 
	Una vez que pase el tiempo indicado, el hilo volvera al estado Runnable. Existe una sobrecarga del
	m�todo que permite recibir nanosegundos tambi�n.

	- yield(): Este m�todo hace que la maquina virtual de java cambie de contexto entre el hilo actual
	y el siguiente hilo ejecutable disponible. Es una manera de asegurar que los hilos con menor
	prioridad no sufran inanici�n.

	- join(): Provoca que el hilo actual espere al hilo sobre el que se hace join, una vez que termina
	se unifican ambos hilos en uno.



############ Las reglas de este ahorcado: ############ 
	
	� Se puede ejecutar la aplicaci�n con tantos jugadores como se quiera, basta con agregarlos en el 
	asList, al momento de cargar la lista de jugadores.

	� Se han implementado hilos como es requerido para la ejecuci�n de los turnos de los jugadores.
	Cada jugador har� la misma cantidad de turnos que los dem�s, excepto que el juego termine e impida
	que algunos jugadores completen su turno.

	� Se aplican m�todos sincronizados para garantizar la exclusi�n mutua en zonas cr�ticas: Obtenci�n
	de letras (Evita que dos hilos vayan a buscar la misma letra al mismo tiempo y mantiene el orden de
	los turnos), y formaci�n parcial de la palabra junto con obtenci�n de la palabra objetivo (Evita que
	un hilo quiera acceder a una palabra mientras que otro la est� modificando).
	Puede variar el orden en el cu�l los hilos ejecutan su turno, pero no habr� inanici�n. Ej: turnos
	de hilos A, B y C :  A-B-C / A-B-C / B-C-A / C-A-B. Pero como se ve, todos ejecutan la misma cantidad
	de turnos.

	� La palabra se obtiene aleatoriamente de la base de datos.

	� Cada Jugador inicia con 7 vidas y 0 puntos. En su turno ira a buscar una letra aleatoria, y se
	 verificar� si la letra est� en la palabra objetivo. Si acierta la letra, suma un punto, si no
	acierta pierde una vida. La letra es eliminada del abecedario para que otro jugador no pueda repetirla
	
	� Tanto el abecedario, la palabra objetivo, y la palabra parcial (que se est� formando), son 
	compartidas por la totalidad de los jugadores.

	� El juego termina cuando:
		- Un jugador coloca la ultima letra faltante para completar la palabra (no significa que gan�)
		- Un jugador se queda sin vidas, por mas que los dem�s sigan teniendo vidas.
		- Se terminan las letras del abecedario (No deberia pasar si estan todas las letras
		cargadas, pero est� contemplada la posibilidad).

	� Una vez terminado el juego, ganar� el jugador que m�s puntos haya conseguido. En caso de empate,
	el ganador ser� el que m�s vidas tenga entre los jugadores que hayan empatado. Si la igualdad 
	persiste, habr� un empate (2 o m�s jugadores con la misma cantidad de puntos y vidas). Se mostrar�n
	el/los ganador/es por consola.

	� El/los ganador/es persistiran en base de datos.

	

	











