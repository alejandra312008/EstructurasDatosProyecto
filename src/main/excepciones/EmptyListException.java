/**
 * Excepción lanzada cuando se intenta realizar una operación en una lista vacía
 */
public class EmptyListException extends RuntimeException {
    public EmptyListException(String message) {
        super(message);
    }
}

