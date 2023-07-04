package sample.salaryvowcher;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.util.ResourceBundle;

import javax.swing.text.DateFormatter;

/**
 * Created by Parnali on 19-01-2018.
 */

public class SalaryVowcherController implements Initializable
{

    Alert alert;
    DbManager dbsql;
    CallableStatement st ;
    String query;

    LocalDate d;
    //DateTimeFormatter dtformatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");


    @FXML
    private AnchorPane ancher1;
    @FXML
    private TextArea txtsvid;

    @FXML
    private TextArea txtsvamount;

    @FXML
    private ComboBox<?> combosvstaffid;

    TableView tv = new TableView();

    @FXML
    private DatePicker datesv;
    private int SalaryVowcherId;

    public void initialize(URL location, ResourceBundle resources) {
        dbsql = new DbManager();

        query = "SELECT StaffId, FullName FROM Staff";
        dbsql.loadDDL(query,combosvstaffid);
        btnNewSVClick(new ActionEvent());
        clickItem();

    }

    private void clickItem()
    {
        tv.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                System.out.println(tv.getItems().get(tv.getSelectionModel().getSelectedIndex()));
                Object selecteditems = tv.getSelectionModel().getSelectedItems().get(0);
                txtsvid.setText(selecteditems.toString().split(",")[0].substring(1));
                combosvstaffid.setPromptText(selecteditems.toString().split(",")[1].substring(1));

                String e = (selecteditems.toString().split(",")[2].substring(1));
                try
                {
                    d = LocalDate.parse(e);
                    System.out.println(d);
                }
                catch (Exception e1)
                {
                    System.out.println("\n Exception is : "+e1);
                }
                datesv.setValue(d);
                //String z = (selecteditems.toString().split(",")[3].substring(1));


                String p = (selecteditems.toString().split(",")[3].substring(1));
                char c = ' ';
                String dr = p.replace(']',c);
                txtsvamount.setText(dr);

                System.out.println("p = " + d);

            }
        });
    }

    @FXML
    private Button btnsvnew;
    @FXML
    private Button btnsvsave;

    @FXML
    private Button btnsvdelete;

    @FXML
    private Button btnsvupdate;

    @FXML
    void btnDeleteSVClick(ActionEvent event)
    {
        try
        {

            dbsql.connectMySql();
            st=dbsql.conn.prepareCall("{call spDeleteSalaryVowcher(?)}");
            st.setInt(1,Integer.parseInt(txtsvid.getText()));

            st.execute();
             btnNewSVClick(new ActionEvent());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Salary Voucher Record Deleted");
            alert.showAndWait();
            btnNewSVClick(new ActionEvent());


        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("\n"+e);
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
        btnNewSVClick(new ActionEvent() );
    }

    @FXML
    void btnNewSVClick(ActionEvent event) {
        int svid =  dbsql.getRecordNumber("salary_vowcher","SalaryVowcherId");
        txtsvid.setText(svid+"");

        combosvstaffid.setPromptText(null);
        combosvstaffid.setPromptText("Select Staff Name");
        txtsvamount.setText(" ");
        datesv.setValue(null);
        tv=dbsql.buildData("SELECT *FROM viewsalaryvowcher");
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
    void btnSaveSVClick(ActionEvent event) {
        try
        {

            System.out.println("\n Salary Vowcher Save Button Clicked");
            dbsql.connectMySql();
            st=dbsql.conn.prepareCall("{call spInsertSalary_Vowcher(?,?,?,?)}");

            System.out.println("id =   "+txtsvid.getText());

            st.setInt(1,Integer.parseInt(txtsvid.getText()));
            d = datesv.getValue();
            st.setDate(2,Date.valueOf(d));
            String svam = txtsvamount.getText();

            st.setDouble(3,Double.parseDouble(svam));
            st.setString(4,combosvstaffid.getValue()+"");

            System.out.println("Amount in int: "+ Double.parseDouble(svam));


            st.execute();
            btnNewSVClick(new ActionEvent());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Salary Voucher Record Inserted");
            alert.showAndWait();

            btnNewSVClick(new ActionEvent() );

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

        btnNewSVClick(new ActionEvent() );

    }

    @FXML
    void btnUpdateSVClick(ActionEvent event) {

        try
        {

            System.out.println("\n Salary Vowcher Save Button Clicked");
            dbsql.connectMySql();
            st=dbsql.conn.prepareCall("{call spUpdateSalary_Vowcher(?,?,?,?)}");

            System.out.println("id =   "+txtsvid.getText());

            st.setInt(1,Integer.parseInt(txtsvid.getText()));
            d = datesv.getValue();
            st.setDate(2,Date.valueOf(d));
            String svam = txtsvamount.getText();

            st.setDouble(3,Double.parseDouble(svam));
            st.setString(4,combosvstaffid.getValue()+"");

            System.out.println("Amount in int: "+ Double.parseDouble(svam));


            st.execute();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Salary Vowcher Updated");
            alert.showAndWait();

            btnNewSVClick(new ActionEvent() );

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
        btnNewSVClick(new ActionEvent() );

    }

}
