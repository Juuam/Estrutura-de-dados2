package util;

public class Fila<T> {
    private ListaEncadeada<T> lista = new ListaEncadeada<>();

    // Insere no final da fila
    public void enqueue(T elemento) {
        lista.adicionar(elemento);
    }

    // Remove e retorna o elemento do início da fila
    public T dequeue() {
        if (lista.tamanho() == 0) {
            return null; // ou lançar exceção, se preferir
        }
        T primeiro = lista.get(0);
        lista.remover(primeiro);
        return primeiro;
    }

    // Retorna o elemento do início da fila sem remover
    public T peek() {
        if (lista.tamanho() == 0) {
            return null;
        }
        return lista.get(0);
    }

    public int tamanho() {
        return lista.tamanho();
    }

    // Iterador simples (se quiser)
    public java.util.Iterator<T> iterator() {
        return lista.iterator(); // assumindo que ListaEncadeada implementa Iterator
    }

    // Caso queira expor a lista completa para iteração for-each
    public ListaEncadeada<T> getLista() {
        return lista;
    }

}
