package sample.post;

import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.Controller;
import sample.DbManager;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * Created by Parnali on 19-01-2018.
 */

public class PostController implements Initializable{

    Alert alert;
    @FXML
    private AnchorPane ancher1;
    @FXML
    private TextArea txtpostid;

    @FXML
    private TextArea txtposttype;

    @FXML
    private TextArea txtpostsalary;

    @FXML
    private Button btnpostnew;

    @FXML
    private Button btnpostdelete;

    @FXML
    private Button btnpostupdate;

    @FXML
    private Button btnpostsave;



    DbManager dbsql;
    CallableStatement st;
    String query;
    TableView tv = new TableView();

    @Override


    public void initialize(URL location, ResourceBundle resources)

    {
        dbsql = new DbManager();
        btnNewPostClick(new ActionEvent());
        clickItem();
    }


    public void btnNewPostClick(ActionEvent event)
    {
           int postid =  dbsql.getRecordNumber("Post","postid");
           txtpostid.setText(postid+"");

           txtpostsalary.setText("");
           txtposttype.setText("");

        tv=dbsql.buildData("SELECT *FROM viewpost");
        ancher1.getChildren().add(tv);

        TableColumn c;
        c = (TableColumn) tv.getColumns().get(0);
        c.setPrefWidth(100);
        c.setStyle("-fx-alignment:CENTER;");


        c = (TableColumn) tv.getColumns().get(1);
        c.setPrefWidth(200);
        c.setStyle("-fx-alignment:LEFT;");

        c = (TableColumn) tv.getColumns().get(2);
        c.setPrefWidth(100);
        c.setStyle("-fx-alignment:CENTER;");


        clickItem();

       // ancher1.getChildren().add(dbsql.buildData("SELECT *FROM viewPost"));
    }

    @FXML
    void btnSavePostClick(ActionEvent event) {
        try
        {

            System.out.println("\n Post New Button Clicked");
            dbsql.connectMySql();
            st=dbsql.conn.prepareCall("{call spInsertPost(?,?,?)}");

            st.setInt(1,Integer.parseInt(txtpostid.getText()));
            st.setString(2,txtposttype.getText());
            st.setInt(3,Integer.parseInt(txtpostsalary.getText()));

            st.execute();


            btnNewPostClick(new ActionEvent() );
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Post Record Inserted");
            alert.showAndWait();

        }
        catch (Exception ex)
        {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(ex.toString());
            alert.showAndWait();
        }
        finally {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    void clickItem()
    {

        tv.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                // System.out.println(tv.getItems().get(tv.getSelectionModel().getSelectedIndex()));
                Object selecteditems = tv.getSelectionModel().getSelectedItems().get(0);
                txtpostid.setText(selecteditems.toString().split(",")[0].substring(1));
                txtposttype.setText(selecteditems.toString().split(",")[1].substring(1));
                String p = (selecteditems.toString().split(",")[2].substring(1));
                char c = ' ';
                String d = p.replace(']',c);
                txtpostsalary.setText(d);


            }
        });
    }
    @FXML
    void btnUpdatePostClick(ActionEvent event) {
        try
        {

            System.out.println("\n Post New Button Clicked");
            dbsql.connectMySql();
            st=dbsql.conn.prepareCall("{call spUpdatePost(?,?,?)}");

            st.setInt(1,Integer.parseInt(txtpostid.getText()));
            st.setString(2,txtposttype.getText());
            st.setInt(3,Integer.parseInt(txtpostsalary.getText()));

            st.execute();


            btnNewPostClick(new ActionEvent() );
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Post Record Updated");
            alert.showAndWait();

        }
        catch (Exception ex)
        {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(ex.toString());
            alert.showAndWait();
        }
        finally {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void btnDeletePostClick(ActionEvent event)
    {
        try
        {

            dbsql.connectMySql();
            st=dbsql.conn.prepareCall("{call spDeletePost(?)}");
            st.setInt(1,Integer.parseInt(txtpostid.getText()));

            st.execute();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Post Record Deleted");
            alert.showAndWait();
            btnNewPostClick(new ActionEvent());


        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("\n Post connected with another field. Cannot be Deleted ");
            alert.showAndWait();

        }
        finally
        {
            try
            {
                st.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText(" "+e);
                alert.showAndWait();
            }
        }

    }

}
