package util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HashTable<K, V> {

    public static class Entrada<K, V> {
        private K chave;
        private V valor;

        Entrada(K chave, V valor) {
            this.chave = chave;
            this.valor = valor;
        }

        public K getChave() {
            return chave;
        }

        public V getValor() {
            return valor;
        }
    }

    private LinkedList<Entrada<K, V>>[] tabela;
    private int capacidade;

    @SuppressWarnings("unchecked")
    public HashTable(int capacidade) {
        this.capacidade = capacidade;
        tabela = new LinkedList[capacidade];
        for (int i = 0; i < capacidade; i++) {
            tabela[i] = new LinkedList<>();
        }
    }

    private int hash(K chave) {
        return Math.abs(chave.hashCode()) % capacidade;
    }

    public void inserir(K chave, V valor) {
        int indice = hash(chave);
        LinkedList<Entrada<K, V>> lista = tabela[indice];

        // Não insere duplicado para mesma chave
        for (Entrada<K, V> e : lista) {
            if (e.getChave().equals(chave)) {
                return;
            }
        }

        lista.add(new Entrada<>(chave, valor));
    }

    public LinkedList<Entrada<K, V>> buscarLista(K chave) {
        int indice = hash(chave);
        return tabela[indice];
    }

    public LinkedList<Entrada<K, V>>[] todasEntradas() {
        return tabela;
    }

    public List<K> getChaves() {
        List<K> chaves = new ArrayList<>();
        for (LinkedList<Entrada<K, V>> lista : tabela) {
            if (lista != null) {
                for (Entrada<K, V> e : lista) {
                    if (!chaves.contains(e.getChave())) {
                        chaves.add(e.getChave());
                    }
                }
            }
        }
        return chaves;
    }
}
