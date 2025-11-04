/**
 * Implementación de lista simplemente enlazada genérica
 * Complejidad: O(1) para addFirst, addLast, removeFirst, removeLast
 *              O(n) para get, insertAt, removeAt
 */
public class SinglyLinkedList<T> {
    private Node<T> head;
    private int size;
    
    /**
     * Constructor: crea una lista vacía
     * Precondición: ninguna
     * Postcondición: lista vacía con size = 0
     */
    public SinglyLinkedList() {
        this.head = null;
        this.size = 0;
    }
    
    /**
     * Agrega un elemento al inicio de la lista
     * Precondición: ninguna
     * Postcondición: elemento agregado al inicio, size incrementado
     * Complejidad: O(1)
     */
    public void addFirst(T value) {
        Node<T> newNode = new Node<>(value);
        newNode.setNext(head);
        head = newNode;
        size++;
    }
    
    /**
     * Agrega un elemento al final de la lista
     * Precondición: ninguna
     * Postcondición: elemento agregado al final, size incrementado
     * Complejidad: O(1) si se mantiene tail, O(n) en esta implementación
     */
    public void addLast(T value) {
        Node<T> newNode = new Node<>(value);
        
        if (isEmpty()) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
        size++;
    }
    
    /**
     * Remueve y retorna el primer elemento de la lista
     * Precondición: lista no vacía
     * Postcondición: primer elemento removido, size decrementado
     * Complejidad: O(1)
     * @throws EmptyListException si la lista está vacía
     */
    public T removeFirst() {
        if (isEmpty()) {
            throw new EmptyListException("No se puede remover de una lista vacía");
        }
        
        T data = head.getData();
        head = head.getNext();
        size--;
        return data;
    }
    
    /**
     * Remueve y retorna el último elemento de la lista
     * Precondición: lista no vacía
     * Postcondición: último elemento removido, size decrementado
     * Complejidad: O(n)
     * @throws EmptyListException si la lista está vacía
     */
    public T removeLast() {
        if (isEmpty()) {
            throw new EmptyListException("No se puede remover de una lista vacía");
        }
        
        if (size == 1) {
            T data = head.getData();
            head = null;
            size = 0;
            return data;
        }
        
        Node<T> current = head;
        while (current.getNext().getNext() != null) {
            current = current.getNext();
        }
        
        T data = current.getNext().getData();
        current.setNext(null);
        size--;
        return data;
    }
    
    /**
     * Obtiene el elemento en el índice especificado
     * Precondición: 0 <= index < size
     * Postcondición: lista sin cambios
     * Complejidad: O(n)
     * @throws InvalidIndexException si el índice es inválido
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new InvalidIndexException("Índice inválido: " + index + ". Tamaño de lista: " + size);
        }
        
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getData();
    }
    
    /**
     * Verifica si la lista está vacía
     * Complejidad: O(1)
     */
    public boolean isEmpty() {
        return head == null;
    }
    
    /**
     * Retorna el número de elementos en la lista
     * Complejidad: O(1)
     */
    public int size() {
        return size;
    }
    
    /**
     * Vacía la lista
     * Precondición: ninguna
     * Postcondición: lista vacía con size = 0
     */
    public void clear() {
        head = null;
        size = 0;
    }
    
    /**
     * Verifica si la lista contiene un elemento específico
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
     * Inserta un elemento en el índice especificado
     * Precondición: 0 <= index <= size
     * Postcondición: elemento insertado en el índice, size incrementado
     * Complejidad: O(n)
     * @throws InvalidIndexException si el índice es inválido
     */
    public void insertAt(int index, T value) {
        if (index < 0 || index > size) {
            throw new InvalidIndexException("Índice inválido: " + index + ". Tamaño de lista: " + size);
        }
        
        if (index == 0) {
            addFirst(value);
            return;
        }
        
        Node<T> newNode = new Node<>(value);
        Node<T> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.getNext();
        }
        
        newNode.setNext(current.getNext());
        current.setNext(newNode);
        size++;
    }
    
    /**
     * Remueve y retorna el elemento en el índice especificado
     * Precondición: 0 <= index < size
     * Postcondición: elemento removido, size decrementado
     * Complejidad: O(n)
     * @throws InvalidIndexException si el índice es inválido
     */
    public T removeAt(int index) {
        if (index < 0 || index >= size) {
            throw new InvalidIndexException("Índice inválido: " + index + ". Tamaño de lista: " + size);
        }
        
        if (index == 0) {
            return removeFirst();
        }
        
        Node<T> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.getNext();
        }
        
        T data = current.getNext().getData();
        current.setNext(current.getNext().getNext());
        size--;
        return data;
    }
    
    /**
     * Convierte la lista a un arreglo
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
    
    /**
     * Retorna una representación en cadena de la lista
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        
        StringBuilder sb = new StringBuilder("[");
        Node<T> current = head;
        while (current != null) {
            sb.append(current.getData());
            if (current.getNext() != null) {
                sb.append(", ");
            }
            current = current.getNext();
        }
        sb.append("]");
        return sb.toString();
    }
}

