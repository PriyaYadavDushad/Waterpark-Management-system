package sample.staff;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Parnali on 29-01-2018.
 */
public class staffmain extends Application
{
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/staff/Staff.fxml"));
        primaryStage.setTitle("Staff");
        primaryStage.setScene(new Scene(root, 1500, 700));
        primaryStage.show();
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}
