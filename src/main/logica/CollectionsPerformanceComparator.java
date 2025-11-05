import java.util.*;

/**
 * Comparador de rendimiento entre diferentes implementaciones de colecciones
 * Mide: ArrayList vs LinkedList, HashSet vs TreeSet, HashMap vs TreeMap
 * Para n=10^4 y n=10^5 elementos
 */
public class CollectionsPerformanceComparator {
    
    /**
     * Compara rendimiento de inserción masiva: ArrayList vs LinkedList
     */
    public void compareListInsertion() {
        System.out.println("=== COMPARACIÓN: LIST INSERTION ===");
        System.out.println("ArrayList vs LinkedList");
        System.out.println();
        
        int[] sizes = {10_000, 100_000};
        
        for (int n : sizes) {
            System.out.println("Probando con " + n + " elementos:");
            
            // ArrayList: inserción al final
            long startTime = System.nanoTime();
            List<Integer> arrayList = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                arrayList.add(i);
            }
            long arrayListTime = System.nanoTime() - startTime;
            
            // LinkedList: inserción al final
            startTime = System.nanoTime();
            List<Integer> linkedList = new LinkedList<>();
            for (int i = 0; i < n; i++) {
                linkedList.add(i);
            }
            long linkedListTime = System.nanoTime() - startTime;
            
            printComparison("ArrayList", "LinkedList", arrayListTime, linkedListTime);
            
            // ArrayList: inserción al inicio
            startTime = System.nanoTime();
            arrayList = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                arrayList.add(0, i); // Inserción al inicio
            }
            long arrayListFrontTime = System.nanoTime() - startTime;
            
            // LinkedList: inserción al inicio
            startTime = System.nanoTime();
            linkedList = new LinkedList<>();
            for (int i = 0; i < n; i++) {
                linkedList.addFirst(i);
            }
            long linkedListFrontTime = System.nanoTime() - startTime;
            
            System.out.println("  Inserción al INICIO:");
            printComparison("ArrayList", "LinkedList", arrayListFrontTime, linkedListFrontTime);
            
