/**
 * Excepción lanzada cuando se intenta acceder a un índice inválido en una lista
 */
public class InvalidIndexException extends RuntimeException {
    public InvalidIndexException(String message) {
        super(message);
    }
}

