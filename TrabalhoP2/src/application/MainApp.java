package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.MenuPrincipalView;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        MenuPrincipalView menu = new MenuPrincipalView();
        Scene cena = new Scene(menu.getTela(), 600, 400);

        primaryStage.setTitle("Sistema de Seleção de Docentes");
        primaryStage.setScene(cena);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
