package controller;

import data.DisciplinaRepository;
import data.InscricaoRepository;
import model.Disciplina;
import model.Inscricao;
import util.ListaEncadeada;

public class DisciplinaController {
    private DisciplinaRepository repo = new DisciplinaRepository();
    private InscricaoRepository inscricaoRepo = new InscricaoRepository();

    private ListaEncadeada<Disciplina> disciplinas = repo.listar();
    private ListaEncadeada<Inscricao> inscricoes = inscricaoRepo.listar();

    public ListaEncadeada<Disciplina> listar() {
        return disciplinas;
    }

    public void adicionar(Disciplina d) {
        disciplinas.adicionar(d);
        repo.salvar(disciplinas);
    }

    public boolean remover(String codigoDisciplina) {
        boolean removido = false;

        for (int i = 0; i < disciplinas.tamanho(); i++) {
            if (disciplinas.get(i).getCodigo().equals(codigoDisciplina)) {
                disciplinas.remover(disciplinas.get(i));
                removido = true;
                break;
            }
        }

        if (removido) {
            // Remover todas as inscrições dessa disciplina
            for (int i = 0; i < inscricoes.tamanho(); i++) {
                if (inscricoes.get(i).getCodigoDisciplina().equals(codigoDisciplina)) {
                    inscricoes.remover(inscricoes.get(i));
                    i--; // ajusta índice após remoção
                }
            }
            repo.salvar(disciplinas);
            inscricaoRepo.salvar(inscricoes);
        }

        return removido;
    }

    public boolean atualizar(String codigo, Disciplina nova) {
        for (int i = 0; i < disciplinas.tamanho(); i++) {
            if (disciplinas.get(i).getCodigo().equals(codigo)) {
                disciplinas.remover(disciplinas.get(i));
                disciplinas.adicionar(nova);
                repo.salvar(disciplinas);
                return true;
            }
        }
        return false;
    }

    public Disciplina buscarPorCodigo(String codigo) {
        for (int i = 0; i < disciplinas.tamanho(); i++) {
            if (disciplinas.get(i).getCodigo().equals(codigo)) {
                return disciplinas.get(i);
            }
        }
        return null;
    }
}
