package view;

import controller.ProfessorController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Professor;

public class ProfessorView extends Application {

    private ProfessorController controller = new ProfessorController();
    private ObservableList<String> professorList = FXCollections.observableArrayList();
    private ListView<String> listView = new ListView<>(professorList);

    private TextField nomeField = new TextField();
    private TextField cpfField = new TextField();
    private TextField areaField = new TextField();
    private TextField pontuacaoField = new TextField();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Gerenciador de Professores");

        nomeField.setPromptText("Nome");
        cpfField.setPromptText("CPF");
        areaField.setPromptText("Área");
        pontuacaoField.setPromptText("Pontuação");

        Button btnAdicionar = new Button("Adicionar");
        Button btnBuscar = new Button("Buscar por CPF");
        Button btnRemover = new Button("Remover");
        Button btnAtualizar = new Button("Atualizar");

        btnAdicionar.setOnAction(e -> adicionarProfessor());
        btnBuscar.setOnAction(e -> buscarProfessor());
        btnRemover.setOnAction(e -> removerProfessor());
        btnAtualizar.setOnAction(e -> atualizarProfessor());

        HBox inputs = new HBox(10, nomeField, cpfField, areaField, pontuacaoField);
        HBox botoes = new HBox(10, btnAdicionar, btnBuscar, btnAtualizar, btnRemover);
        VBox layout = new VBox(10, inputs, botoes, listView);
        layout.setPadding(new Insets(10));

        atualizarLista();

        Scene scene = new Scene(layout, 800, 400);
        stage.setScene(scene);
        stage.show();
    }

    private void atualizarLista() {
        professorList.clear();
        for (int i = 0; i < controller.listar().tamanho(); i++) {
            Professor p = controller.listar().get(i);
            professorList
                    .add(p.getNome() + " | " + p.getCpf() + " | " + p.getArea() + " | Pontuação: " + p.getPontuacao());
        }
    }

    private void adicionarProfessor() {
        try {
            String cpf = cpfField.getText();
            String nome = nomeField.getText();
            String area = areaField.getText();
            int pontuacao = Integer.parseInt(pontuacaoField.getText());

            Professor p = new Professor(cpf, nome, area, pontuacao);
            controller.adicionar(p);
            atualizarLista();
            limparCampos();
        } catch (NumberFormatException e) {
            showAlert("Pontuação deve ser um número inteiro.");
        }
    }

    private void buscarProfessor() {
        String cpf = cpfField.getText();
        Professor p = controller.buscarPorCpf(cpf);
        if (p != null) {
            nomeField.setText(p.getNome());
            areaField.setText(p.getArea());
            pontuacaoField.setText(String.valueOf(p.getPontuacao()));
        } else {
            showAlert("Professor não encontrado.");
        }
    }

    private void removerProfessor() {
        String cpf = cpfField.getText();
        if (controller.remover(cpf)) {
            showAlert("Professor removido com sucesso.");
            atualizarLista();
            limparCampos();
        } else {
            showAlert("Professor não encontrado.");
        }
    }

    private void atualizarProfessor() {
        try {
            String cpf = cpfField.getText();
            String nome = nomeField.getText();
            String area = areaField.getText();
            int pontuacao = Integer.parseInt(pontuacaoField.getText());

            Professor novo = new Professor(cpf, nome, area, pontuacao);
            if (controller.atualizar(cpf, novo)) {
                showAlert("Professor atualizado com sucesso.");
                atualizarLista();
                limparCampos();
            } else {
                showAlert("Professor não encontrado.");
            }
        } catch (NumberFormatException e) {
            showAlert("Pontuação deve ser um número inteiro.");
        }
    }

    private void limparCampos() {
        nomeField.clear();
        cpfField.clear();
        areaField.clear();
        pontuacaoField.clear();
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
