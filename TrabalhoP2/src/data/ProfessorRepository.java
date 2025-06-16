package data;

import model.Professor;
import util.ListaEncadeada;

import java.io.*;

public class ProfessorRepository {
    private static final String ARQUIVO = "professor.csv";

    public ListaEncadeada<Professor> listar() {
        ListaEncadeada<Professor> lista = new ListaEncadeada<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 4) {
                    Professor p = new Professor(
                        partes[0], partes[1], partes[2],
                        Integer.parseInt(partes[3])
                    );
                    lista.adicionar(p);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler professores: " + e.getMessage());
        }
        return lista;
    }

    public void salvar(ListaEncadeada<Professor> professores) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (int i = 0; i < professores.tamanho(); i++) {
                bw.write(professores.get(i).toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar professores: " + e.getMessage());
        }
    }
}
