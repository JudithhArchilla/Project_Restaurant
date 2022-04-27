package main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import resources.ReadProperties;

public class Main extends Application {

    /**
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        ReadProperties readP = new ReadProperties();
        Parent root = FXMLLoader.load(getClass().getResource(readP.getrView() + "principal.fxml"));
        primaryStage.setTitle("Welcome to Oh Food");

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);


        primaryStage.show();
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
