package data;

import model.Disciplina;
import util.ListaEncadeada;

import java.io.*;

public class DisciplinaRepository {
    private static final String ARQUIVO = "disciplinas.csv";

    public ListaEncadeada<Disciplina> listar() {
        ListaEncadeada<Disciplina> lista = new ListaEncadeada<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 6) {
                    Disciplina d = new Disciplina(
                        partes[0], partes[1], partes[2],
                        partes[3], Integer.parseInt(partes[4]),
                        partes[5]
                    );
                    lista.adicionar(d);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler disciplinas: " + e.getMessage());
        }
        return lista;
    }

    public void salvar(ListaEncadeada<Disciplina> disciplinas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (int i = 0; i < disciplinas.tamanho(); i++) {
                bw.write(disciplinas.get(i).toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar disciplinas: " + e.getMessage());
        }
    }
}
