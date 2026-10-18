// Clase principal ejecutable que crea los objetos y lanza las hebras
public class Principal {
    // Punto de entrada de la aplicación en Java
    public static void main(String[] args) {
        // Instanciamos el recurso compartido indicando que solo existen 2 pistas
        Pistas pistasCompartidas = new Pistas(2);

        // Creamos las instancias de los cuatro vehículos según el enunciado
        Vehiculo v1 = new Vehiculo("Vehículo 1", pistasCompartidas);
        Vehiculo v2 = new Vehiculo("Vehículo 2", pistasCompartidas);
        Vehiculo v3 = new Vehiculo("Vehículo 3", pistasCompartidas);
        Vehiculo v4 = new Vehiculo("Vehículo 4", pistasCompartidas);

        // Iniciamos la ejecución concurrente de las cuatro hebras
        v1.start();
        v2.start();
        v3.start();
        v4.start();

        try {
            // Esperamos a que todos los vehículos terminen sus pruebas antes de finalizar main
            v1.join();
            v2.join();
            v3.join();
            v4.join();
        } catch (InterruptedException e) {
            // Manejo de la interrupción del hilo principal
            Thread.currentThread().interrupt();
        }
    }
}