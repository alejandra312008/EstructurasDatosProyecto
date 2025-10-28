public class EmptyQueueException extends RuntimeException {
    public EmptyQueueException() {
        super("La cola esta vacia");
    }
    
    public EmptyQueueException(String message) {
        super(message);
    }
}





