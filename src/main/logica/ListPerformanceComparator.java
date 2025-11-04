import java.util.*;

/**
 * Clase para comparar el rendimiento entre implementación manual y nativa
 * de listas simples y dobles
 * Mide: inserciones/eliminaciones en cabeza y cola para n=1000, n=10000
 */
public class ListPerformanceComparator {
    
    /**
     * Compara el rendimiento de SinglyLinkedList vs LinkedList nativa
     */
    public void compareSinglyLinkedListPerformance() {
        System.out.println("=== COMPARACIÓN DE RENDIMIENTO: LISTA SIMPLE ===");
        System.out.println("SinglyLinkedList (Manual) vs LinkedList (Nativa)");
        System.out.println();
        
        int[] testSizes = {1000, 10000};
        
        for (int size : testSizes) {
            System.out.println("Probando con " + size + " elementos:");
            
            // Test addFirst (cabeza)
            System.out.println("  Operación: addFirst (insertar en cabeza)");
            long manualTime = testAddFirstManual(size);
            long nativeTime = testAddFirstNative(size);
            printComparison("SinglyLinkedList", "LinkedList", manualTime, nativeTime);
            
            // Test addLast (cola)
            System.out.println("  Operación: addLast (insertar en cola)");
            manualTime = testAddLastManual(size);
            nativeTime = testAddLastNative(size);
            printComparison("SinglyLinkedList", "LinkedList", manualTime, nativeTime);
            
            // Test removeFirst (cabeza)
            System.out.println("  Operación: removeFirst (remover de cabeza)");
            manualTime = testRemoveFirstManual(size);
            nativeTime = testRemoveFirstNative(size);
            printComparison("SinglyLinkedList", "LinkedList", manualTime, nativeTime);
            
            // Test removeLast (cola)
            System.out.println("  Operación: removeLast (remover de cola)");
            manualTime = testRemoveLastManual(size);
            nativeTime = testRemoveLastNative(size);
            printComparison("SinglyLinkedList", "LinkedList", manualTime, nativeTime);
            
            System.out.println();
        }
    }
    
    /**
     * Compara el rendimiento de DoublyLinkedList vs LinkedList nativa
     */
    public void compareDoublyLinkedListPerformance() {
        System.out.println("=== COMPARACIÓN DE RENDIMIENTO: LISTA DOBLE ===");
        System.out.println("DoublyLinkedList (Manual) vs LinkedList (Nativa)");
        System.out.println();
        
        int[] testSizes = {1000, 10000};
        
        for (int size : testSizes) {
            System.out.println("Probando con " + size + " elementos:");
            
            // Test addFirst (cabeza)
            System.out.println("  Operación: addFirst (insertar en cabeza)");
            long manualTime = testAddFirstDoublyManual(size);
            long nativeTime = testAddFirstNative(size);
            printComparison("DoublyLinkedList", "LinkedList", manualTime, nativeTime);
            
            // Test addLast (cola)
            System.out.println("  Operación: addLast (insertar en cola)");
            manualTime = testAddLastDoublyManual(size);
            nativeTime = testAddLastNative(size);
            printComparison("DoublyLinkedList", "LinkedList", manualTime, nativeTime);
            
            // Test removeFirst (cabeza)
            System.out.println("  Operación: removeFirst (remover de cabeza)");
            manualTime = testRemoveFirstDoublyManual(size);
            nativeTime = testRemoveFirstNative(size);
            printComparison("DoublyLinkedList", "LinkedList", manualTime, nativeTime);
            
            // Test removeLast (cola)
            System.out.println("  Operación: removeLast (remover de cola)");
            manualTime = testRemoveLastDoublyManual(size);
            nativeTime = testRemoveLastNative(size);
            printComparison("DoublyLinkedList", "LinkedList", manualTime, nativeTime);
            
            System.out.println();
        }
    }
    
    // Métodos de prueba para SinglyLinkedList
    private long testAddFirstManual(int size) {
        long startTime = System.nanoTime();
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        for (int i = 0; i < size; i++) {
            list.addFirst(i);
        }
        return System.nanoTime() - startTime;
    }
    
    private long testAddLastManual(int size) {
        long startTime = System.nanoTime();
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        for (int i = 0; i < size; i++) {
            list.addLast(i);
        }
        return System.nanoTime() - startTime;
    }
    
    private long testRemoveFirstManual(int size) {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        for (int i = 0; i < size; i++) {
            list.addLast(i);
        }
        long startTime = System.nanoTime();
        for (int i = 0; i < size; i++) {
            list.removeFirst();
        }
        return System.nanoTime() - startTime;
    }
    
    private long testRemoveLastManual(int size) {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        for (int i = 0; i < size; i++) {
            list.addLast(i);
        }
        long startTime = System.nanoTime();
        for (int i = 0; i < size; i++) {
            list.removeLast();
        }
        return System.nanoTime() - startTime;
    }
    
    // Métodos de prueba para DoublyLinkedList
    private long testAddFirstDoublyManual(int size) {
        long startTime = System.nanoTime();
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        for (int i = 0; i < size; i++) {
            list.addFirst(i);
        }
        return System.nanoTime() - startTime;
    }
    
    private long testAddLastDoublyManual(int size) {
        long startTime = System.nanoTime();
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        for (int i = 0; i < size; i++) {
            list.addLast(i);
        }
        return System.nanoTime() - startTime;
    }
    
    private long testRemoveFirstDoublyManual(int size) {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        for (int i = 0; i < size; i++) {
            list.addLast(i);
        }
        long startTime = System.nanoTime();
        for (int i = 0; i < size; i++) {
            list.removeFirst();
        }
        return System.nanoTime() - startTime;
    }
    
