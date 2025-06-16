package data;

import model.Inscricao;
import util.ListaEncadeada;

import java.io.*;

public class InscricaoRepository {
    private static final String ARQUIVO = "inscricoes.csv";

    public ListaEncadeada<Inscricao> listar() {
        ListaEncadeada<Inscricao> lista = new ListaEncadeada<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 3) {
                    Inscricao i = new Inscricao(partes[0], partes[1], partes[2]);
                    lista.adicionar(i);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler inscrições: " + e.getMessage());
        }
        return lista;
    }

    public void salvar(ListaEncadeada<Inscricao> inscricoes) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (int i = 0; i < inscricoes.tamanho(); i++) {
                bw.write(inscricoes.get(i).toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar inscrições: " + e.getMessage());
        }
    }
}
