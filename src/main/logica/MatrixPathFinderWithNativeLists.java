import java.util.*;

/**
 * Clase para buscar rutas en una matriz usando LinkedList nativa de Java
 * - LinkedList<Point>: Historial de visitas (equivalente a lista simple)
 * - LinkedList<List<Point>>: Múltiples rutas encontradas (equivalente a lista doble)
 */
public class MatrixPathFinderWithNativeLists {
    private char[][] matrix;
    private int rows;
    private int cols;
    
    // Direcciones: arriba, abajo, izquierda, derecha
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    
    // LinkedList nativa: historial de celdas visitadas
    private LinkedList<Point> visitHistory;
    
    // LinkedList nativa: múltiples rutas encontradas
    private LinkedList<List<Point>> foundPaths;
    
    public MatrixPathFinderWithNativeLists(char[][] matrix) {
        this.matrix = matrix;
        this.rows = matrix.length;
        this.cols = matrix[0].length;
        this.visitHistory = new LinkedList<>();
        this.foundPaths = new LinkedList<>();
    }
    
    /**
     * Busca una ruta usando DFS y registra el historial de visitas
     * @return la primera ruta encontrada
     */
    public List<Point> findPathDFS(Point start, Point end) {
        visitHistory.clear();
        java.util.Stack<Point> stack = new java.util.Stack<>();
        boolean[][] visited = new boolean[rows][cols];
        Point[][] parent = new Point[rows][cols];
        
        stack.push(start);
        visited[start.row][start.col] = true;
        visitHistory.addLast(start); // addLast = O(1) en LinkedList
        
        while (!stack.isEmpty()) {
            Point current = stack.pop();
            
            if (current.equals(end)) {
                List<Point> path = reconstructPath(parent, start, end);
                foundPaths.addLast(path); // Agregar ruta a lista doble nativa
                return path;
            }
            
            // Explorar vecinos
            for (int[] dir : DIRECTIONS) {
                int newRow = current.row + dir[0];
                int newCol = current.col + dir[1];
                
                if (isValid(newRow, newCol) && !visited[newRow][newCol] && matrix[newRow][newCol] != '#') {
                    Point neighbor = new Point(newRow, newCol);
                    stack.push(neighbor);
                    visited[newRow][newCol] = true;
                    parent[newRow][newCol] = current;
                    visitHistory.addLast(neighbor); // addLast = O(1)
                }
            }
        }
        
        return new ArrayList<>(); // No se encontró ruta
    }
    
    /**
     * Busca una ruta usando BFS y registra el historial de visitas
     * @return la primera ruta encontrada
     */
    public List<Point> findPathBFS(Point start, Point end) {
        visitHistory.clear();
        Queue<Point> queue = new LinkedList<>();
        boolean[][] visited = new boolean[rows][cols];
        Point[][] parent = new Point[rows][cols];
        
        queue.offer(start);
        visited[start.row][start.col] = true;
        visitHistory.addLast(start); // addLast = O(1) en LinkedList
        
        while (!queue.isEmpty()) {
            Point current = queue.poll();
            
            if (current.equals(end)) {
                List<Point> path = reconstructPath(parent, start, end);
                foundPaths.addLast(path); // Agregar ruta a lista doble nativa
                return path;
            }
            
            // Explorar vecinos
            for (int[] dir : DIRECTIONS) {
                int newRow = current.row + dir[0];
                int newCol = current.col + dir[1];
                
                if (isValid(newRow, newCol) && !visited[newRow][newCol] && matrix[newRow][newCol] != '#') {
                    Point neighbor = new Point(newRow, newCol);
                    queue.offer(neighbor);
                    visited[newRow][newCol] = true;
                    parent[newRow][newCol] = current;
                    visitHistory.addLast(neighbor); // addLast = O(1)
                }
            }
        }
        
        return new ArrayList<>(); // No se encontró ruta
    }
    
    /**
     * Busca múltiples rutas y las almacena en la lista doble nativa
     * @param maxPaths número máximo de rutas a buscar
     */
    public void findMultiplePaths(Point start, Point end, int maxPaths) {
        foundPaths.clear();
        visitHistory.clear();
        
        for (int i = 0; i < maxPaths; i++) {
            List<Point> path = findPathBFS(start, end);
            if (path.isEmpty()) {
                break; // No hay más rutas
            }
        }
    }
    
    /**
     * Obtiene el historial de visitas (LinkedList nativa)
     */
    public LinkedList<Point> getVisitHistory() {
        return visitHistory;
    }
    
    /**
     * Obtiene todas las rutas encontradas (LinkedList nativa)
     */
    public LinkedList<List<Point>> getFoundPaths() {
        return foundPaths;
    }
    
    /**
     * Navega a la siguiente ruta usando ListIterator
     * @param iterator iterador de la lista de rutas
     * @return siguiente ruta o null si no hay más
     */
    public List<Point> getNextPath(ListIterator<List<Point>> iterator) {
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }
    
    /**
     * Navega a la ruta anterior usando ListIterator
     * @param iterator iterador de la lista de rutas
     * @return ruta anterior o null si no hay más
     */
    public List<Point> getPreviousPath(ListIterator<List<Point>> iterator) {
        if (iterator.hasPrevious()) {
            return iterator.previous();
        }
        return null;
    }
    
    /**
     * Verifica si las coordenadas son válidas
     */
    private boolean isValid(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }
    
    /**
     * Reconstruye la ruta desde el punto de inicio hasta el final
     */
    private List<Point> reconstructPath(Point[][] parent, Point start, Point end) {
        List<Point> path = new ArrayList<>();
        Point current = end;
        
        while (current != null) {
            path.add(current);
            current = parent[current.row][current.col];
        }
        
        Collections.reverse(path);
        return path;
    }
    
    /**
     * Imprime la matriz con la ruta marcada
     */
    public void printMatrixWithPath(List<Point> path) {
        char[][] displayMatrix = new char[rows][cols];
        
        // Copiar matriz original
        for (int i = 0; i < rows; i++) {
            System.arraycopy(matrix[i], 0, displayMatrix[i], 0, cols);
        }
        
        // Marcar ruta
        for (Point p : path) {
            if (displayMatrix[p.row][p.col] != 'S' && displayMatrix[p.row][p.col] != 'E') {
                displayMatrix[p.row][p.col] = '*';
            }
        }
        
        // Imprimir matriz
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(displayMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    /**
     * Imprime el historial de visitas
     */
    public void printVisitHistory() {
        System.out.println("Historial de visitas (LinkedList Nativa):");
        System.out.println("Total de celdas visitadas: " + visitHistory.size());
        System.out.print("Orden: ");
        
        Iterator<Point> it = visitHistory.iterator();
        while (it.hasNext()) {
            System.out.print(it.next());
            if (it.hasNext()) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }
}

