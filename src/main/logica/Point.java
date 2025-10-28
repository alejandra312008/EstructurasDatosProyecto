/**
 * Clase para representar coordenadas en la matriz
 */
public class Point {
    int row;
    int col;
    
    public Point(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Point point = (Point) obj;
        return row == point.row && col == point.col;
    }
    
    @Override
    public int hashCode() {
        return row * 1000 + col;
    }
    
    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}
