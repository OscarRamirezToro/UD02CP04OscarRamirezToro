// Clase que representa a cada vehículo concurrente heredando de Thread
class Vehiculo extends Thread {
    // Atributo con el nombre identificador del vehículo (ej: "Vehículo 1")
    private final String nombre;
    // Atributo con la referencia a la instancia compartida de Pistas
    private final Pistas pistas;

    // Constructor que recibe el nombre y el recurso compartido de pistas
    public Vehiculo(String nombre, Pistas pistas) {
        // Guardamos el nombre del vehículo
        this.nombre = nombre;
        // Guardamos la referencia compartida a las pistas
        this.pistas = pistas;
    }

    // Método principal que ejecuta el hilo al ponerse en marcha
    @Override
    public void run() {
        // 1. Intentar acceder a una pista libre mediante el método sincronizado
        int indicePista = pistas.ocuparPista();

        // Número visual de la pista (sumamos 1 porque los arrays empiezan en 0)
        int numeroPista = indicePista + 1;

        // Imprimir mensaje obligatorio indicando que el vehículo ha entrado en la pista
        System.out.println(this.nombre + " ha entrado en la pista " + numeroPista + ".");

        // Generar un tiempo aleatorio de prueba entre 2 y 5 segundos (en milisegundos: 2000 a 5000)
        long tiempoSimulacionMs = 2000 + (long) (Math.random() * 3000);

        // Guardamos el instante exacto de inicio de la prueba para medir el tiempo real transcurrido
        long inicioPrueba = System.currentTimeMillis();

        try {
            // Dormimos el hilo para simular que el coche está rodando en el circuito
            Thread.sleep(tiempoSimulacionMs);
        } catch (InterruptedException e) {
            // Manejamos posible interrupción del hilo
            Thread.currentThread().interrupt();
        }

        // Guardamos el instante exacto en el que finaliza la prueba
        long finPrueba = System.currentTimeMillis();

        // Calculamos los segundos transcurridos con precisión de milisegundos (decimales)
        double segundosTotales = (finPrueba - inicioPrueba) / 1000.0;

        // Imprimir mensaje obligatorio con el tiempo exacto que duró la prueba
        System.out.println(this.nombre + " completó la prueba en " + segundosTotales + " segundos.");

        // 2. Liberar la pista en el monitor compartido para que otro vehículo pueda entrar
        pistas.liberarPista(indicePista);

        // Imprimir mensaje obligatorio informando de la salida de la pista
        System.out.println(this.nombre + " ha salido de la pista " + numeroPista + ".");
    }
}