// Clase que gestiona el recurso compartido: las pistas de prueba
class Pistas {
    // Array de booleanos para controlar el estado de cada pista: true = libre, false = ocupada
    private final boolean[] disponibles;

    // Constructor que inicializa el array con el número de pistas indicado (en este caso 2)
    public Pistas(int numeroPistas) {
        // Reservamos memoria para el array de pistas
        this.disponibles = new boolean[numeroPistas];
        // Al arrancar, marcamos todas las pistas como disponibles (true)
        for (int i = 0; i < disponibles.length; i++) {
            this.disponibles[i] = true;
        }
    }

    // Método sincronizado para que un vehículo solicite y reserve una pista libre
    public synchronized int ocuparPista() {
        // Variable para almacenar el índice de la pista asignada (-1 significa ninguna libre aún)
        int pistaAsignada = -1;

        // Bucle while para comprobar si hay alguna pista libre; si no la hay, se espera
        while (pistaAsignada == -1) {
            // Recorremos el array buscando la primera pista con valor true
            for (int i = 0; i < disponibles.length; i++) {
                if (disponibles[i]) {
                    // Si encontramos una libre, guardamos su índice
                    pistaAsignada = i;
                    // La marcamos inmediatamente como ocupada (false)
                    disponibles[i] = false;
                    // Salimos del bucle de búsqueda
                    break;
                }
            }

            // Si tras revisar todas las pistas no se encontró ninguna libre
            if (pistaAsignada == -1) {
                try {
                    // El hilo libera el cerrojo de sincronización y se duerme hasta que otra hebra avise
                    wait();
                } catch (InterruptedException e) {
                    // En caso de interrupción forzada del hilo, restauramos el flag de interrupción
                    Thread.currentThread().interrupt();
                }
            }
        }

        // Retornamos el índice de la pista que ha ocupado el vehículo
        return pistaAsignada;
    }

    // Método sincronizado para liberar la pista una vez finalizado el test
    public synchronized void liberarPista(int indicePista) {
        // Marcamos la pista de nuevo como disponible (true)
        this.disponibles[indicePista] = true;
        // Despertamos a los hilos que estén esperando en wait() para que compitan por la pista libre
        notifyAll();
    }
}