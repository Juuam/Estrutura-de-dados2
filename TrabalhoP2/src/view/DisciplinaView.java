package view;

import controller.DisciplinaController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Disciplina;

public class DisciplinaView extends Application {

    private DisciplinaController controller = new DisciplinaController();
    private ObservableList<String> disciplinaList = FXCollections.observableArrayList();
    private ListView<String> listView = new ListView<>(disciplinaList);

    private TextField codigoField = new TextField();
    private TextField nomeField = new TextField();
    private TextField diaSemanaField = new TextField();
    private TextField horarioInicialField = new TextField();
    private TextField horasDiariasField = new TextField();
    private TextField codigoCursoField = new TextField();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Gerenciador de Disciplinas");

        codigoField.setPromptText("Código");
        nomeField.setPromptText("Nome");
        diaSemanaField.setPromptText("Dia da Semana");
        horarioInicialField.setPromptText("Horário Inicial");
        horasDiariasField.setPromptText("Horas Diárias");
        codigoCursoField.setPromptText("Código do Curso");

        Button btnAdicionar = new Button("Adicionar");
        Button btnRemover = new Button("Remover");
        Button btnAtualizar = new Button("Atualizar");
        Button btnBuscar = new Button("Buscar");
        Button btnListarTodas = new Button("Listar Todas");

        btnAdicionar.setOnAction(e -> adicionarDisciplina());
        btnRemover.setOnAction(e -> removerDisciplina());
        btnAtualizar.setOnAction(e -> atualizarDisciplina());
        btnBuscar.setOnAction(e -> buscarDisciplina());
        btnListarTodas.setOnAction(e -> atualizarLista());

        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);
        inputGrid.setPadding(new Insets(10));

        inputGrid.add(new Label("Código:"), 0, 0);
        inputGrid.add(codigoField, 1, 0);
        inputGrid.add(new Label("Nome:"), 0, 1);
        inputGrid.add(nomeField, 1, 1);
        inputGrid.add(new Label("Dia Semana:"), 0, 2);
        inputGrid.add(diaSemanaField, 1, 2);
        inputGrid.add(new Label("Horário Inicial:"), 0, 3);
        inputGrid.add(horarioInicialField, 1, 3);
        inputGrid.add(new Label("Horas Diárias:"), 0, 4);
        inputGrid.add(horasDiariasField, 1, 4);
        inputGrid.add(new Label("Código Curso:"), 0, 5);
        inputGrid.add(codigoCursoField, 1, 5);

        HBox botoes = new HBox(10, btnAdicionar, btnAtualizar, btnBuscar, btnRemover, btnListarTodas);
        VBox layout = new VBox(10, inputGrid, botoes, listView);
        layout.setPadding(new Insets(10));

        atualizarLista();

        Scene scene = new Scene(layout, 800, 500);
        stage.setScene(scene);
        stage.show();
    }

    private void atualizarLista() {
        disciplinaList.clear();
        var disciplinas = controller.listar();
        for (int i = 0; i < disciplinas.tamanho(); i++) {
            Disciplina d = disciplinas.get(i);
            disciplinaList.add(
                    "Código: " + d.getCodigo() +
                            " | Nome: " + d.getNome() +
                            " | Dia: " + d.getDiaSemana() +
                            " | Início: " + d.getHorarioInicial() +
                            " | Horas: " + d.getHorasDiarias() +
                            " | Curso: " + d.getCodigoCurso());
        }
    }

    private void adicionarDisciplina() {
        try {
            Disciplina d = lerDisciplinaDosCampos();
            controller.adicionar(d);
            atualizarLista();
            limparCampos();
        } catch (Exception e) {
            showAlert("Erro ao adicionar: " + e.getMessage());
        }
    }

    private void removerDisciplina() {
        String codigo = codigoField.getText().trim();

        if (codigo.isEmpty()) {
            showAlert("Informe o código da disciplina.");
            return;
        }

        if (controller.remover(codigo)) {
            showAlert("Disciplina (e inscrições relacionadas) removida com sucesso.");
            atualizarLista();
            limparCampos();
        } else {
            showAlert("Disciplina não encontrada.");
        }
    }

    private void atualizarDisciplina() {
        try {
            Disciplina d = lerDisciplinaDosCampos();
            if (controller.atualizar(d.getCodigo(), d)) {
                showAlert("Disciplina atualizada.");
                atualizarLista();
                limparCampos();
            } else {
                showAlert("Disciplina não encontrada.");
            }
        } catch (Exception e) {
            showAlert("Erro: " + e.getMessage());
        }
    }

    private void buscarDisciplina() {
        String codigo = codigoField.getText().trim();
        if (codigo.isEmpty()) {
            showAlert("Informe o código da disciplina.");
            return;
        }

        Disciplina d = controller.buscarPorCodigo(codigo);
        if (d != null) {
            nomeField.setText(d.getNome());
            diaSemanaField.setText(d.getDiaSemana());
            horarioInicialField.setText(d.getHorarioInicial());
            horasDiariasField.setText(String.valueOf(d.getHorasDiarias()));
            codigoCursoField.setText(d.getCodigoCurso());
        } else {
            showAlert("Disciplina não encontrada.");
        }
    }

    private Disciplina lerDisciplinaDosCampos() {
        String codigo = codigoField.getText().trim();
        String nome = nomeField.getText().trim();
        String diaSemana = diaSemanaField.getText().trim();
        String horarioInicial = horarioInicialField.getText().trim();
        String horasStr = horasDiariasField.getText().trim();
        String codigoCurso = codigoCursoField.getText().trim();

        if (codigo.isEmpty() || nome.isEmpty() || diaSemana.isEmpty() || horarioInicial.isEmpty() || horasStr.isEmpty()
                || codigoCurso.isEmpty()) {
            throw new IllegalArgumentException("Todos os campos devem ser preenchidos.");
        }

        int horasDiarias;
        try {
            horasDiarias = Integer.parseInt(horasStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Horas Diárias deve ser um número inteiro.");
        }

        return new Disciplina(codigo, nome, diaSemana, horarioInicial, horasDiarias, codigoCurso);
    }

    private void limparCampos() {
        codigoField.clear();
        nomeField.clear();
        diaSemanaField.clear();
        horarioInicialField.clear();
        horasDiariasField.clear();
        codigoCursoField.clear();
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}
