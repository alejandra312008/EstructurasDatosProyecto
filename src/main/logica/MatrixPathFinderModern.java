import java.util.*;
import java.util.stream.Collectors;

/**
 * Refactorización moderna de MatrixPathFinder usando colecciones estándar de Java
 * Implementa operaciones típicas: transformación, filtrado, ordenación, conjuntos, mapas
 * 
 * PARTE A - Selección de colecciones:
 * - List: ArrayList (acceso aleatorio rápido a rutas)
 * - Set: HashSet (celdas visitadas únicas), LinkedHashSet (conserva orden)
 * - Map: HashMap (índice rápido de rutas por ID), TreeMap (rutas ordenadas por longitud)
 */
public class MatrixPathFinderModern {
    private char[][] matrix;
    private int rows;
    private int cols;
    
    // PARTE A: Colecciones seleccionadas
    private final List<PathInfo> allPaths; // ArrayList: acceso aleatorio rápido
    private final Set<Point> visitedCells; // HashSet: búsqueda O(1) de celdas únicas
    private final Map<String, PathInfo> pathIndex; // HashMap: índice O(1) por ID
    private final Map<Integer, List<PathInfo>> pathsByLength; // TreeMap: agrupación ordenada
    
    // Direcciones
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    
    /**
     * Clase interna para representar información de ruta
     */
    public static class PathInfo {
        private final String id;
        private final List<Point> path;
        private final int length;
        private final long timestamp;
        
        public PathInfo(String id, List<Point> path) {
            this.id = id;
            this.path = Collections.unmodifiableList(new ArrayList<>(path)); // Inmutabilidad
            this.length = path.size();
            this.timestamp = System.currentTimeMillis();
        }
        
        public String getId() { return id; }
        public List<Point> getPath() { return path; }
        public int getLength() { return length; }
        public long getTimestamp() { return timestamp; }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PathInfo pathInfo = (PathInfo) o;
            return Objects.equals(id, pathInfo.id);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
        
        @Override
        public String toString() {
            return String.format("Path[%s: %d pasos]", id, length);
        }
    }
    
    public MatrixPathFinderModern(char[][] matrix) {
        this.matrix = matrix;
        this.rows = matrix.length;
        this.cols = matrix[0].length;
        this.allPaths = new ArrayList<>();
        this.visitedCells = new HashSet<>();
        this.pathIndex = new HashMap<>();
        this.pathsByLength = new TreeMap<>();
    }
    
    /**
     * Busca múltiples rutas usando BFS
     */
    public void findMultiplePaths(Point start, Point end, int maxPaths) {
        allPaths.clear();
        visitedCells.clear();
        pathIndex.clear();
        pathsByLength.clear();
        
        Queue<List<Point>> queue = new LinkedList<>();
        Set<String> pathIds = new HashSet<>(); // Para evitar rutas duplicadas
        
        queue.offer(Collections.singletonList(start));
        
        while (!queue.isEmpty() && allPaths.size() < maxPaths) {
            List<Point> currentPath = queue.poll();
            Point current = currentPath.get(currentPath.size() - 1);
            
            if (current.equals(end)) {
                String pathId = generatePathId(currentPath);
                if (!pathIds.contains(pathId)) {
                    pathIds.add(pathId);
                    PathInfo pathInfo = new PathInfo(pathId, currentPath);
                    addPath(pathInfo);
                }
                continue;
            }
            
            // Explorar vecinos
            for (int[] dir : DIRECTIONS) {
                int newRow = current.row + dir[0];
                int newCol = current.col + dir[1];
                
                if (isValid(newRow, newCol) && matrix[newRow][newCol] != '#') {
                    Point neighbor = new Point(newRow, newCol);
                    
                    if (!visitedCells.contains(neighbor)) {
                        List<Point> newPath = new ArrayList<>(currentPath);
                        newPath.add(neighbor);
                        queue.offer(newPath);
                        visitedCells.add(neighbor);
                    }
                }
            }
        }
    }
    
    private String generatePathId(List<Point> path) {
        return path.stream()
                .map(p -> p.row + "," + p.col)
                .collect(Collectors.joining("->"));
    }
    
