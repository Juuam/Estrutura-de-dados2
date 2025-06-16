package view;

import controller.InscricaoController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Inscricao;

public class InscricaoView extends Application {

    private InscricaoController controller = new InscricaoController();
    private ObservableList<String> inscricoesList = FXCollections.observableArrayList();
    private ListView<String> listView = new ListView<>(inscricoesList);

    private TextField cpfField = new TextField();
    private TextField codDisciplinaField = new TextField();
    private TextField codProcessoField = new TextField();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Gerenciador de Inscrições");

        cpfField.setPromptText("CPF do Professor");
        codDisciplinaField.setPromptText("Código da Disciplina");
        codProcessoField.setPromptText("Código do Processo");

        Button btnAdicionar = new Button("Adicionar");
        Button btnRemover = new Button("Remover");
        Button btnBuscarPorDisciplina = new Button("Buscar por Disciplina");
        Button btnListarTodas = new Button("Listar Todas");

        btnAdicionar.setOnAction(e -> adicionarInscricao());
        btnRemover.setOnAction(e -> removerInscricao());
        btnBuscarPorDisciplina.setOnAction(e -> buscarPorDisciplina());
        btnListarTodas.setOnAction(e -> atualizarLista());

        HBox inputs = new HBox(10, cpfField, codDisciplinaField, codProcessoField);
        HBox botoes = new HBox(10, btnAdicionar, btnRemover, btnBuscarPorDisciplina, btnListarTodas);
        VBox layout = new VBox(10, inputs, botoes, listView);
        layout.setPadding(new Insets(10));

        atualizarLista();

        Scene scene = new Scene(layout, 800, 400);
        stage.setScene(scene);
        stage.show();
    }

    private void atualizarLista() {
        inscricoesList.clear();
        var inscricoes = controller.listar();
        for (int i = 0; i < inscricoes.tamanho(); i++) {
            Inscricao ins = inscricoes.get(i);
            inscricoesList.add("CPF: " + ins.getCpfProfessor()
                    + " | Disciplina: " + ins.getCodigoDisciplina()
                    + " | Processo: " + ins.getCodigoProcesso());
        }
    }

    private void adicionarInscricao() {
        String cpf = cpfField.getText().trim();
        String codDisciplina = codDisciplinaField.getText().trim();
        String codProcesso = codProcessoField.getText().trim();

        if (cpf.isEmpty() || codDisciplina.isEmpty() || codProcesso.isEmpty()) {
            showAlert("Preencha todos os campos.");
            return;
        }

        Inscricao i = new Inscricao(cpf, codDisciplina, codProcesso);
        controller.adicionar(i);
        controller.salvarAlteracoes();
        atualizarLista();
        limparCampos();
    }

    private void removerInscricao() {
        String cpf = cpfField.getText().trim();
        String codDisciplina = codDisciplinaField.getText().trim();

        if (cpf.isEmpty() || codDisciplina.isEmpty()) {
            showAlert("Informe CPF e código da disciplina para remover.");
            return;
        }

        if (controller.remover(cpf, codDisciplina)) {
            showAlert("Inscrição removida com sucesso.");
            atualizarLista();
            limparCampos();
        } else {
            showAlert("Inscrição não encontrada.");
        }
    }

    private void buscarPorDisciplina() {
        String codDisciplina = codDisciplinaField.getText().trim();
        if (codDisciplina.isEmpty()) {
            showAlert("Digite o código da disciplina.");
            return;
        }

        var resultado = controller.buscarPorDisciplina(codDisciplina);
        inscricoesList.clear();
        for (int i = 0; i < resultado.tamanho(); i++) {
            Inscricao ins = resultado.get(i);
            inscricoesList.add("CPF: " + ins.getCpfProfessor()
                    + " | Disciplina: " + ins.getCodigoDisciplina()
                    + " | Processo: " + ins.getCodigoProcesso());
        }
    }

    private void limparCampos() {
        cpfField.clear();
        codDisciplinaField.clear();
        codProcessoField.clear();
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
