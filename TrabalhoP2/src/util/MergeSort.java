package util;

import model.Professor;

public class MergeSort {

    public static void ordenar(Professor[] array) {
        if (array.length <= 1) return;
        mergeSort(array, 0, array.length - 1);
    }

    private static void mergeSort(Professor[] array, int inicio, int fim) {
        if (inicio < fim) {
            int meio = (inicio + fim) / 2;
            mergeSort(array, inicio, meio);
            mergeSort(array, meio + 1, fim);
            merge(array, inicio, meio, fim);
        }
    }

    private static void merge(Professor[] array, int inicio, int meio, int fim) {
        int n1 = meio - inicio + 1;
        int n2 = fim - meio;

        Professor[] esquerda = new Professor[n1];
        Professor[] direita = new Professor[n2];

        for (int i = 0; i < n1; i++) esquerda[i] = array[inicio + i];
        for (int j = 0; j < n2; j++) direita[j] = array[meio + 1 + j];

        int i = 0, j = 0, k = inicio;

        while (i < n1 && j < n2) {
            if (esquerda[i].getPontuacao() >= direita[j].getPontuacao()) {
                array[k++] = esquerda[i++];
            } else {
                array[k++] = direita[j++];
            }
        }

        while (i < n1) array[k++] = esquerda[i++];
        while (j < n2) array[k++] = direita[j++];
    }
}
