package sample.staff;
//import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.DateTimeStringConverter;
import sample.Controller;
import sample.DbManager;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ResourceBundle;
import javafx.scene.image.*;
import java.util.function.IntUnaryOperator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.Initializable;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Created by Parnali on 19-01-2018.
 */
public class StaffController implements Initializable {

    Alert alert;
   LocalDate d,d1;
    String q;
    int a;
    Time t1,t2 ;


   DateFormat format = new SimpleDateFormat("hh:mm");
   DateTimeFormatter dtformatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    TableView tv = new TableView();
    int StaffId;
    double sid;

    public ImageView getImgstaffphoto()
    {
        return imgstaffphoto;
    }

    DbManager dbsql;
    CallableStatement st ;
    String query;
    @FXML
    private AnchorPane ancher1;
    @FXML
    private TextArea txtstafffname;

    @FXML
    private DatePicker datestaffjoining;

    @FXML
    private TextArea txtstaffid;

    @FXML
    private TextArea txtstaffmname;

    @FXML
    private TextArea txtstafflname;

    @FXML
    private TextArea txtstafffullname;

    @FXML
    private DatePicker datestaffdob;

    @FXML
    private TextArea txtstaffcontact;

    @FXML
    private ComboBox<?> combostaffpostid;

    @FXML
    private TextArea txtstafftimein;

    @FXML
    private TextArea txtstafftimeout;

    @FXML
    private TextArea txtstaffusername;

    @FXML
    private PasswordField staffpassword;

    @FXML
    private ImageView imgstaffphoto;

    @FXML
    private ImageView imgstaffsign;

    public void initialize(URL location, ResourceBundle resources) {

        dbsql = new DbManager();


        txtstaffid.setVisible(true);

        query = "SELECT PostId,PostType FROM Post";
        dbsql.loadDDL(query,combostaffpostid);
        btnNewStaffClick(new ActionEvent());


        clickItem();
    }

    @FXML
    private Button btnstaffphoto;

    @FXML
    private Button btnstaffsign;

    @FXML
    private Button btnstaffnew;

    @FXML
    private Button btnstaffsave;

    @FXML
    private Button btnstaffupdate;

    @FXML
    private Button btnstaffdelete;

