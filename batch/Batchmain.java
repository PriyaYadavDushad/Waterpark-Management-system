package sample.batch;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Parnali on 27-01-2018.
 */
public class Batchmain extends Application {

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/batch/Batch.fxml"));
        primaryStage.setTitle("Batch");
        primaryStage.setScene(new Scene(root, 1400, 700));
        primaryStage.show();
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}
