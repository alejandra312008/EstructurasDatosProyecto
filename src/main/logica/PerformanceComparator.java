import java.util.*;

/**
 * Clase para comparar el rendimiento entre implementación manual y nativa
 * de estructuras de datos (Stack y Queue) y algoritmos de búsqueda de rutas
 */
public class PerformanceComparator {
    
    /**
     * Compara el rendimiento de StackManual vs ArrayDeque como pila
     */
    public void compareStackPerformance() {
        System.out.println("=== COMPARACIÓN DE RENDIMIENTO: PILA ===");
        System.out.println("StackManual vs ArrayDeque (como pila)");
        System.out.println();
        
        int[] testSizes = {1000, 10000, 100000};
        
        for (int size : testSizes) {
            System.out.println("Probando con " + size + " elementos:");
            
            // Test StackManual
            long startTime = System.nanoTime();
            StackManual<Integer> manualStack = new StackManual<>();
            for (int i = 0; i < size; i++) {
                manualStack.push(i);
            }
            for (int i = 0; i < size; i++) {
                manualStack.pop();
            }
            long manualTime = System.nanoTime() - startTime;
            
            // Test ArrayDeque como pila
            startTime = System.nanoTime();
            Deque<Integer> nativeStack = new ArrayDeque<>();
            for (int i = 0; i < size; i++) {
                nativeStack.push(i);
            }
            for (int i = 0; i < size; i++) {
                nativeStack.pop();
            }
            long nativeTime = System.nanoTime() - startTime;
            
            System.out.printf("  StackManual:  %8.2f ms%n", manualTime / 1_000_000.0);
            System.out.printf("  ArrayDeque:   %8.2f ms%n", nativeTime / 1_000_000.0);
            System.out.printf("  Diferencia:   %8.2f ms (%.1f%% %s)%n", 
                Math.abs(manualTime - nativeTime) / 1_000_000.0,
                Math.abs(manualTime - nativeTime) * 100.0 / Math.max(manualTime, nativeTime),
                manualTime < nativeTime ? "más rápido manual" : "más rápido nativo");
            System.out.println();
        }
    }
    
    /**
     * Compara el rendimiento de QueueManual vs ArrayDeque como cola
     */
    public void compareQueuePerformance() {
        System.out.println("=== COMPARACIÓN DE RENDIMIENTO: COLA ===");
        System.out.println("QueueManual vs ArrayDeque (como cola)");
        System.out.println();
        
        int[] testSizes = {1000, 10000, 100000};
        
        for (int size : testSizes) {
            System.out.println("Probando con " + size + " elementos:");
            
            // Test QueueManual
            long startTime = System.nanoTime();
            QueueManual<String> manualQueue = new QueueManual<>();
            for (int i = 0; i < size; i++) {
                manualQueue.enqueue("item" + i);
            }
            for (int i = 0; i < size; i++) {
                manualQueue.dequeue();
            }
            long manualTime = System.nanoTime() - startTime;
            
            // Test ArrayDeque como cola
            startTime = System.nanoTime();
            Queue<String> nativeQueue = new ArrayDeque<>();
            for (int i = 0; i < size; i++) {
                nativeQueue.offer("item" + i);
            }
            for (int i = 0; i < size; i++) {
                nativeQueue.poll();
            }
            long nativeTime = System.nanoTime() - startTime;
            
            System.out.printf("  QueueManual:  %8.2f ms%n", manualTime / 1_000_000.0);
            System.out.printf("  ArrayDeque:   %8.2f ms%n", nativeTime / 1_000_000.0);
            System.out.printf("  Diferencia:   %8.2f ms (%.1f%% %s)%n", 
                Math.abs(manualTime - nativeTime) / 1_000_000.0,
                Math.abs(manualTime - nativeTime) * 100.0 / Math.max(manualTime, nativeTime),
                manualTime < nativeTime ? "más rápido manual" : "más rápido nativo");
            System.out.println();
        }
    }
    
    /**
     * Compara el rendimiento de búsqueda de rutas entre implementación manual y nativa
     */
    public void comparePathFinding() {
        System.out.println("=== COMPARACIÓN DE RENDIMIENTO: BÚSQUEDA DE RUTAS ===");
        System.out.println("MatrixPathFinder vs MatrixPathFinderNative");
        System.out.println();
        
        // Crear matrices de diferentes tamaños
        char[][] smallMatrix = createTestMatrix(5, 5);
        char[][] mediumMatrix = createTestMatrix(10, 10);
        char[][] largeMatrix = createTestMatrix(20, 20);
        
        Point start = new Point(0, 0);
        Point endSmall = new Point(4, 4);
        Point endMedium = new Point(9, 9);
        Point endLarge = new Point(19, 19);
        
        // Test matriz pequeña
        System.out.println("Matriz 5x5:");
        testPathFindingPerformance(smallMatrix, start, endSmall);
        
        // Test matriz mediana
        System.out.println("Matriz 10x10:");
        testPathFindingPerformance(mediumMatrix, start, endMedium);
        
        // Test matriz grande
        System.out.println("Matriz 20x20:");
        testPathFindingPerformance(largeMatrix, start, endLarge);
    }
    
