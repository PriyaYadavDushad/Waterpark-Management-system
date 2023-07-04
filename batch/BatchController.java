package sample.batch;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import sample.Controller;

import sample.DbManager;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import javax.lang.model.type.NullType;

import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;

/**
 * Created by Parnali on 19-01-2019.
 */

public class BatchController implements Initializable

{
    Alert alert;

    DbManager dbsql;
    CallableStatement st ;
    String query;
    TableView tv = new TableView();
    Time t;

    public void initialize(URL location, ResourceBundle resources)
    {
        dbsql = new DbManager();
        btnNewBatchClick(new ActionEvent());

        query = "SELECT TicketId,TicketColorCode FROM Ticket";
        dbsql.loadDDL(query,combobatchticket);

        clickItem();
    }

    @FXML
    private TableView<?> tbviewBatch;

    @FXML
    private RadioButton radiobatchfood;

    @FXML
    private RadioButton radiobatchfoodno;

    @FXML
    private TextArea txtbatchid;

    @FXML
    private TextArea txtbatchtype;

    @FXML
    private TextArea txtbatchtime;

    @FXML
    private ComboBox<?> combobatchticket;

    @FXML
    private TextArea txtbatchfees;

    @FXML
    private Button btnbatchnew;

    @FXML
    private Button btnbatchdelete;

    @FXML
    private Button btnbatchsave;

    @FXML
    private Button btnbatchupdate;

    @FXML
    private AnchorPane ancher1;

    @FXML
    void btnDeleteBatchClick(ActionEvent event) {
        try
        {

            System.out.println("\n Batch Delete Button Clicked");
            dbsql.connectMySql();
            st=dbsql.conn.prepareCall("{call spDeleteBatch(?)}");

            st.setInt(1,Integer.parseInt(txtbatchid.getText()));


            st.execute();
            System.out.println("Batch Record Deleted");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Batch Record Deleted");
            alert.showAndWait();


            btnNewBatchClick(new ActionEvent() );


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

    void btnNewBatchClick(ActionEvent event)
    {
        int BatchId =  dbsql.getRecordNumber("Batch","BatchId");
        txtbatchid.setText((BatchId)+"");

        txtbatchfees.setText("");
        txtbatchtime.setText("");
        txtbatchtype.setText("");
        combobatchticket.setPromptText("Select Ticket color code");

        tv=dbsql.buildData("SELECT *FROM viewBatch");
        ancher1.getChildren().add(tv);
        TableColumn c;
        c = (TableColumn) tv.getColumns().get(0);
        c.setPrefWidth(100);
        c.setStyle("-fx-alignment:CENTER;");
        c = (TableColumn) tv.getColumns().get(1);
        c.setPrefWidth(100);
        c.setStyle("-fx-alignment:CENTER;");
        c = (TableColumn) tv.getColumns().get(2);
        c.setPrefWidth(100);
        c.setStyle("-fx-alignment:CENTER;");
        c = (TableColumn) tv.getColumns().get(3);
        c.setPrefWidth(100);
        c.setStyle("-fx-alignment:CENTER;");
        c = (TableColumn) tv.getColumns().get(4);
        c.setPrefWidth(100);
        c.setStyle("-fx-alignment:CENTER;");
        c = (TableColumn) tv.getColumns().get(5);
        c.setPrefWidth(100);
        c.setStyle("-fx-alignment:CENTER;");

        clickItem();
        //dbsql.buildData(tbviewBatch,"SELECT *FROM Batch");
    }

    @FXML
    void btnSaveBatchClick(ActionEvent event)
    {
        try
        {
            System.out.println("\n Batch Save Button Clicked");
            dbsql.connectMySql();
            st=dbsql.conn.prepareCall("{call spInsertBatch(?,?,?,?,?,?)}");


            int id = Integer.parseInt(txtbatchid.getText());
            st.setInt(1,id);
            st.setString(2,txtbatchtype.getText());
            st.setString(3,txtbatchtime.getText());
            st.setString(4,combobatchticket.getValue()+"");
            st.setInt(5,Integer.parseInt(txtbatchfees.getText()));
            String b;
            if(radiobatchfood.isSelected())
            {
                b="Yes";
            }
            else
            {
                b="No";
            }
            st.setString(6,b);


            st.execute();
            btnNewBatchClick(new ActionEvent() );
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

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Batch Record Inserted");
        alert.showAndWait();
    }

    @FXML
    void btnUpdateBatchClick(ActionEvent event) {
        try
        {

            System.out.println("\n Batch Update Button Clicked");
            dbsql.connectMySql();
            st=dbsql.conn.prepareCall("{call spUpdateBatch(?,?,?,?,?,?)}");

            st.setInt(1,Integer.parseInt(txtbatchid.getText()));
            st.setString(2,txtbatchtype.getText());
            st.setString(3,txtbatchtime.getText());
            st.setObject(4,combobatchticket.getPromptText());
            st.setInt(5,Integer.parseInt(txtbatchfees.getText()));
            String b1;
            if(radiobatchfood.isSelected())
            {
                b1="Yes";
                st.setString(6,"Yes");

            }
            else
            {
                b1="No";
                st.setString(6,"No");

            }


            st.execute();
            System.out.println("Batch Record Updated");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Batch Record Updated");
            alert.showAndWait();


            btnNewBatchClick(new ActionEvent() );


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
    void radiobatch(ActionEvent event) {

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
              txtbatchid.setText(selecteditems.toString().split(",")[0].substring(1));
              System.out.println("\n batch Id = " + txtbatchid.getText());
              txtbatchtype.setText(selecteditems.toString().split(",")[1].substring(1));
              txtbatchtime.setText(selecteditems.toString().split(",")[2].substring(1));

              txtbatchfees.setText(selecteditems.toString().split(",")[4].substring(1));
              combobatchticket.setPromptText(selecteditems.toString().split(",")[3].substring(1));
              String p = (selecteditems.toString().split(",")[5].substring(1));
              char c = ' ';
              String d = p.replace(']',c);

              System.out.println("p = " + d);

               if(d.equalsIgnoreCase("YES "))
               {

                   radiobatchfood.setSelected(true);
               }
               else
               {
                   radiobatchfoodno.setSelected(true);
               }
           }
       });
    }

    public void radiobatchno(ActionEvent event)
    {
    }

}



