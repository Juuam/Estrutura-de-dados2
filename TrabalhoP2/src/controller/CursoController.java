package controller;

import data.CursoRepository;
import model.Curso;
import util.Fila;

public class CursoController {
    private CursoRepository repo = new CursoRepository();
    private Fila<Curso> cursos = new Fila<>();

    public CursoController() {
        // Popula a fila com os dados do arquivo
        for (Curso c : repo.listar()) {
            cursos.enqueue(c);
        }
    }

    public Fila<Curso> listar() {
        return cursos;
    }

    public void adicionar(Curso curso) {
        cursos.enqueue(curso);
        // Como a fila encapsula ListaEncadeada, para salvar pode acessar a lista:
        repo.salvar(cursos.getLista());
    }

    public boolean remover(String codigo) {
        // Aqui você precisaria iterar a fila para encontrar e remover
        for (int i = 0; i < cursos.tamanho(); i++) {
            Curso c = cursos.getLista().get(i);
            if (c.getCodigo().equals(codigo)) {
                cursos.getLista().remover(c);
                repo.salvar(cursos.getLista());
                return true;
            }
        }
        return false;
    }

    public boolean atualizar(String codigo, Curso novo) {
        for (int i = 0; i < cursos.tamanho(); i++) {
            Curso c = cursos.getLista().get(i);
            if (c.getCodigo().equals(codigo)) {
                cursos.getLista().remover(c);
                cursos.enqueue(novo);
                repo.salvar(cursos.getLista());
                return true;
            }
        }
        return false;
    }

    public Curso buscarPorCodigo(String codigo) {
        for (int i = 0; i < cursos.tamanho(); i++) {
            Curso c = cursos.getLista().get(i);
            if (c.getCodigo().equals(codigo)) {
                return c;
            }
        }
        return null;
    }
}
