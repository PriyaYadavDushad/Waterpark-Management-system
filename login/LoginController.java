package sample.login;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import sample.Controller;
import sample.DbManager;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * Created by Parnali on 19-01-2018.
 */
public class LoginController implements Initializable
{
    DbManager dbsql;
    Alert alert;
    public void initialize(URL location, ResourceBundle resources)

    {
        dbsql = new DbManager();

    }
    final void timeline()
    {
        Timeline t = new Timeline();
    }
    @FXML
    private ImageView imgpass;

    @FXML
    private JFXPasswordField fieldPwd;

    @FXML
    private ImageView imglog;

    @FXML
    private ButtonBar btnlog;

    @FXML
    private ImageView imglogin;


    @FXML
    private JFXTextField fieldUsername;

    @FXML
    void btnlogclick(MouseEvent event) {
        try {

            int staffid=-1;
            String query="SELECT *FROM viewStaff";
            dbsql.connectMySql();
            Statement st = dbsql.conn.createStatement();

            System.out.println( "\n\n" + fieldUsername.getText() + "\t" + fieldPwd.getText());
            ResultSet r = st.executeQuery(query);
            while (r.next()) {

                if(r.getString(12).equals(fieldUsername.getText()) && r.getString(13).equals(fieldPwd.getText()))
                {
                    staffid=r.getInt(1);
                }
            }

            if(staffid==-1) {
                //t = new IndexController(false);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Invalid Username or Password ");
                alert.showAndWait();
            }
            else {
                //  t=new IndexController(true);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(" Login Successfull");
                alert.showAndWait();

            }
        }

        catch (Exception ex)
        {
            ex.printStackTrace();
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(ex.toString());
            alert.showAndWait();
        }

    }

}
