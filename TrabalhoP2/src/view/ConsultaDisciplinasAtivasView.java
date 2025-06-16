package view;

import controller.CursoController;
import controller.DisciplinaController;
import controller.InscricaoController;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Curso;
import model.Disciplina;
import model.Inscricao;
import util.HashTable;
import util.ListaEncadeada;

public class ConsultaDisciplinasAtivasView {

    private InscricaoController inscricaoController = new InscricaoController();
    private DisciplinaController disciplinaController = new DisciplinaController();
    private CursoController cursoController = new CursoController();

    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Disciplinas com Processos Abertos por Curso");

        TextArea areaResultado = new TextArea();
        areaResultado.setEditable(false);

        HashTable<String, Disciplina> tabela = new HashTable<>(100);
        ListaEncadeada<Inscricao> inscricoes = inscricaoController.listar();

        for (int i = 0; i < inscricoes.tamanho(); i++) {
            Inscricao iAtual = inscricoes.get(i);
            Disciplina d = disciplinaController.buscarPorCodigo(iAtual.getCodigoDisciplina());
            if (d != null) {
                Curso c = cursoController.buscarPorCodigo(d.getCodigoCurso());
                if (c != null) {
                    tabela.inserir(c.getNome(), d);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (String nomeCurso : tabela.getChaves()) {
            sb.append("Curso: ").append(nomeCurso).append("\n");

            var lista = tabela.buscarLista(nomeCurso); // LinkedList<Entrada<String, Disciplina>>
            ListaEncadeada<Disciplina> disciplinas = new ListaEncadeada<>();

            // Transferência para ListaEncadeada
            for (var entrada : lista) {
                disciplinas.adicionar(entrada.getValor());
            }

            for (int i = 0; i < disciplinas.tamanho(); i++) {
                Disciplina d = disciplinas.get(i);
                sb.append("  - ").append(d.getCodigo())
                        .append(": ").append(d.getNome())
                        .append(" (").append(d.getDiaSemana())
                        .append(" às ").append(d.getHorarioInicial())
                        .append(", ").append(d.getHorasDiarias()).append("h)\n");
            }
            sb.append("\n");
        }

        areaResultado.setText(sb.toString());

        VBox layout = new VBox(areaResultado);
        layout.setPadding(new javafx.geometry.Insets(10));
        stage.setScene(new Scene(layout, 700, 500));
        stage.show();
    }
}
