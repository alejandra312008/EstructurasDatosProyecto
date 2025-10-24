public class EmptyStackException extends RuntimeException {
    public EmptyStackException() {
        super("La pila esta vacia");
    }
    
    public EmptyStackException(String message) {
        super(message);
    }
}





