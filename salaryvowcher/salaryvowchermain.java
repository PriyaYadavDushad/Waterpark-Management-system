package sample.salaryvowcher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Parnali on 29-01-2018.
 */
public class salaryvowchermain extends Application {
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/salaryvowcher/SalaryVowcher.fxml"));
        primaryStage.setTitle("Salary Vowcher");
        primaryStage.setScene(new Scene(root, 1200, 600));
        primaryStage.show();
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}