    @FXML
    void btnDeleteStaffClick(ActionEvent event)
    {
        try
        {

            dbsql.connectMySql();
            st=dbsql.conn.prepareCall("{call spDeleteStaff(?)}");
            st.setInt(1,Integer.parseInt(txtstaffid.getText()));

            st.execute();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Staff Record Deleted");
            alert.showAndWait();
            btnNewStaffClick(new ActionEvent());


        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("\n Staff connected with another field. Cannot be Deleted ");
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
    void btnNewStaffClick(ActionEvent event) {

        StaffId =  dbsql.getRecordNumber("Staff","StaffId");
        System.out.println("\n Staff ID = " + StaffId);
        txtstaffid.setText((StaffId)+"");

        //datestaffjoining.setDate();
        txtstafffname.setText("");
        txtstaffmname.setText("");
        txtstafflname.setText("");
        //txtstafffullname.setText(" ");
        txtstaffcontact.setText("");
        txtstafftimein.setText("");
        txtstafftimeout.setText("");
        txtstaffusername.setText("");
        staffpassword.setText("");
        combostaffpostid.setPromptText("  ");

        imgstaffphoto.setImage(null);
        imgstaffsign.setImage(null);
        datestaffdob.setValue(null);
        datestaffjoining.setValue(null);

            combostaffpostid.setPromptText("select Post");




        //ancher1.getChildren().add(dbsql.buildData("SELECT *FROM viewStaff"));

        tv=dbsql.buildData("SELECT *FROM viewStaff");
        ancher1.getChildren().clear();
        ancher1.getChildren().add(tv);
        TableColumn c;
        c = (TableColumn) tv.getColumns().get(0);
        c.setPrefWidth(80);
        c.setStyle("-fx-alignment:CENTER;");
        c = (TableColumn) tv.getColumns().get(1);
        c.setPrefWidth(100);
        c.setStyle("-fx-alignment:CENTER;");
        c = (TableColumn) tv.getColumns().get(2);
        c.setPrefWidth(80);
        c.setStyle("-fx-alignment:CENTER;");
        c = (TableColumn) tv.getColumns().get(3);
        c.setPrefWidth(80);
        c.setStyle("-fx-alignment:CENTER;");
        c = (TableColumn) tv.getColumns().get(4);
        c.setPrefWidth(80);
        c.setStyle("-fx-alignment:CENTER;");
        c = (TableColumn) tv.getColumns().get(5);
        c.setPrefWidth(100);
        c.setStyle("-fx-alignment:CENTER;");
        c = (TableColumn) tv.getColumns().get(6);
        c.setPrefWidth(100);
        c.setStyle("-fx-alignment:CENTER;");
        c = (TableColumn) tv.getColumns().get(7);
        c.setPrefWidth(80);
        c.setStyle("-fx-alignment:CENTER;");
        c = (TableColumn) tv.getColumns().get(8);
        c.setPrefWidth(80);
        c.setStyle("-fx-alignment:CENTER;");
        c = (TableColumn) tv.getColumns().get(9);
        c.setPrefWidth(100);
        c.setStyle("-fx-alignment:CENTER;");
        c = (TableColumn) tv.getColumns().get(10);
        c.setPrefWidth(100);
        c.setStyle("-fx-alignment:CENTER;");
        c = (TableColumn) tv.getColumns().get(11);
        c.setPrefWidth(100);
        c.setStyle("-fx-alignment:CENTER;");
        clickItem();


    }

    @FXML
    void btnSaveStaffClick(ActionEvent event)
    {
        try
        {
         System.out.println("\n Staff Save Button Clicked");
            dbsql.connectMySql();
            st=dbsql.conn.prepareCall("{call spInsertStaff(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

            q=txtstaffid.getText();
            a=Integer.parseInt(q);

            System.out.println("Value of Staff Id is : "+a);
            try
            {
                st.setInt(1,a);
            }
            catch(MySQLIntegrityConstraintViolationException eq)
            {
                System.out.println("\n Exception is "+eq);
            }

            st.setString(2, datestaffjoining.getValue()+"");
            st.setString(3,txtstafffname.getText());
            st.setString(4,txtstaffmname.getText());
            st.setString(5,txtstafflname.getText());
            //st.setString(6,txtstafffullname.getText());
           st.setString(6,datestaffdob.getValue()+"");
            st.setString(7,txtstafftimein.getText());
            st.setString(8,txtstafftimeout.getText());

            st.setString(9,combostaffpostid.getValue()+"");
            st.setString(10,txtstaffcontact.getText());
            st.setString(11,txtstaffusername.getText());
            st.setString(12,staffpassword.getText());
            FileInputStream fphoto = new FileInputStream(DbManager.file);
            st.setBinaryStream(13, (InputStream)fphoto,(int)DbManager.file.length());

            FileInputStream fsign = new FileInputStream(DbManager.file);
            st.setBinaryStream(14, (InputStream)fsign,(int)DbManager.file.length());

            st.execute();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Staff Record Inserted");
            alert.showAndWait();


            btnNewStaffClick(new ActionEvent());


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
    void btnUpdateStaffClick(ActionEvent event) {
        try
        {
            System.out.println("\n Staff New Button Clicked");
            dbsql.connectMySql();
            st=dbsql.conn.prepareCall("{call spUpdateStaff(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

            q=txtstaffid.getText();
            a=Integer.parseInt(q);

            System.out.println("Value of Staff Id is : "+a);
            try
            {
                st.setInt(1,a);
            }
            catch(MySQLIntegrityConstraintViolationException eq)
            {
                System.out.println("\n Exception is "+eq);
            }

            st.setString(2, datestaffjoining.getValue()+"");
            st.setString(3,txtstafffname.getText());
            st.setString(4,txtstaffmname.getText());
            st.setString(5,txtstafflname.getText());
            //st.setString(6,txtstafffullname.getText());
            st.setString(6,datestaffdob.getValue()+"");
            st.setString(7,txtstafftimein.getText());
            st.setString(8,txtstafftimeout.getText());

            st.setString(9,combostaffpostid.getValue()+"");
            st.setString(10,txtstaffcontact.getText());
            st.setString(11,txtstaffusername.getText());
            st.setString(12,staffpassword.getText());
            FileInputStream fphoto = new FileInputStream(DbManager.file);
            st.setBinaryStream(13, (InputStream)fphoto,(int)DbManager.file.length());

            FileInputStream fsign = new FileInputStream(DbManager.file);
            st.setBinaryStream(14, (InputStream)fsign,(int)DbManager.file.length());

            st.execute();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Staff Record Updated");
            alert.showAndWait();


            btnNewStaffClick(new ActionEvent());


        }
        catch (Exception ex)
        {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(ex.toString());
            alert.showAndWait();

            ex.printStackTrace();
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
                Object selecteditems = tv.getSelectionModel().getSelectedItems().get(0);
                String ef = (selecteditems.toString().split(",")[0].substring(1));
                txtstaffid.setText(ef);
                String de = selecteditems.toString().split(",")[1].substring(1);

                try
                {
                    d = LocalDate.parse(de);
                    System.out.println(d);
                }
                catch (Exception e1)
                {
                    System.out.println("\n Exception is : "+e1);
                }
                datestaffjoining.setValue(d);
               txtstafffname.setText(selecteditems.toString().split(",")[2].substring(1));
                txtstaffmname.setText(selecteditems.toString().split(",")[3].substring(1));
                txtstafflname.setText(selecteditems.toString().split(",")[4].substring(1));
                //txtstafffullname.setText(selecteditems.toString().split(",")[5].substring(1));
                String e = selecteditems.toString().split(",")[6].substring(1);

                try
                {
                    d1 = LocalDate.parse(e);
                    System.out.println(d);
                }
                catch (Exception e1)
                {
                    System.out.println("\n Exception is : "+e1);
                }
                datestaffdob.setValue(d1);
                txtstafftimein.setText(selecteditems.toString().split(",")[7].substring(1));
                txtstafftimeout.setText(selecteditems.toString().split(",")[8].substring(1));
                combostaffpostid.setPromptText(selecteditems.toString().split(",")[9].substring(1));
                txtstaffcontact.setText(selecteditems.toString().split(",")[10].substring(1));
                txtstaffusername.setText(selecteditems.toString().split(",")[11].substring(1));
                staffpassword.setText(selecteditems.toString().split(",")[12].substring(1));

               /* query = "SELECT Photo FROM Staff WHERE StaffId="+txtstaffid.getText();
                ImageIcon im = dbsql.readImage(query);

                imgstaffphoto.setImage(im.getImage());  */


            }
        });
    }
    @FXML
    void btnUploadPhotoStaffClick(ActionEvent event)
    {
       imgstaffphoto.setImage(dbsql.InsertImage());
    }

    @FXML
    void btnUploadSignStaffClick(ActionEvent event) {
        imgstaffsign.setImage(dbsql.InsertImage());
    }
}
