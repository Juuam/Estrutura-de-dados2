package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MenuPrincipalView {

    public Pane getTela() {
        Button btnCurso = new Button("Gerenciar Cursos");
        Button btnProfessor = new Button("Gerenciar Professores");
        Button btnDisciplina = new Button("Gerenciar Disciplinas");
        Button btnInscricao = new Button("Gerenciar Inscrições");
        Button btnConsultaInscritos = new Button("Consulta de Inscritos por Disciplina");
        Button btnConsultaDisciplinasAtivas = new Button("Disciplinas Ativas por Curso");

        // Abre cada view correspondente
        btnCurso.setOnAction(e -> new view.CursoView().start(new Stage()));
        btnProfessor.setOnAction(e -> new view.ProfessorView().start(new Stage()));
        btnDisciplina.setOnAction(e -> new view.DisciplinaView().start(new Stage()));
        btnInscricao.setOnAction(e -> new view.InscricaoView().start(new Stage()));
        btnConsultaInscritos.setOnAction(e -> new view.ConsultaView().mostrar());
        btnConsultaDisciplinasAtivas.setOnAction(e -> new view.ConsultaDisciplinasAtivasView().mostrar());

        VBox layout = new VBox(10,
                btnCurso,
                btnProfessor,
                btnDisciplina,
                btnInscricao,
                btnConsultaInscritos,
                btnConsultaDisciplinasAtivas);
        layout.setPadding(new Insets(20));

        return layout;
    }
}