    private void addPath(PathInfo pathInfo) {
        allPaths.add(pathInfo);
        pathIndex.put(pathInfo.getId(), pathInfo);
        pathsByLength.computeIfAbsent(pathInfo.getLength(), k -> new ArrayList<>()).add(pathInfo);
    }
    
    private boolean isValid(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }
    
    // ========== PARTE B: OPERACIONES OBLIGATORIAS ==========
    
    /**
     * 1. TRANSFORMACIÓN (map)
     * Convierte PathInfo a DTO simplificado con solo coordenadas de inicio y fin
     */
    public List<PathSummary> transformPathsToSummary() {
        return allPaths.stream()
                .map(pathInfo -> new PathSummary(
                    pathInfo.getId(),
                    pathInfo.getPath().get(0),
                    pathInfo.getPath().get(pathInfo.getPath().size() - 1),
                    pathInfo.getLength()
                ))
                .collect(Collectors.toList());
    }
    
    public static class PathSummary {
        private final String id;
        private final Point start;
        private final Point end;
        private final int length;
        
        public PathSummary(String id, Point start, Point end, int length) {
            this.id = id;
            this.start = start;
            this.end = end;
            this.length = length;
        }
        
        @Override
        public String toString() {
            return String.format("Summary[%s: %s -> %s (%d)]", id, start, end, length);
        }
    }
    
    /**
     * 2. FILTRADO (filter)
     * Filtra rutas por longitud mínima y máxima
     * PARTE D: Retorna vista de solo lectura cuando sea apropiado
     */
    public List<PathInfo> filterPathsByLength(int minLength, int maxLength) {
        return Collections.unmodifiableList(
            allPaths.stream()
                .filter(path -> path.getLength() >= minLength && path.getLength() <= maxLength)
                .collect(Collectors.toList())
        );
    }
    
    /**
     * Filtra rutas que pasan por un punto específico
     * PARTE D: Retorna vista de solo lectura
     */
    public List<PathInfo> filterPathsContainingPoint(Point point) {
        if (point == null) return Collections.emptyList();
        return Collections.unmodifiableList(
            allPaths.stream()
                .filter(pathInfo -> pathInfo.getPath().contains(point))
                .collect(Collectors.toList())
        );
    }
    
