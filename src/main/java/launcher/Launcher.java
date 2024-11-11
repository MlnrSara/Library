package launcher;

import javafx.application.Application;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Launcher extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ComponentFactory.getInstance(false, primaryStage);
    }
}
