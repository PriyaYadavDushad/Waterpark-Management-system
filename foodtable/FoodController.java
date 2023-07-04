package sample.foodtable;
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
import java.util.ResourceBundle;
import javafx.fxml.Initializable;


/**
 * Created by Parnali on 19-01-2018.
 */
public class FoodController implements Initializable
{
    Alert alert;

    DbManager dbsql;
    CallableStatement st ;
    String query;
    TableView tv = new TableView();


    @FXML
    private AnchorPane ancher1;
    @FXML
    private TextArea txtfoodid;

    @FXML
    private TextArea txtfoodname;

    @FXML
    private TextArea txtfoodprice;

    @FXML
    private ComboBox<?> combofoodticketid;

    public void initialize(URL location, ResourceBundle resources) {

        dbsql = new DbManager();
        btnNewFoodClick(new ActionEvent());

        query = "SELECT TicketId,TicketColorCode FROM Ticket";
        dbsql.loadDDL(query,combofoodticketid);

        clickItem();


    }
    @FXML
    private Button btnfoodnew;

    @FXML
    private Button btnfoodsave;

    @FXML
    private Button btnfoodupdate;

    @FXML
    private Button btnfooddelete;

    @FXML
    private TableView<?> tbviewfood;

    @FXML
    void btnDeleteFoodClick(ActionEvent event)
    {
        try
        {

            System.out.println("\n ID = " + txtfoodid.getText());
            dbsql.connectMySql();
            st=dbsql.conn.prepareCall("{call spDeleteFood(?)}");

            st.setInt(1,Integer.parseInt(txtfoodid.getText()));

            st.execute();
            System.out.println("Food Record Inserted");


            btnNewFoodClick(new ActionEvent() );


            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Food Record Deleted");
            alert.showAndWait();
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
    void btnNewFoodClick(ActionEvent event) {
        int FoodId =  dbsql.getRecordNumber("food","FoodId");
        txtfoodid.setText((FoodId+1)+"");

        txtfoodname.clear();
        txtfoodprice.clear();
        combofoodticketid.setPromptText("Select Ticket Type");

        tv=dbsql.buildData("SELECT *FROM viewfood");
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
        c.setPrefWidth(100);
        c.setStyle("-fx-alignment:CENTER;");
        c = (TableColumn) tv.getColumns().get(3);
        c.setPrefWidth(100);
        c.setStyle("-fx-alignment:CENTER;");
        clickItem();
       // ancher1.getChildren().add(dbsql.buildData("SELECT *FROM viewfood"));

    }

    @FXML
    void btnSaveFoodClick(ActionEvent event) {

        try
        {

            System.out.println("\n Food Save Button Clicked");
            dbsql.connectMySql();
            st=dbsql.conn.prepareCall("{call spInsertFood(?,?,?,?)}");

            st.setInt(1,Integer.parseInt(txtfoodid.getText()));
            st.setString(2,txtfoodname.getText());
            st.setInt(3,Integer.parseInt(txtfoodprice.getText()));
            st.setString(4,combofoodticketid.getValue()+"");

            st.execute();
            System.out.println("Food Record Inserted");


            btnNewFoodClick(new ActionEvent() );


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
        alert.setContentText("Food Record Inserted");
        alert.showAndWait();
    }
    @FXML
    void clickItem()
    {

        tv.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // System.out.println(tv.getItems().get(tv.getSelectionModel().getSelectedIndex()));
                Object selecteditems = tv.getSelectionModel().getSelectedItems().get(0);
                txtfoodid.setText(selecteditems.toString().split(",")[0].substring(1));

                txtfoodname.setText(selecteditems.toString().split(",")[1].substring(1));
                txtfoodprice.setText(selecteditems.toString().split(",")[2].substring(1));

                String p = (selecteditems.toString().split(",")[3].substring(1));

                char c = ' ';

                String d = p.replace(']',c);

                combofoodticketid.setPromptText(d);
            }
          });
        }

    @FXML
    void btnUpdateFoodClick(ActionEvent event) {
        try
        {

            System.out.println("\n Food Update Button Clicked");
            dbsql.connectMySql();
            st=dbsql.conn.prepareCall("{call spUpdateFood(?,?,?,?)}");

            st.setInt(1,Integer.parseInt(txtfoodid.getText()));
            st.setString(2,txtfoodname.getText());
            st.setInt(3,Integer.parseInt(txtfoodprice.getText()));
            st.setString(4,combofoodticketid.getPromptText());

            st.execute();
            System.out.println("Food Record Updated");


            btnNewFoodClick(new ActionEvent() );


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
        alert.setContentText("Food Record Updated");
        alert.showAndWait();
    }
}
