package sample.cleaning;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import sample.Controller;
import sample.DbManager;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.sql.Date;

/**
 * Created by Parnali on 19-01-2018.
 */
public class CleaningController implements Initializable
{

    Alert alert;

    @FXML
    private AnchorPane ancher1;
    @FXML

    private ComboBox<?> combocleaningrideid;

    @FXML
    private ComboBox<?> combocleaningstaffid;

    @FXML
    private DatePicker datecleaning;

    @FXML
    private TextArea txtcleaningid;
    @FXML
    //private AnchorPane ancher1;


    DbManager dbsql;
    CallableStatement st ;
    String query;
    LocalDate d;

    //DateTimeFormatter dtformatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
    //DateFormat format = new SimpleDateFormat("yyyy-mm-dd");


    TableView tv = new TableView();






    @Override
    public void initialize(URL location, ResourceBundle resources)

    {
        dbsql = new DbManager();

       query = "SELECT S.StaffId,S.FullName FROM Staff S WHERE S.PostType='Cleaner' OR S.PostType='Sweeper'";
        dbsql.loadDDL(query,combocleaningstaffid);

        query="SELECT RidesId,RideName from Waterparkrides";
        dbsql.loadDDL(query,combocleaningrideid);

        query="SELECT StaffId,FullName from Staff";
        dbsql.loadDDL(query,combocleaningstaffid);


        btnNewCleanClick(new ActionEvent());
        clickItem();

    }
    @FXML
    private Button btncleannew;

    @FXML
    private Button btncleansave;

    @FXML
    private Button btncleanupdate;

    @FXML
    private Button btncleandelete;




    @FXML
    void btnDeleteCleanClick(ActionEvent event)
    {
        try
        {

            System.out.println("\n Clean Delete Button Clicked");
            dbsql.connectMySql();
            st=dbsql.conn.prepareCall("{call spDeleteCleaning(?)}");

            st.setInt(1,Integer.parseInt(txtcleaningid.getText()));


            st.execute();
            System.out.println("Cleaning Record Deleted");
            btnNewCleanClick(new ActionEvent() );


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Cleaning Record Deleted");
            alert.showAndWait();


            btnNewCleanClick(new ActionEvent() );


        }
        catch (Exception ex)
        {
            System.out.println("Exception = " + ex);

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Record Exits in field. Cannot Delete record");
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
                alert.setContentText(e.toString());
                alert.showAndWait();
            }
        }


        }

    @FXML
    void btnNewCleanClick(ActionEvent event)
    {
        int CleanId =  dbsql.getRecordNumber("Cleaning","CleanId");
        txtcleaningid.setText(CleanId+"");

            System.out.println(txtcleaningid.getText());
        datecleaning.setValue(null);
        combocleaningstaffid.setPromptText("Select Staff Name");
        combocleaningrideid.setPromptText("Select Ride Name");


        //ancher1.getChildren().add(dbsql.buildData("SELECT *FROM viewcleaning"));


        tv=dbsql.buildData("SELECT *FROM viewcleaning");
        ancher1.getChildren().clear();
        ancher1.getChildren().add(tv);
        TableColumn c;
        c = (TableColumn) tv.getColumns().get(0);
        c.setPrefWidth(100);
        c.setStyle("-fx-alignment:CENTER;");
        c = (TableColumn) tv.getColumns().get(1);
        c.setPrefWidth(100);
        c.setStyle("-fx-alignment:CENTER;");
        c = (TableColumn) tv.getColumns().get(2);
        c.setPrefWidth(150);
        c.setStyle("-fx-alignment:CENTER;");
        c = (TableColumn) tv.getColumns().get(3);
        c.setPrefWidth(150);
        c.setStyle("-fx-alignment:CENTER;");
        clickItem();
    }

    @FXML
    void btnSaveCleanClick(ActionEvent event)
    {
        try
        {

            System.out.println("\n Cleaning New Button Clicked");
            dbsql.connectMySql();
            st=dbsql.conn.prepareCall("{call spInsertCleaning(?,?,?,?)}");

            System.out.println("\n ID " + txtcleaningid.getText());
            st.setInt(1,Integer.parseInt(txtcleaningid.getText()));

            d=datecleaning.getValue();
            st.setDate(2,Date.valueOf(d) );
            st.setString(3, combocleaningstaffid.getValue().toString());
            st.setString(4,combocleaningrideid.getValue().toString());

            st.execute();
            System.out.println("Cleaning Record Inserted");
            btnNewCleanClick(new ActionEvent() );

            tv=dbsql.buildData("SELECT *FROM viewCleaning");
            ancher1.getChildren().add(tv);

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Cleaning Record Inserted");
            alert.showAndWait();

            btnNewCleanClick(new ActionEvent() );


        }
        catch (Exception ex)
        {
            alert = new Alert(Alert.AlertType.INFORMATION);
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

    }

    @FXML
    void btnUpdateCleanClick(ActionEvent event)
    {
        try
        {


            dbsql.connectMySql();
            st=dbsql.conn.prepareCall("{call spUpdateCleaning(?,?,?,?)}");


            st.setInt(1,Integer.parseInt(txtcleaningid.getText()));

            d=datecleaning.getValue();
            st.setDate(2,Date.valueOf(d) );

            st.setString(3, combocleaningstaffid.getValue().toString());
            st.setString(4,combocleaningrideid.getValue().toString());

            st.execute();

            tv=dbsql.buildData("SELECT *FROM viewCleaning");

            ancher1.getChildren().add(tv);
            btnNewCleanClick(new ActionEvent() );

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Cleaning Record Updated");
            alert.showAndWait();

            btnNewCleanClick(new ActionEvent() );


        }
        catch (Exception ex)
        {

            alert = new Alert(Alert.AlertType.INFORMATION);
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



    }
    @FXML
    void clickItem()
    {

        tv.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                System.out.println(tv.getItems().get(tv.getSelectionModel().getSelectedIndex()));
                Object selecteditems = tv.getSelectionModel().getSelectedItems().get(0);
                txtcleaningid.setText(selecteditems.toString().split(",")[0].substring(1));
                String e = (selecteditems.toString().split(",")[1].substring(1));
                try
                {
                    d = LocalDate.parse(e);
                    System.out.println(d);
                }
                catch (Exception e1)
                {
                    System.out.println("\n Exception is : "+e1);
                }
                datecleaning.setValue(d);
                combocleaningstaffid.setPromptText(selecteditems.toString().split(",")[2].substring(1));


                String p = (selecteditems.toString().split(",")[3].substring(1));
                char c = ' ';
                String d = p.replace(']',c);
                combocleaningrideid.setPromptText(d);

                System.out.println("p = " + d);

            }
        });
    }
}
