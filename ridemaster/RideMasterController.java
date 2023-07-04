package sample.ridemaster;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.Controller;
import sample.DbManager;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
/**
 * Created by Parnali on 19-01-2018.
 */
public class RideMasterController implements Initializable
{
    Alert alert;
    DbManager dbsql;
    CallableStatement st ;
    String query;
    TableView tv = new TableView();


    @FXML
    private AnchorPane ancher1;
    @FXML
    private TextArea txtridemasterid;

    @FXML
    private ComboBox<?> comboridemrideid;

    @FXML
    private ComboBox<?> comboridembatchid;


    public void initialize(URL location, ResourceBundle resources) {

        dbsql = new DbManager();
        query = "SELECT BatchId,BatchType FROM Batch ";
        dbsql.loadDDL(query,comboridembatchid);

        query = "SELECT RidesId,RideName FROM waterparkrides ";
        dbsql.loadDDL(query,comboridemrideid);


        btnNewRideMasterClick(new ActionEvent());
        clickItem();
    }

    @FXML
    private Button btnridemnew;

    @FXML
    private Button btnridemdelete;

    @FXML
    private Button btnridemupdate;

    @FXML
    private Button btnridemsave;

    @FXML
    void btnDeleteRideMasterClick(ActionEvent event)
    {
        try
        {

            dbsql.connectMySql();
            st=dbsql.conn.prepareCall("{call spDeleteRideMaster(?)}");
            st.setInt(1,Integer.parseInt(txtridemasterid.getText()));

            st.execute();
            btnNewRideMasterClick(new ActionEvent());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Ride Master Record Deleted");
            alert.showAndWait();
            btnNewRideMasterClick(new ActionEvent());


        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("\n Ride Master connected with another field. Cannot be Deleted ");
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
    void btnNewRideMasterClick(ActionEvent event) {
        int RideMasterId =  dbsql.getRecordNumber("ridemaster","RideMasterId");
        txtridemasterid.setText((RideMasterId)+"");

        comboridembatchid.setPromptText("select batch type");
        comboridemrideid.setPromptText("select ride name");

        //ancher1.getChildren().add(dbsql.buildData("SELECT *FROM viewridemaster"));

        tv=dbsql.buildData("SELECT *FROM viewridemaster");
        ancher1.getChildren().clear();
        ancher1.getChildren().add(tv);
        TableColumn c;
        c = (TableColumn) tv.getColumns().get(0);
        c.setPrefWidth(100);
        c.setStyle("-fx-alignment:CENTER;");
        c = (TableColumn) tv.getColumns().get(1);
        c.setPrefWidth(150);
        c.setStyle("-fx-alignment:CENTER;");
        c = (TableColumn) tv.getColumns().get(2);
        c.setPrefWidth(100);
        c.setStyle("-fx-alignment:CENTER;");


        clickItem();

    }

    @FXML
    void btnSaveRideMasterClick(ActionEvent event) {

        try
        {

            System.out.println("\n Ride Master New Button Clicked");
            dbsql.connectMySql();
            st=dbsql.conn.prepareCall("{call spInsertRideMaster(?,?,?)}");

            st.setInt(1,Integer.parseInt(txtridemasterid.getText()));
            st.setString(2,comboridemrideid.getValue()+"");
            st.setString(3,comboridembatchid.getValue()+"");



            st.execute();
            System.out.println("Ride Master Record Inserted");


            btnNewRideMasterClick(new ActionEvent() );


        }
        catch (Exception ex)
        {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(ex.toString());
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
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Ride Master Record Inserted");
        alert.showAndWait();

        btnNewRideMasterClick(new ActionEvent());

    }

    @FXML
    void clickItem()
    {
        tv.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                System.out.println("clicked");
                // System.out.println(tv.getItems().get(tv.getSelectionModel().getSelectedIndex()));
                Object selecteditems = tv.getSelectionModel().getSelectedItems().get(0);
                txtridemasterid.setText(selecteditems.toString().split(",")[0].substring(1));
                comboridembatchid.setPromptText(selecteditems.toString().split(",")[2].substring(1));
                comboridemrideid.setPromptText(selecteditems.toString().split(",")[1].substring(1));

            }
        });
    }

    @FXML
    void btnUpdateRideMasterClick(ActionEvent event) {

        try
        {

            System.out.println("\n Ride Master New Button Clicked");
            dbsql.connectMySql();
            st=dbsql.conn.prepareCall("{call spUpdateRideMaster(?,?,?)}");

            st.setInt(1,Integer.parseInt(txtridemasterid.getText()));
            System.out.println("ID = "+txtridemasterid.getText());
            st.setString(2,comboridemrideid.getValue()+"");
            st.setString(3,comboridembatchid.getValue()+"");



            st.execute();
            System.out.println("Ride Master Record Updated");


            btnNewRideMasterClick(new ActionEvent() );


        }
        catch (Exception ex)
        {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(ex.toString());
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
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Ride Master Record Updated");
        alert.showAndWait();

    }
}