    private long testRemoveLastDoublyManual(int size) {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        for (int i = 0; i < size; i++) {
            list.addLast(i);
        }
        long startTime = System.nanoTime();
        for (int i = 0; i < size; i++) {
            list.removeLast();
        }
        return System.nanoTime() - startTime;
    }
    
    // Métodos de prueba para LinkedList nativa
    private long testAddFirstNative(int size) {
        long startTime = System.nanoTime();
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            list.addFirst(i);
        }
        return System.nanoTime() - startTime;
    }
    
    private long testAddLastNative(int size) {
        long startTime = System.nanoTime();
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            list.addLast(i);
        }
        return System.nanoTime() - startTime;
    }
    
    private long testRemoveFirstNative(int size) {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            list.addLast(i);
        }
        long startTime = System.nanoTime();
        for (int i = 0; i < size; i++) {
            list.removeFirst();
        }
        return System.nanoTime() - startTime;
    }
    
    private long testRemoveLastNative(int size) {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            list.addLast(i);
        }
        long startTime = System.nanoTime();
        for (int i = 0; i < size; i++) {
            list.removeLast();
        }
        return System.nanoTime() - startTime;
    }
    
    /**
     * Imprime la comparación de tiempos
     */
    private void printComparison(String manualName, String nativeName, long manualTime, long nativeTime) {
        System.out.printf("    %-20s %8.2f ms%n", manualName + ":", manualTime / 1_000_000.0);
        System.out.printf("    %-20s %8.2f ms%n", nativeName + ":", nativeTime / 1_000_000.0);
        System.out.printf("    %-20s %8.2f ms (%.1f%% %s)%n", 
            "Diferencia:",
            Math.abs(manualTime - nativeTime) / 1_000_000.0,
            Math.abs(manualTime - nativeTime) * 100.0 / Math.max(manualTime, nativeTime),
            manualTime < nativeTime ? "más rápido manual" : "más rápido nativo");
    }
    
    /**
     * Imprime un reporte completo de comparación de listas
     */
    public void printReport() {
        System.out.println("=".repeat(70));
        System.out.println("REPORTE DE COMPARACIÓN: LISTAS MANUALES vs NATIVAS");
        System.out.println("=".repeat(70));
        System.out.println();
        
        compareSinglyLinkedListPerformance();
        compareDoublyLinkedListPerformance();
        
        System.out.println("=".repeat(70));
        System.out.println("ANÁLISIS DE COMPLEJIDAD:");
        System.out.println("=".repeat(70));
        System.out.println();
        System.out.println("LISTA SIMPLE (SinglyLinkedList):");
        System.out.println("  - addFirst:     O(1) - inserción en cabeza");
        System.out.println("  - addLast:      O(n) - requiere recorrer toda la lista");
        System.out.println("  - removeFirst:  O(1) - remoción de cabeza");
        System.out.println("  - removeLast:   O(n) - requiere recorrer hasta el final");
        System.out.println();
        System.out.println("LISTA DOBLE (DoublyLinkedList):");
        System.out.println("  - addFirst:     O(1) - inserción en cabeza");
        System.out.println("  - addLast:       O(1) - inserción en cola (tiene tail)");
        System.out.println("  - removeFirst:  O(1) - remoción de cabeza");
        System.out.println("  - removeLast:   O(1) - remoción de cola (tiene tail)");
        System.out.println();
        System.out.println("LINKEDLIST NATIVA (Java):");
        System.out.println("  - addFirst:     O(1) - doblemente enlazada");
        System.out.println("  - addLast:       O(1) - doblemente enlazada");
        System.out.println("  - removeFirst:  O(1) - doblemente enlazada");
        System.out.println("  - removeLast:   O(1) - doblemente enlazada");
        System.out.println();
        System.out.println("=".repeat(70));
        System.out.println("CONCLUSIONES:");
        System.out.println("=".repeat(70));
        System.out.println();
        System.out.println("1. COMPLEJIDAD TEÓRICA:");
        System.out.println("   - Lista simple: O(1) para cabeza, O(n) para cola");
        System.out.println("   - Lista doble: O(1) para ambas operaciones");
        System.out.println("   - LinkedList nativa: O(1) para todas (doblemente enlazada)");
        System.out.println();
        System.out.println("2. RENDIMIENTO EMPÍRICO:");
        System.out.println("   - LinkedList nativa generalmente más rápida por optimizaciones");
        System.out.println("   - Diferencias más notorias en operaciones masivas (n=10000)");
        System.out.println("   - Lista doble manual comparable a nativa en operaciones O(1)");
        System.out.println();
        System.out.println("3. LEGIBILIDAD Y MANTENIBILIDAD:");
        System.out.println("   - Código nativo: más conciso, menos propenso a errores");
        System.out.println("   - Implementación manual: mayor control y comprensión");
        System.out.println("   - Documentación oficial disponible para LinkedList");
        System.out.println();
        System.out.println("4. CASOS DE USO:");
        System.out.println("   - Lista Simple: cuando solo necesitas recorrer en una dirección");
        System.out.println("                  (historial secuencial, logs, intentos de juego)");
        System.out.println("   - Lista Doble: cuando necesitas navegar en ambas direcciones");
        System.out.println("                  (historial navegable, reproductor de música, undo/redo)");
        System.out.println();
        System.out.println("5. RECOMENDACIÓN:");
        System.out.println("   - USAR LinkedList nativa para producción (probada y optimizada)");
        System.out.println("   - Implementación manual útil para aprendizaje y casos específicos");
        System.out.println("   - Elegir lista simple si solo necesitas recorrer hacia adelante");
        System.out.println("   - Elegir lista doble si necesitas navegación bidireccional");
        System.out.println();
    }
}