            System.out.println();
        }
    }
    
    /**
     * Compara rendimiento de búsqueda: List vs Set
     */
    public void compareListVsSetSearch() {
        System.out.println("=== COMPARACIÓN: SEARCH PERFORMANCE ===");
        System.out.println("List.contains() vs Set.contains()");
        System.out.println();
        
        int[] sizes = {10_000, 100_000};
        
        for (int n : sizes) {
            System.out.println("Probando con " + n + " elementos:");
            
            // Preparar datos
            List<Integer> arrayList = new ArrayList<>();
            Set<Integer> hashSet = new HashSet<>();
            Set<Integer> treeSet = new TreeSet<>();
            
            for (int i = 0; i < n; i++) {
                arrayList.add(i);
                hashSet.add(i);
                treeSet.add(i);
            }
            
            // Buscar elemento en medio
            int searchValue = n / 2;
            int iterations = 1000; // Repetir búsqueda 1000 veces
            
            // ArrayList.contains() - O(n)
            long startTime = System.nanoTime();
            for (int i = 0; i < iterations; i++) {
                arrayList.contains(searchValue);
            }
            long arrayListTime = System.nanoTime() - startTime;
            
            // HashSet.contains() - O(1)
            startTime = System.nanoTime();
            for (int i = 0; i < iterations; i++) {
                hashSet.contains(searchValue);
            }
            long hashSetTime = System.nanoTime() - startTime;
            
            // TreeSet.contains() - O(log n)
            startTime = System.nanoTime();
            for (int i = 0; i < iterations; i++) {
                treeSet.contains(searchValue);
            }
            long treeSetTime = System.nanoTime() - startTime;
            
            System.out.printf("    %-20s %8.2f ms (1000 búsquedas)%n", "ArrayList.contains:", arrayListTime / 1_000_000.0);
            System.out.printf("    %-20s %8.2f ms (1000 búsquedas)%n", "HashSet.contains:", hashSetTime / 1_000_000.0);
            System.out.printf("    %-20s %8.2f ms (1000 búsquedas)%n", "TreeSet.contains:", treeSetTime / 1_000_000.0);
            System.out.println();
        }
    }
    
    /**
     * Compara rendimiento: HashMap vs TreeMap
     */
    public void compareMapOperations() {
        System.out.println("=== COMPARACIÓN: MAP OPERATIONS ===");
        System.out.println("HashMap vs TreeMap");
        System.out.println();
        
        int[] sizes = {10_000, 100_000};
        
        for (int n : sizes) {
            System.out.println("Probando con " + n + " elementos:");
            
            // HashMap: inserción
            long startTime = System.nanoTime();
            Map<Integer, String> hashMap = new HashMap<>();
            for (int i = 0; i < n; i++) {
                hashMap.put(i, "value" + i);
            }
            long hashMapInsertTime = System.nanoTime() - startTime;
            
            // TreeMap: inserción
            startTime = System.nanoTime();
            Map<Integer, String> treeMap = new TreeMap<>();
            for (int i = 0; i < n; i++) {
                treeMap.put(i, "value" + i);
            }
            long treeMapInsertTime = System.nanoTime() - startTime;
            
            System.out.println("  Inserción:");
            printComparison("HashMap", "TreeMap", hashMapInsertTime, treeMapInsertTime);
            
            // HashMap: lookup
            int iterations = 10000;
            startTime = System.nanoTime();
            for (int i = 0; i < iterations; i++) {
                hashMap.get(n / 2);
            }
            long hashMapLookupTime = System.nanoTime() - startTime;
            
            // TreeMap: lookup
            startTime = System.nanoTime();
            for (int i = 0; i < iterations; i++) {
                treeMap.get(n / 2);
            }
            long treeMapLookupTime = System.nanoTime() - startTime;
            
            System.out.println("  Lookup (10,000 operaciones):");
            printComparison("HashMap", "TreeMap", hashMapLookupTime, treeMapLookupTime);
            
            System.out.println();
        }
    }
    
    /**
     * Compara HashSet vs TreeSet vs LinkedHashSet
     */
    public void compareSetImplementations() {
        System.out.println("=== COMPARACIÓN: SET IMPLEMENTATIONS ===");
        System.out.println("HashSet vs TreeSet vs LinkedHashSet");
        System.out.println();
        
        int[] sizes = {10_000, 100_000};
        
        for (int n : sizes) {
            System.out.println("Probando con " + n + " elementos:");
            
            // HashSet: inserción
            long startTime = System.nanoTime();
            Set<Integer> hashSet = new HashSet<>();
            for (int i = 0; i < n; i++) {
                hashSet.add(i);
            }
            long hashSetTime = System.nanoTime() - startTime;
            
            // TreeSet: inserción
            startTime = System.nanoTime();
            Set<Integer> treeSet = new TreeSet<>();
            for (int i = 0; i < n; i++) {
                treeSet.add(i);
            }
            long treeSetTime = System.nanoTime() - startTime;
            
            // LinkedHashSet: inserción
            startTime = System.nanoTime();
            Set<Integer> linkedHashSet = new LinkedHashSet<>();
            for (int i = 0; i < n; i++) {
                linkedHashSet.add(i);
            }
            long linkedHashSetTime = System.nanoTime() - startTime;
            
            System.out.printf("    %-20s %8.2f ms%n", "HashSet:", hashSetTime / 1_000_000.0);
            System.out.printf("    %-20s %8.2f ms%n", "TreeSet:", treeSetTime / 1_000_000.0);
            System.out.printf("    %-20s %8.2f ms%n", "LinkedHashSet:", linkedHashSetTime / 1_000_000.0);
            System.out.println();
        }
    }
    
    private void printComparison(String name1, String name2, long time1, long time2) {
        System.out.printf("    %-20s %8.2f ms%n", name1 + ":", time1 / 1_000_000.0);
        System.out.printf("    %-20s %8.2f ms%n", name2 + ":", time2 / 1_000_000.0);
        System.out.printf("    %-20s %8.2f ms (%.1f%% %s)%n", 
            "Diferencia:",
            Math.abs(time1 - time2) / 1_000_000.0,
            Math.abs(time1 - time2) * 100.0 / Math.max(time1, time2),
            time1 < time2 ? "más rápido " + name1 : "más rápido " + name2);
    }
    
    /**
     * Imprime reporte completo de comparación
     */
    public void printReport() {
        System.out.println("=".repeat(70));
        System.out.println("REPORTE DE COMPARACIÓN: COLECCIONES ESTÁNDAR");
        System.out.println("=".repeat(70));
        System.out.println();
        
        compareListInsertion();
        compareListVsSetSearch();
        compareMapOperations();
        compareSetImplementations();
        
        System.out.println("=".repeat(70));
        System.out.println("ANÁLISIS DE COMPLEJIDAD TEÓRICA:");
        System.out.println("=".repeat(70));
        System.out.println();
        System.out.println("LISTAS:");
        System.out.println("  ArrayList:");
        System.out.println("    - add() al final:      O(1) amortizado");
        System.out.println("    - add() al inicio:     O(n)");
        System.out.println("    - get(index):          O(1)");
        System.out.println("    - contains():          O(n)");
        System.out.println();
        System.out.println("  LinkedList:");
        System.out.println("    - add() al final:      O(1)");
        System.out.println("    - add() al inicio:     O(1)");
        System.out.println("    - get(index):          O(n)");
        System.out.println("    - contains():          O(n)");
        System.out.println();
        System.out.println("CONJUNTOS:");
        System.out.println("  HashSet:");
        System.out.println("    - add():               O(1) amortizado");
        System.out.println("    - contains():          O(1) amortizado");
        System.out.println("    - Orden:               No garantizado");
        System.out.println();
        System.out.println("  TreeSet:");
        System.out.println("    - add():               O(log n)");
        System.out.println("    - contains():          O(log n)");
        System.out.println("    - Orden:               Orden natural");
        System.out.println();
        System.out.println("  LinkedHashSet:");
        System.out.println("    - add():               O(1) amortizado");
        System.out.println("    - contains():          O(1) amortizado");
        System.out.println("    - Orden:               Orden de inserción");
        System.out.println();
        System.out.println("MAPAS:");
        System.out.println("  HashMap:");
        System.out.println("    - put():               O(1) amortizado");
        System.out.println("    - get():               O(1) amortizado");
        System.out.println("    - Orden:               No garantizado");
        System.out.println();
        System.out.println("  TreeMap:");
        System.out.println("    - put():               O(log n)");
        System.out.println("    - get():               O(log n)");
        System.out.println("    - Orden:               Orden natural de claves");
        System.out.println();
        System.out.println("=".repeat(70));
        System.out.println("RECOMENDACIONES PARA PRODUCCIÓN:");
        System.out.println("=".repeat(70));
        System.out.println();
        System.out.println("1. LISTAS:");
        System.out.println("   - USAR ArrayList: acceso aleatorio frecuente, inserciones al final");
        System.out.println("   - USAR LinkedList: inserciones/eliminaciones frecuentes en extremos");
        System.out.println();
        System.out.println("2. CONJUNTOS:");
        System.out.println("   - USAR HashSet: búsquedas rápidas, no importa el orden");
        System.out.println("   - USAR TreeSet: necesitas orden natural");
        System.out.println("   - USAR LinkedHashSet: necesitas orden de inserción");
        System.out.println();
        System.out.println("3. MAPAS:");
        System.out.println("   - USAR HashMap: acceso rápido, no importa el orden");
        System.out.println("   - USAR TreeMap: necesitas orden por clave");
        System.out.println("   - USAR LinkedHashMap: necesitas orden de inserción (LRU cache)");
        System.out.println();
        System.out.println("4. BÚSQUEDAS:");
        System.out.println("   - SIEMPRE usar Set/Map para búsquedas en lugar de List.contains()");
        System.out.println("   - Set.contains(): O(1) vs List.contains(): O(n)");
        System.out.println();
    }
}
