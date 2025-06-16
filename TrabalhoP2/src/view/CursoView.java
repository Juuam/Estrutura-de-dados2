package view;

import controller.CursoController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Curso;
import util.ListaEncadeada;

public class CursoView extends Application {

    private CursoController controller = new CursoController();
    private ObservableList<String> cursoList = FXCollections.observableArrayList();
    private ListView<String> listView = new ListView<>(cursoList);

    private TextField codigoField = new TextField();
    private TextField nomeField = new TextField();
    private TextField areaField = new TextField();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Gerenciador de Cursos");

        codigoField.setPromptText("Código");
        nomeField.setPromptText("Nome");
        areaField.setPromptText("Área");

        Button btnAdicionar = new Button("Adicionar");
        Button btnRemover = new Button("Remover");
        Button btnAtualizar = new Button("Atualizar");
        Button btnBuscar = new Button("Buscar");
        Button btnListarTodas = new Button("Listar Todas");

        btnAdicionar.setOnAction(e -> adicionarCurso());
        btnRemover.setOnAction(e -> removerCurso());
        btnAtualizar.setOnAction(e -> atualizarCurso());
        btnBuscar.setOnAction(e -> buscarCurso());
        btnListarTodas.setOnAction(e -> atualizarLista());

        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);
        inputGrid.setPadding(new Insets(10));

        inputGrid.add(new Label("Código:"), 0, 0);
        inputGrid.add(codigoField, 1, 0);
        inputGrid.add(new Label("Nome:"), 0, 1);
        inputGrid.add(nomeField, 1, 1);
        inputGrid.add(new Label("Área:"), 0, 2);
        inputGrid.add(areaField, 1, 2);

        HBox botoes = new HBox(10, btnAdicionar, btnAtualizar, btnBuscar, btnRemover, btnListarTodas);
        VBox layout = new VBox(10, inputGrid, botoes, listView);
        layout.setPadding(new Insets(10));

        atualizarLista();

        Scene scene = new Scene(layout, 700, 400);
        stage.setScene(scene);
        stage.show();
    }

    private void atualizarLista() {
        cursoList.clear();
        ListaEncadeada<Curso> cursos = controller.listar().getLista();

        for (int i = 0; i < cursos.tamanho(); i++) {
            Curso c = cursos.get(i);
            cursoList.add("Código: " + c.getCodigo() + " | Nome: " + c.getNome() + " | Área: " + c.getArea());
        }
    }

    private void adicionarCurso() {
        try {
            Curso c = lerCursoDosCampos();
            controller.adicionar(c);
            atualizarLista();
            limparCampos();
        } catch (Exception e) {
            showAlert("Erro ao adicionar: " + e.getMessage());
        }
    }

    private void removerCurso() {
        String codigo = codigoField.getText().trim();

        if (codigo.isEmpty()) {
            showAlert("Informe o código do curso.");
            return;
        }

        if (controller.remover(codigo)) {
            showAlert("Curso removido com sucesso.");
            atualizarLista();
            limparCampos();
        } else {
            showAlert("Curso não encontrado.");
        }
    }

    private void atualizarCurso() {
        try {
            Curso novo = lerCursoDosCampos();
            if (controller.atualizar(novo.getCodigo(), novo)) {
                showAlert("Curso atualizado.");
                atualizarLista();
                limparCampos();
            } else {
                showAlert("Curso não encontrado.");
            }
        } catch (Exception e) {
            showAlert("Erro: " + e.getMessage());
        }
    }

    private void buscarCurso() {
        String codigo = codigoField.getText().trim();
        if (codigo.isEmpty()) {
            showAlert("Informe o código do curso.");
            return;
        }

        Curso c = controller.buscarPorCodigo(codigo);
        if (c != null) {
            nomeField.setText(c.getNome());
            areaField.setText(c.getArea());
        } else {
            showAlert("Curso não encontrado.");
        }
    }

    private Curso lerCursoDosCampos() {
        String codigo = codigoField.getText().trim();
        String nome = nomeField.getText().trim();
        String area = areaField.getText().trim();

        if (codigo.isEmpty() || nome.isEmpty() || area.isEmpty()) {
            throw new IllegalArgumentException("Todos os campos devem ser preenchidos.");
        }

        return new Curso(codigo, nome, area);
    }

    private void limparCampos() {
        codigoField.clear();
        nomeField.clear();
        areaField.clear();
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