    /**
     * Prueba el rendimiento de búsqueda de rutas en una matriz específica
     */
    private void testPathFindingPerformance(char[][] matrix, Point start, Point end) {
        int iterations = 100; // Número de iteraciones para promediar
        
        // Test implementación manual
        long manualTime = 0;
        for (int i = 0; i < iterations; i++) {
            MatrixPathFinder manualFinder = new MatrixPathFinder(matrix);
            long startTime = System.nanoTime();
            List<Point> pathDFS = manualFinder.findPathDFS(start, end);
            List<Point> pathBFS = manualFinder.findPathBFS(start, end);
            manualTime += System.nanoTime() - startTime;
        }
        manualTime /= iterations;
        
        // Test implementación nativa
        long nativeTime = 0;
        for (int i = 0; i < iterations; i++) {
            MatrixPathFinderNative nativeFinder = new MatrixPathFinderNative(matrix);
            long startTime = System.nanoTime();
            List<Point> pathDFS = nativeFinder.findPathDFS(start, end);
            List<Point> pathBFS = nativeFinder.findPathBFS(start, end);
            nativeTime += System.nanoTime() - startTime;
        }
        nativeTime /= iterations;
        
        System.out.printf("  Manual:       %8.2f ms (promedio de %d iteraciones)%n", 
            manualTime / 1_000_000.0, iterations);
        System.out.printf("  Nativa:       %8.2f ms (promedio de %d iteraciones)%n", 
            nativeTime / 1_000_000.0, iterations);
        System.out.printf("  Diferencia:   %8.2f ms (%.1f%% %s)%n", 
            Math.abs(manualTime - nativeTime) / 1_000_000.0,
            Math.abs(manualTime - nativeTime) * 100.0 / Math.max(manualTime, nativeTime),
            manualTime < nativeTime ? "más rápido manual" : "más rápido nativo");
        System.out.println();
    }
    
    /**
     * Crea una matriz de prueba con obstáculos aleatorios
     */
    private char[][] createTestMatrix(int rows, int cols) {
        char[][] matrix = new char[rows][cols];
        Random random = new Random(42); // Semilla fija para resultados consistentes
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == 0 && j == 0) {
                    matrix[i][j] = 'S'; // Inicio
                } else if (i == rows - 1 && j == cols - 1) {
                    matrix[i][j] = 'E'; // Final
                } else if (random.nextDouble() < 0.2) { // 20% de obstáculos
                    matrix[i][j] = '#';
                } else {
                    matrix[i][j] = '.';
                }
            }
        }
        
        return matrix;
    }
    
    /**
     * Imprime un reporte completo de comparación
     */
    public void printReport() {
        System.out.println("=".repeat(60));
        System.out.println("REPORTE DE COMPARACIÓN: MANUAL vs NATIVA");
        System.out.println("=".repeat(60));
        System.out.println();
        
        compareStackPerformance();
        compareQueuePerformance();
        comparePathFinding();
        
        System.out.println("=".repeat(60));
        System.out.println("CONCLUSIONES:");
        System.out.println("=".repeat(60));
        System.out.println("1. COMPLEJIDAD TEÓRICA:");
        System.out.println("   - Ambas implementaciones mantienen O(1) amortizado");
        System.out.println("   - Operaciones básicas: push/pop/enqueue/dequeue");
        System.out.println();
        System.out.println("2. RENDIMIENTO EMPÍRICO:");
        System.out.println("   - ArrayDeque generalmente más rápido por optimizaciones del JDK");
        System.out.println("   - Diferencias más notorias en operaciones masivas");
        System.out.println();
        System.out.println("3. CLARIDAD Y MANTENIBILIDAD:");
        System.out.println("   - Código nativo: más conciso y legible");
        System.out.println("   - Menos líneas de código, menos bugs potenciales");
        System.out.println("   - Documentación oficial y comunidad amplia");
        System.out.println();
        System.out.println("4. MANEJO DE ERRORES:");
        System.out.println("   - Nativa: excepciones estándar del JDK");
        System.out.println("   - Manual: excepciones personalizadas (más control)");
        System.out.println();
        System.out.println("5. RECOMENDACIÓN PARA PRODUCCIÓN:");
        System.out.println("   - USAR ArrayDeque para la mayoría de casos");
        System.out.println("   - Implementación manual solo si necesitas control específico");
        System.out.println("   - ArrayDeque está probado, optimizado y mantenido por Oracle");
        System.out.println();
    }
}




