package sample.waterparkrides;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.Controller;
import sample.DbManager;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * Created by Parnali on 19-01-2018.
 */
public class WaterparkRidesController implements Initializable
{
  //  public Alert alert;

    DbManager dbsql;
    CallableStatement st;
    String query;
    TableView tv = new TableView();

    public void initialize(URL location, ResourceBundle resources)
    {
        dbsql = new DbManager();
        btnNewWaterparkRidesClick(new ActionEvent());
        clickItem();
    }

    @FXML
    private AnchorPane ancher1;
    @FXML
    private TextArea txtridename;

    @FXML
    private TextArea txtrideid;

    @FXML
    private Button btnridenew;

    @FXML
    private Button btnridedelete;

    @FXML
    private Button btnridesave;

    @FXML
    private Button btnrideupdate;

    @FXML
    void btnDeleteWaterparkRidesClick(ActionEvent event)
    {
        try
        {

            dbsql.connectMySql();
            st=dbsql.conn.prepareCall("{call spDeleteWaterparkRides(?)}");
            st.setInt(1,Integer.parseInt(txtrideid.getText()));

            st.execute();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Waterpark Rides Record Deleted");
            alert.showAndWait();
            btnNewWaterparkRidesClick(new ActionEvent());


        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("\n Waterpark Rides connected with another field. Cannot be Deleted ");
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

    @FXML
    void btnNewWaterparkRidesClick(ActionEvent event)
    {

        int RidesId =  dbsql.getRecordNumber("WaterparkRides","RidesId");
        txtrideid.setText(RidesId+"");

        txtridename.setText("");
        //ancher1.getChildren().add(dbsql.buildData("SELECT *FROM viewwaterparkrides"));
        tv=dbsql.buildData("SELECT *FROM viewwaterparkrides");
        ancher1.getChildren().clear();
        ancher1.getChildren().add(tv);
        TableColumn c;
        c = (TableColumn) tv.getColumns().get(0);
        c.setPrefWidth(150);
        c.setStyle("-fx-alignment:CENTER;");
        c = (TableColumn) tv.getColumns().get(1);
        c.setPrefWidth(210);
        c.setStyle("-fx-alignment:CENTER;");

        clickItem();
    }



    @FXML
    void btnSaveWaterparkRidesClick(ActionEvent event)
    {
        try
        {

            System.out.println("\n Waterparkrides New Button Clicked");
            dbsql.connectMySql();
            st=dbsql.conn.prepareCall("{call spInsertWaterparkRides(?,?)}");

            st.setInt(1,Integer.parseInt(txtrideid.getText()));
            st.setString(2,txtridename.getText());

            st.execute();
            System.out.println("Waterpark rides Record Inserted");

            btnNewWaterparkRidesClick(new ActionEvent() );


        }
        catch (Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Waterpark rides Record Inserted");
        alert.showAndWait();
    }

    @FXML
    void btnUpdateWaterparkRidesClick(ActionEvent event) {
     try
     {
        System.out.println("\n Waterparkrides New Button Clicked");
        dbsql.connectMySql();
        st=dbsql.conn.prepareCall("{call spUpdateWaterparkRides(?,?)}");

        st.setInt(1,Integer.parseInt(txtrideid.getText()));
        st.setString(2,txtridename.getText());

        st.execute();
        System.out.println("Waterpark rides Record Updated");

        btnNewWaterparkRidesClick(new ActionEvent() );


    }
        catch (Exception ex)
    {
      Alert  alert = new Alert(Alert.AlertType.INFORMATION);
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
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Waterpark rides Record Updated");
        alert.showAndWait();
    }

   @FXML
    void clickItem()
    {
        System.out.println("clicked");


        tv.setOnMouseClicked(new EventHandler<MouseEvent>()
        {

            @Override
            public void handle(MouseEvent event)
            {

                 System.out.println(tv.getItems().get(tv.getSelectionModel().getSelectedIndex()));
                Object selecteditems = tv.getSelectionModel().getSelectedItems().get(0);
                txtrideid.setText(selecteditems.toString().split(",")[0].substring(1));
               String a = (selecteditems.toString().split(",")[1].substring(1));
               String b = a.replace(']',' ');
                txtridename.setText(b);
            }
        });

    }

}
