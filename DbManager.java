package sample;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class DbManager {
    public Connection conn;
    String dbname;
    String query;
    Statement stmt;
    private ObservableList<ObservableList> data;
    private TableView tableview;
    public static File file;

    Alert alert;

    public DbManager()
    {
        dbname = "unknwon";
    }
    public DbManager(String dbname)
    {
        this.dbname = dbname;
    }
    public boolean connectOracle() throws Exception
    {
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
            return true;
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return false;
    }


    public boolean connectAccess()
    {
        return false;
    }

    public boolean connectMySql()throws  Exception
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbwaterpark","root","manager");
            return true;
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean connectSqlServer()
    {
        return false;
    }

    public int getRecordNumber(String tablename,String colname)
    {
        int id=-1;
        try {
            connectMySql();
            stmt = conn.createStatement();
            query="SELECT MAX("+ colname +") AS maxid FROM "+tablename;
            stmt.executeQuery(query);
            ResultSet rs = stmt.getResultSet();
            if(rs.next())
                id = rs.getInt(1)+1;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return id;


    }


    public void loadDDL(String q, ComboBox<?> ddl)
    {
        ArrayList a = new ArrayList();
        try {
            connectMySql();
            stmt = conn.createStatement();

            stmt.executeQuery(q);
            ResultSet rs = stmt.getResultSet();
            while(rs.next())
            {
               // rs.getInt(1);
                String val = rs.getString(2);

                a.add(val);
            }
            ddl.getItems().addAll(a);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public TableView buildData(String SQL){
        tableview = new TableView();
        tableview.autosize();


        Connection c ;
        data = FXCollections.observableArrayList();
        try{
            //SQL FOR SELECTING ALL OF CUSTOMER

            //ResultSet
            ResultSet rs = conn.createStatement().executeQuery(SQL);

            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tableview.getColumns().addAll(col);
            }

            /********************************
             * Data added to ObservableList *
             ********************************/
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }

                data.add(row);

            }

            //FINALLY ADDED TO TableView


            tableview.setItems(data);

        }catch(Exception e){
            e.printStackTrace();
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
        return tableview;
    }


    public void fillGridView()
    {
    }

    public Image InsertImage()
    {
        FileChooser fileChooser = new FileChooser();
        Image image=null;
        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        //Show open file dialog
        file = fileChooser.showOpenDialog(null);

        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            image= SwingFXUtils.toFXImage(bufferedImage, null);
        } catch (IOException ex) {
            //   Logger.getLogger(JavaFXPixel.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        }

        return image;

    }

    public ImageIcon readImage(String q)
    {
        byte []img;
        ImageIcon  image=null;

        try {
            connectMySql();
            stmt = conn.createStatement();

            stmt.executeQuery(q);
            ResultSet rs = stmt.getResultSet();
            if(rs.next())
            {
             img = rs.getBytes(1);
             image = new ImageIcon(img);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return image;
    }

}
