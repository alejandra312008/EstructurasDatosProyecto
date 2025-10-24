import java.util.*;

/**
 * Implementación de búsqueda de rutas usando librerías nativas de Java
 * Usa ArrayDeque para pila (Deque) y cola (Queue)
 */
public class MatrixPathFinderNative {
    private char[][] matrix;
    private int rows;
    private int cols;
    
    // Direcciones: arriba, abajo, izquierda, derecha
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    
    public MatrixPathFinderNative(char[][] matrix) {
        this.matrix = matrix;
        this.rows = matrix.length;
        this.cols = matrix[0].length;
    }
    
    /**
     * Busca una ruta usando DFS (Depth-First Search) con Deque nativo
     * Usa ArrayDeque como pila: push() para agregar, pop() para remover
     */
    public List<Point> findPathDFS(Point start, Point end) {
        Deque<Point> stack = new ArrayDeque<>(); // Pila nativa
        boolean[][] visited = new boolean[rows][cols];
        Point[][] parent = new Point[rows][cols];
        
        stack.push(start);
        visited[start.row][start.col] = true;
        
        while (!stack.isEmpty()) {
            Point current = stack.pop();
            
            if (current.equals(end)) {
                return reconstructPath(parent, start, end);
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
                }
            }
        }
        
        return new ArrayList<>(); // No se encontró ruta
    }
    
    /**
     * Busca una ruta usando BFS (Breadth-First Search) con Queue nativa
     * Usa ArrayDeque como cola: offer() para agregar, poll() para remover
     */
    public List<Point> findPathBFS(Point start, Point end) {
        Queue<Point> queue = new ArrayDeque<>(); // Cola nativa
        boolean[][] visited = new boolean[rows][cols];
        Point[][] parent = new Point[rows][cols];
        
        queue.offer(start);
        visited[start.row][start.col] = true;
        
        while (!queue.isEmpty()) {
            Point current = queue.poll();
            
            if (current.equals(end)) {
                return reconstructPath(parent, start, end);
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
                }
            }
        }
        
        return new ArrayList<>(); // No se encontró ruta
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
}




