package view;

import controller.InscricaoController;
import controller.ProfessorController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Inscricao;
import model.Professor;
import util.ListaEncadeada;
import util.MergeSort;

public class ConsultaView {

    private InscricaoController inscricaoController = new InscricaoController();
    private ProfessorController professorController = new ProfessorController();

    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Consulta de Inscritos por Disciplina");

        TextField txtDisciplina = new TextField();
        Button btnBuscar = new Button("Buscar");
        TextArea resultado = new TextArea();
        resultado.setEditable(false);

        btnBuscar.setOnAction(e -> {
            String codDisc = txtDisciplina.getText().trim();

            if (codDisc.isEmpty()) {
                resultado.setText("Por favor, insira um código de disciplina.");
                return;
            }

            ListaEncadeada<Inscricao> inscricoes = inscricaoController.buscarPorDisciplina(codDisc);

            if (inscricoes.tamanho() == 0) {
                resultado.setText("Nenhuma inscrição encontrada para a disciplina " + codDisc);
                return;
            }

            ListaEncadeada<Professor> listaProfessores = new ListaEncadeada<>();
            for (int i = 0; i < inscricoes.tamanho(); i++) {
                Inscricao ins = inscricoes.get(i);
                Professor p = professorController.buscarPorCpf(ins.getCpfProfessor());
                if (p != null) {
                    listaProfessores.adicionar(p);
                }
            }

            if (listaProfessores.tamanho() == 0) {
                resultado.setText("Nenhum professor encontrado para as inscrições desta disciplina.");
                return;
            }

            Professor[] arrayProfessores = new Professor[listaProfessores.tamanho()];
            listaProfessores.paraArray(arrayProfessores);

            MergeSort.ordenar(arrayProfessores);

            StringBuilder sb = new StringBuilder("Inscritos na disciplina " + codDisc + ":\n\n");
            for (Professor p : arrayProfessores) {
                sb.append("Nome: ").append(p.getNome())
                        .append(" | Área: ").append(p.getArea())
                        .append(" | CPF: ").append(p.getCpf())
                        .append(" | Pontuação: ").append(p.getPontuacao())
                        .append("\n");
            }

            resultado.setText(sb.toString());
        });

        VBox layout = new VBox(10,
                new HBox(10, new Label("Código Disciplina:"), txtDisciplina, btnBuscar),
                resultado);
        layout.setPadding(new Insets(10));

        stage.setScene(new Scene(layout, 600, 400));
        stage.show();
    }
}