    /**
     * 3. ORDENACIÓN (sort)
     * Ordena rutas por longitud (ascendente) y luego por timestamp (descendente)
     */
    public List<PathInfo> sortPathsByLengthAndTime() {
        return allPaths.stream()
                .sorted(Comparator
                    .comparingInt(PathInfo::getLength)
                    .thenComparing(PathInfo::getTimestamp, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }
    
    /**
     * Ordena rutas por timestamp (más recientes primero)
     */
    public List<PathInfo> sortPathsByTimestampDesc() {
        return allPaths.stream()
                .sorted(Comparator.comparing(PathInfo::getTimestamp).reversed())
                .collect(Collectors.toList());
    }
    
    /**
     * 4. CONJUNTOS (Set operations)
     * Unión de celdas visitadas de múltiples rutas
     * PARTE D: Retorna copia defensiva (inmutabilidad)
     */
    public Set<Point> unionOfVisitedCells(List<List<Point>> paths) {
        if (paths == null || paths.isEmpty()) {
            return Collections.emptySet();
        }
        // Copia defensiva: crear nueva colección inmutable
        return Collections.unmodifiableSet(
            paths.stream()
                .flatMap(List::stream)
                .collect(Collectors.toSet())
        );
    }
    
    /**
     * Intersección: celdas comunes en todas las rutas
     * PARTE D: Retorna copia defensiva
     */
    public Set<Point> intersectionOfPaths(List<List<Point>> paths) {
        if (paths == null || paths.isEmpty()) {
            return Collections.emptySet();
        }
        
        Set<Point> intersection = new HashSet<>(paths.get(0));
        for (int i = 1; i < paths.size(); i++) {
            intersection.retainAll(new HashSet<>(paths.get(i)));
        }
        return Collections.unmodifiableSet(intersection);
    }
    
    /**
     * Diferencia: celdas en primera ruta que no están en la segunda
     * PARTE D: Retorna copia defensiva
     */
    public Set<Point> differenceOfPaths(List<Point> path1, List<Point> path2) {
        if (path1 == null) path1 = Collections.emptyList();
        if (path2 == null) path2 = Collections.emptyList();
        
        Set<Point> set1 = new HashSet<>(path1);
        Set<Point> set2 = new HashSet<>(path2);
        set1.removeAll(set2);
        return Collections.unmodifiableSet(set1);
    }
    
    /**
     * 5. MAPAS (conteo y agrupación)
     * Cuenta frecuencia de celdas visitadas
     */
    public Map<Point, Long> countCellFrequency() {
        return allPaths.stream()
                .flatMap(pathInfo -> pathInfo.getPath().stream())
                .collect(Collectors.groupingBy(
                    point -> point,
                    Collectors.counting()
                ));
    }
    
    /**
     * Agrupa rutas por longitud
     */
    public Map<Integer, List<PathInfo>> groupPathsByLength() {
        return allPaths.stream()
                .collect(Collectors.groupingBy(PathInfo::getLength));
    }
    
    /**
     * Top-N rutas más cortas
     * PARTE D: Retorna vista de solo lectura
     */
    public List<PathInfo> getTopShortestPaths(int n) {
        if (n <= 0) return Collections.emptyList();
        return Collections.unmodifiableList(
            allPaths.stream()
                .sorted(Comparator.comparingInt(PathInfo::getLength))
                .limit(n)
                .collect(Collectors.toList())
        );
    }
    
    /**
     * Top-N celdas más visitadas
     */
    public List<Map.Entry<Point, Long>> getTopVisitedCells(int n) {
        return countCellFrequency().entrySet().stream()
                .sorted(Map.Entry.<Point, Long>comparingByValue().reversed())
                .limit(n)
                .collect(Collectors.toList());
    }
    
    /**
     * 6. BÚSQUEDA INDEXADA con Map
     * Busca ruta por ID usando índice HashMap (O(1) amortizado)
     */
    public PathInfo findPathById(String id) {
        return pathIndex.get(id); // O(1) vs O(n) búsqueda lineal
    }
    
    /**
     * Busca rutas por longitud usando índice TreeMap (O(log n))
     */
    public List<PathInfo> findPathsByLength(int length) {
        return pathsByLength.getOrDefault(length, Collections.emptyList());
    }
    
    /**
     * Pipeline declarativo completo: transformar → filtrar → ordenar → limitar
     */
    public List<PathSummary> pipelineExample(int minLength, int maxLength, int limit) {
        return allPaths.stream()
                .filter(path -> path.getLength() >= minLength && path.getLength() <= maxLength)
                .sorted(Comparator.comparingInt(PathInfo::getLength))
                .limit(limit)
                .map(pathInfo -> new PathSummary(
                    pathInfo.getId(),
                    pathInfo.getPath().get(0),
                    pathInfo.getPath().get(pathInfo.getPath().size() - 1),
                    pathInfo.getLength()
                ))
                .collect(Collectors.toList());
    }
    
    // Getters inmutables
    public List<PathInfo> getAllPaths() {
        return Collections.unmodifiableList(allPaths);
    }
    
    public Set<Point> getVisitedCells() {
        return Collections.unmodifiableSet(visitedCells);
    }
    
    public Map<String, PathInfo> getPathIndex() {
        return Collections.unmodifiableMap(pathIndex);
    }
    
    public Map<Integer, List<PathInfo>> getPathsByLength() {
        return Collections.unmodifiableMap(pathsByLength);
    }
    
    public void printMatrixWithPath(List<Point> path) {
        char[][] displayMatrix = new char[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            System.arraycopy(matrix[i], 0, displayMatrix[i], 0, cols);
        }
        
        for (Point p : path) {
            if (displayMatrix[p.row][p.col] != 'S' && displayMatrix[p.row][p.col] != 'E') {
                displayMatrix[p.row][p.col] = '*';
            }
        }
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(displayMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
