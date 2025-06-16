package data;

import model.Curso;
import util.ListaEncadeada;

import java.io.*;

public class CursoRepository {
    private static final String ARQUIVO = "cursos.csv";

    public ListaEncadeada<Curso> listar() {
        ListaEncadeada<Curso> lista = new ListaEncadeada<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 3) {
                    Curso curso = new Curso(partes[0], partes[1], partes[2]);
                    lista.adicionar(curso);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler cursos: " + e.getMessage());
        }
        return lista;
    }

    public void salvar(ListaEncadeada<Curso> cursos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (int i = 0; i < cursos.tamanho(); i++) {
                bw.write(cursos.get(i).toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar cursos: " + e.getMessage());
        }
    }
}
