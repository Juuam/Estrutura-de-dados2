package util;

import java.util.Iterator;

public class ListaEncadeada<T> implements Iterable<T> {
    private No<T> inicio;
    private int tamanho;

    private static class No<T> {
        T elemento;
        No<T> proximo;

        No(T elemento) {
            this.elemento = elemento;
            this.proximo = null;
        }
    }

    public void adicionar(T elemento) {
        No<T> novo = new No<>(elemento);
        if (inicio == null) {
            inicio = novo;
        } else {
            No<T> atual = inicio;
            while (atual.proximo != null) {
                atual = atual.proximo;
            }
            atual.proximo = novo;
        }
        tamanho++;
    }

    public boolean remover(T elemento) {
        if (inicio == null)
            return false;

        if (inicio.elemento.equals(elemento)) {
            inicio = inicio.proximo;
            tamanho--;
            return true;
        }

        No<T> atual = inicio;
        while (atual.proximo != null && !atual.proximo.elemento.equals(elemento)) {
            atual = atual.proximo;
        }

        if (atual.proximo != null) {
            atual.proximo = atual.proximo.proximo;
            tamanho--;
            return true;
        }

        return false;
    }

    public T get(int index) {
        if (index < 0 || index >= tamanho)
            throw new IndexOutOfBoundsException();

        No<T> atual = inicio;
        for (int i = 0; i < index; i++) {
            atual = atual.proximo;
        }
        return atual.elemento;
    }

    public int tamanho() {
        return tamanho;
    }

    public void limpar() {
        inicio = null;
        tamanho = 0;
    }

    public T[] paraArray(T[] tipo) {
        int i = 0;
        No<T> atual = inicio;
        while (atual != null) {
            tipo[i++] = atual.elemento;
            atual = atual.proximo;
        }
        return tipo;
    }

    public No<T> getInicio() {
        return inicio;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private No<T> atual = inicio;

            @Override
            public boolean hasNext() {
                return atual != null;
            }

            @Override
            public T next() {
                T elemento = atual.elemento;
                atual = atual.proximo;
                return elemento;
            }
        };
    }
}