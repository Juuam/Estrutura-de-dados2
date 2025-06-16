package controller;

import data.ProfessorRepository;
import model.Professor;
import util.ListaEncadeada;

public class ProfessorController {
    private ProfessorRepository repo = new ProfessorRepository();
    private ListaEncadeada<Professor> professores = repo.listar();

    public ListaEncadeada<Professor> listar() {
        return professores;
    }

    public void adicionar(Professor p) {
        professores.adicionar(p);
        repo.salvar(professores);
    }

    public boolean remover(String cpf) {
        for (int i = 0; i < professores.tamanho(); i++) {
            if (professores.get(i).getCpf().equals(cpf)) {
                professores.remover(professores.get(i));
                repo.salvar(professores);
                return true;
            }
        }
        return false;
    }

    public boolean atualizar(String cpf, Professor novo) {
        for (int i = 0; i < professores.tamanho(); i++) {
            if (professores.get(i).getCpf().equals(cpf)) {
                professores.remover(professores.get(i));
                professores.adicionar(novo);
                repo.salvar(professores);
                return true;
            }
        }
        return false;
    }

    public Professor buscarPorCpf(String cpf) {
        for (int i = 0; i < professores.tamanho(); i++) {
            if (professores.get(i).getCpf().equals(cpf)) {
                return professores.get(i);
            }
        }
        return null;
    }
}
