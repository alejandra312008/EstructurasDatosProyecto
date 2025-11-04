import java.util.*;

/**
 * Clase para buscar rutas en una matriz integrando listas simples y dobles
 * - Lista Simple: Registra secuencialmente las celdas visitadas durante la búsqueda
 * - Lista Doble: Almacena múltiples rutas encontradas para navegación bidireccional
 */
public class MatrixPathFinderWithLists {
    private char[][] matrix;
    private int rows;
    private int cols;
    
    // Direcciones: arriba, abajo, izquierda, derecha
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    
    // Lista simple: historial de celdas visitadas (secuencial)
    private SinglyLinkedList<Point> visitHistory;
    
    // Lista doble: múltiples rutas encontradas (navegable en ambas direcciones)
    private DoublyLinkedList<List<Point>> foundPaths;
    
    public MatrixPathFinderWithLists(char[][] matrix) {
        this.matrix = matrix;
        this.rows = matrix.length;
        this.cols = matrix[0].length;
        this.visitHistory = new SinglyLinkedList<>();
        this.foundPaths = new DoublyLinkedList<>();
    }
    
    /**
     * Busca una ruta usando DFS y registra el historial de visitas en lista simple
     * @return la primera ruta encontrada
     */
    public List<Point> findPathDFS(Point start, Point end) {
        visitHistory.clear();
        StackManual<Point> stack = new StackManual<>();
        boolean[][] visited = new boolean[rows][cols];
        Point[][] parent = new Point[rows][cols];
        
        stack.push(start);
        visited[start.row][start.col] = true;
        visitHistory.addLast(start); // Registrar visita en lista simple
        
        while (!stack.isEmpty()) {
            Point current = stack.pop();
            
            if (current.equals(end)) {
                List<Point> path = reconstructPath(parent, start, end);
                foundPaths.addLast(path); // Agregar ruta a lista doble
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
                    visitHistory.addLast(neighbor); // Registrar cada visita en lista simple
                }
            }
        }
        
        return new ArrayList<>(); // No se encontró ruta
    }
    
    /**
     * Busca una ruta usando BFS y registra el historial de visitas en lista simple
     * @return la primera ruta encontrada
     */
    public List<Point> findPathBFS(Point start, Point end) {
        visitHistory.clear();
        QueueManual<Point> queue = new QueueManual<>();
        boolean[][] visited = new boolean[rows][cols];
        Point[][] parent = new Point[rows][cols];
        
        queue.enqueue(start);
        visited[start.row][start.col] = true;
        visitHistory.addLast(start); // Registrar visita en lista simple
        
        while (!queue.isEmpty()) {
            Point current = queue.dequeue();
            
            if (current.equals(end)) {
                List<Point> path = reconstructPath(parent, start, end);
                foundPaths.addLast(path); // Agregar ruta a lista doble
                return path;
            }
            
            // Explorar vecinos
            for (int[] dir : DIRECTIONS) {
                int newRow = current.row + dir[0];
                int newCol = current.col + dir[1];
                
                if (isValid(newRow, newCol) && !visited[newRow][newCol] && matrix[newRow][newCol] != '#') {
                    Point neighbor = new Point(newRow, newCol);
                    queue.enqueue(neighbor);
                    visited[newRow][newCol] = true;
                    parent[newRow][newCol] = current;
                    visitHistory.addLast(neighbor); // Registrar cada visita en lista simple
                }
            }
        }
        
        return new ArrayList<>(); // No se encontró ruta
    }
    
    /**
     * Busca múltiples rutas y las almacena en la lista doble
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
     * Obtiene el historial de visitas (lista simple)
     */
    public SinglyLinkedList<Point> getVisitHistory() {
        return visitHistory;
    }
    
    /**
     * Obtiene todas las rutas encontradas (lista doble)
     */
    public DoublyLinkedList<List<Point>> getFoundPaths() {
        return foundPaths;
    }
    
    /**
     * Navega a la siguiente ruta en la lista doble
     * @param currentNode nodo actual en la lista de rutas
     * @return siguiente ruta o null si no hay más
     */
    public List<Point> getNextPath(DoublyNode<List<Point>> currentNode) {
        if (currentNode == null || currentNode.getNext() == null) {
            return null;
        }
        return currentNode.getNext().getData();
    }
    
    /**
     * Navega a la ruta anterior en la lista doble
     * @param currentNode nodo actual en la lista de rutas
     * @return ruta anterior o null si no hay más
     */
    public List<Point> getPreviousPath(DoublyNode<List<Point>> currentNode) {
        if (currentNode == null || currentNode.getPrev() == null) {
            return null;
        }
        return currentNode.getPrev().getData();
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
     * Imprime el historial de visitas (de la lista simple)
     */
    public void printVisitHistory() {
        System.out.println("Historial de visitas (Lista Simple):");
        System.out.println("Total de celdas visitadas: " + visitHistory.size());
        System.out.print("Orden: ");
        
        for (int i = 0; i < visitHistory.size(); i++) {
            System.out.print(visitHistory.get(i));
            if (i < visitHistory.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }
}

