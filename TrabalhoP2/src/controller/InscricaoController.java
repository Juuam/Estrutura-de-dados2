package controller;

import data.InscricaoRepository;
import model.Inscricao;
import util.ListaEncadeada;

public class InscricaoController {
    private InscricaoRepository repo = new InscricaoRepository();
    private ListaEncadeada<Inscricao> inscricoes = repo.listar();

    public ListaEncadeada<Inscricao> listar() {
        return inscricoes;
    }

    public void adicionar(Inscricao i) {
        inscricoes.adicionar(i);
        repo.salvar(inscricoes);
    }

    public boolean remover(String cpf, String codDisciplina) {
        for (int i = 0; i < inscricoes.tamanho(); i++) {
            Inscricao ins = inscricoes.get(i);
            if (ins.getCpfProfessor().equals(cpf) && ins.getCodigoDisciplina().equals(codDisciplina)) {
                inscricoes.remover(ins);
                repo.salvar(inscricoes);
                return true;
            }
        }
        return false;
    }

    public ListaEncadeada<Inscricao> buscarPorDisciplina(String codDisciplina) {
        ListaEncadeada<Inscricao> lista = new ListaEncadeada<>();
        for (int i = 0; i < inscricoes.tamanho(); i++) {
            if (inscricoes.get(i).getCodigoDisciplina().equals(codDisciplina)) {
                lista.adicionar(inscricoes.get(i));
            }
        }
        return lista;
    }

    public void salvarAlteracoes() {
        repo.salvar(inscricoes);
    }
}
