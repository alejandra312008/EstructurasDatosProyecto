/**
 * Implementación de lista doblemente enlazada genérica
 * Complejidad: O(1) para addFirst, addLast, removeFirst, removeLast
 *              O(n) para get, insertAt, removeAt
 * Ventaja: permite recorrer en ambos sentidos (next y prev)
 */
public class DoublyLinkedList<T> {
    private DoublyNode<T> head;
    private DoublyNode<T> tail;
    private int size;
    
    /**
     * Constructor: crea una lista vacía
     * Precondición: ninguna
     * Postcondición: lista vacía con size = 0, head = tail = null
     */
    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
    
    /**
     * Agrega un elemento al inicio de la lista
     * Precondición: ninguna
     * Postcondición: elemento agregado al inicio, size incrementado
     * Complejidad: O(1)
     */
    public void addFirst(T value) {
        DoublyNode<T> newNode = new DoublyNode<>(value);
        
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.setNext(head);
            head.setPrev(newNode);
            head = newNode;
        }
        size++;
    }
    
    /**
     * Agrega un elemento al final de la lista
     * Precondición: ninguna
     * Postcondición: elemento agregado al final, size incrementado
     * Complejidad: O(1)
     */
    public void addLast(T value) {
        DoublyNode<T> newNode = new DoublyNode<>(value);
        
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.setPrev(tail);
            tail.setNext(newNode);
            tail = newNode;
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
        
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            head = head.getNext();
            head.setPrev(null);
        }
        size--;
        return data;
    }
    
    /**
     * Remueve y retorna el último elemento de la lista
     * Precondición: lista no vacía
     * Postcondición: último elemento removido, size decrementado
     * Complejidad: O(1)
     * @throws EmptyListException si la lista está vacía
     */
    public T removeLast() {
        if (isEmpty()) {
            throw new EmptyListException("No se puede remover de una lista vacía");
        }
        
        T data = tail.getData();
        
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            tail = tail.getPrev();
            tail.setNext(null);
        }
        size--;
        return data;
    }
    
    /**
     * Obtiene el elemento en el índice especificado
     * Precondición: 0 <= index < size
     * Postcondición: lista sin cambios
     * Complejidad: O(n) - optimizable a O(n/2) recorriendo desde el extremo más cercano
     * @throws InvalidIndexException si el índice es inválido
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new InvalidIndexException("Índice inválido: " + index + ". Tamaño de lista: " + size);
        }
        
        // Optimización: recorrer desde el extremo más cercano
        DoublyNode<T> current;
        if (index < size / 2) {
            // Recorrer desde el inicio
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
        } else {
            // Recorrer desde el final
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.getPrev();
            }
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
     * Postcondición: lista vacía con size = 0, head = tail = null
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }
    
    /**
     * Verifica si la lista contiene un elemento específico
     * Complejidad: O(n)
     */
    public boolean contains(T value) {
        DoublyNode<T> current = head;
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
        
        if (index == size) {
            addLast(value);
            return;
        }
        
        // Encontrar el nodo en la posición index
        DoublyNode<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.getPrev();
            }
        }
        
        DoublyNode<T> newNode = new DoublyNode<>(value);
        newNode.setPrev(current.getPrev());
        newNode.setNext(current);
        current.getPrev().setNext(newNode);
        current.setPrev(newNode);
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
        
        if (index == size - 1) {
            return removeLast();
        }
        
        // Encontrar el nodo en la posición index
        DoublyNode<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.getPrev();
            }
        }
        
        T data = current.getData();
        current.getPrev().setNext(current.getNext());
        current.getNext().setPrev(current.getPrev());
        size--;
        return data;
    }
    
    /**
     * Agrega un elemento antes del nodo especificado
     * Precondición: node != null y node pertenece a esta lista
     * Postcondición: elemento agregado antes del nodo, size incrementado
     * Complejidad: O(1)
     */
    public void addBefore(DoublyNode<T> node, T value) {
        if (node == null) {
            throw new IllegalArgumentException("El nodo no puede ser null");
        }
        
        DoublyNode<T> newNode = new DoublyNode<>(value);
        
        if (node == head) {
            addFirst(value);
            return;
        }
        
        newNode.setPrev(node.getPrev());
        newNode.setNext(node);
        node.getPrev().setNext(newNode);
        node.setPrev(newNode);
        size++;
    }
    
    /**
     * Agrega un elemento después del nodo especificado
     * Precondición: node != null y node pertenece a esta lista
     * Postcondición: elemento agregado después del nodo, size incrementado
     * Complejidad: O(1)
     */
    public void addAfter(DoublyNode<T> node, T value) {
        if (node == null) {
            throw new IllegalArgumentException("El nodo no puede ser null");
        }
        
        DoublyNode<T> newNode = new DoublyNode<>(value);
        
        if (node == tail) {
            addLast(value);
            return;
        }
        
        newNode.setPrev(node);
        newNode.setNext(node.getNext());
        node.getNext().setPrev(newNode);
        node.setNext(newNode);
        size++;
    }
    
    /**
     * Remueve el nodo especificado de la lista
     * Precondición: node != null y node pertenece a esta lista
     * Postcondición: nodo removido, size decrementado
     * Complejidad: O(1)
     */
    public T remove(DoublyNode<T> node) {
        if (node == null) {
            throw new IllegalArgumentException("El nodo no puede ser null");
        }
        
        if (node == head) {
            return removeFirst();
        }
        
        if (node == tail) {
            return removeLast();
        }
        
        T data = node.getData();
        node.getPrev().setNext(node.getNext());
        node.getNext().setPrev(node.getPrev());
        size--;
        return data;
    }
    
    /**
     * Obtiene el nodo en el índice especificado (método auxiliar para addBefore/addAfter)
     * Precondición: 0 <= index < size
     * Postcondición: lista sin cambios
     * Complejidad: O(n)
     */
    public DoublyNode<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new InvalidIndexException("Índice inválido: " + index + ". Tamaño de lista: " + size);
        }
        
        DoublyNode<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.getPrev();
            }
        }
        return current;
    }
    
    /**
     * Obtiene el primer nodo (head)
     */
    public DoublyNode<T> getFirstNode() {
        return head;
    }
    
    /**
     * Obtiene el último nodo (tail)
     */
    public DoublyNode<T> getLastNode() {
        return tail;
    }
    
    /**
     * Convierte la lista a un arreglo
     * Complejidad: O(n)
     */
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] array = (T[]) new Object[size];
        DoublyNode<T> current = head;
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
        DoublyNode<T> current = head;
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

