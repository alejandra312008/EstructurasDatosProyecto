/**
 * Implementación de pila genérica usando nodos enlazados
 * Complejidad: O(1) para push, pop, peek
 */
public class StackManual<T> {
    private Node<T> head;
    private int size;
    
    /**
     * Constructor: crea una pila vacía
     * Precondición: ninguna
     * Postcondición: pila vacía con size = 0
     */
    public StackManual() {
        this.head = null;
        this.size = 0;
    }
    
    /**
     * Agrega un elemento al tope de la pila
     * Precondición: ninguna
     * Postcondición: elemento agregado al tope, size incrementado
     * Complejidad: O(1)
     */
    public void push(T value) {
        Node<T> newNode = new Node<>(value);
        newNode.setNext(head);
        head = newNode;
        size++;
    }
    
    /**
     * Remueve y retorna el elemento del tope de la pila
     * Precondición: pila no vacía
     * Postcondición: elemento removido del tope, size decrementado
     * Complejidad: O(1)
     * @throws EmptyStackException si la pila está vacía
     */
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException("No se puede hacer pop en una pila vacía");
        }
        
        T data = head.getData();
        head = head.getNext();
        size--;
        return data;
    }
    
    /**
     * Retorna el elemento del tope sin removerlo
     * Precondición: pila no vacía
     * Postcondición: pila sin cambios
     * Complejidad: O(1)
     * @throws EmptyStackException si la pila está vacía
     */
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException("No se puede hacer peek en una pila vacía");
        }
        
        return head.getData();
    }
    
    /**
     * Verifica si la pila está vacía
     * Complejidad: O(1)
     */
    public boolean isEmpty() {
        return head == null;
    }
    
    /**
     * Retorna el número de elementos en la pila
     * Complejidad: O(1)
     */
    public int size() {
        return size;
    }
    
    /**
     * Vacía la pila
     * Precondición: ninguna
     * Postcondición: pila vacía con size = 0
     */
    public void clear() {
        head = null;
        size = 0;
    }
    
    /**
     * Verifica si la pila contiene un elemento específico
     * Complejidad: O(n)
     */
    public boolean contains(T value) {
        Node<T> current = head;
        while (current != null) {
            if (current.getData().equals(value)) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }
    
    /**
     * Convierte la pila a un arreglo
     * Complejidad: O(n)
     */
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] array = (T[]) new Object[size];
        Node<T> current = head;
        int index = 0;
        
        while (current != null) {
            array[index++] = current.getData();
            current = current.getNext();
        }
        
        return array;
    }
}
