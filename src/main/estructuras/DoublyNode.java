/**
 * Nodo genérico para implementar listas doblemente enlazadas
 */
public class DoublyNode<T> {
    T data;
    DoublyNode<T> next;
    DoublyNode<T> prev;
    
    public DoublyNode(T data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
    
    public DoublyNode<T> getNext() {
        return next;
    }
    
    public void setNext(DoublyNode<T> next) {
        this.next = next;
    }
    
    public DoublyNode<T> getPrev() {
        return prev;
    }
    
    public void setPrev(DoublyNode<T> prev) {
        this.prev = prev;
    }
}

