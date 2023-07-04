package sample.MDI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class IndexController implements Initializable {


    @FXML
    private AnchorPane ancherMain;

    @FXML
    public ListView<String> listViewMaster;

    @FXML
    private AnchorPane MsterAnchor;

    @FXML
    private TabPane tabpane1;



    public IndexController()
    {}

    public IndexController(boolean result)
    {
        if(result)
            MsterAnchor.setDisable(false);
        else
            MsterAnchor.setDisable(true);
    }

    @Override
    public void initialize(URL localtion, ResourceBundle resources) {

        btnLogin_Click(new ActionEvent());

        loadListView();
        System.out.println("initialize");
     //   MsterAnchor.setDisable(true);
    }

    public void loadListView()
    {
        System.out.println("loadList");
        ObservableList <String> obl = FXCollections.observableArrayList();

        obl.add("Batch Allocation");
        obl.add("Rides Cleaning");
        obl.add("Food Court");
        obl.add("Staff Post");
        obl.add("Ride Master");
        obl.add("Salary Voucher");
        obl.add("Staff Details");
        obl.add("Ticket");
        obl.add("Waterpark Rides");
        obl.add("");

        listViewMaster.setItems(obl);

        System.out.println(obl);

        selectmenu();
    }

    @FXML
    void btnLogin_Click(ActionEvent event) {
        try {
            Node node = (AnchorPane) FXMLLoader.load(getClass().getResource("/sample/login/login.fxml"));
            Tab tb = new Tab("Login", node);
            tabpane1.getTabs().add(tb);
            //ane2.getTabs().add(tb);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void selectmenu()
    {
        listViewMaster.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                int i = listViewMaster.getSelectionModel().getSelectedIndex();
                System.out.println("\n i = " + i);


                switch (i)
                {
                    case 0:
                        try {
                            Node node = (AnchorPane) FXMLLoader.load(getClass().getResource("/sample/batch/Batch.fxml"));
                            Tab tb = new Tab("Batch Allocation", node);

                            if(!tabpane1.getTabs().contains(tb))
                                tabpane1.getTabs().add(tb);
                            //ane2.getTabs().add(tb);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 1:
                        try {
                            Node node = (AnchorPane) FXMLLoader.load(getClass().getResource("/sample/cleaning/Cleaning.fxml"));
                            Tab tb = new Tab("Rides Cleaning", node);
                            tabpane1.getTabs().add(tb);
                            //ane2.getTabs().add(tb);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        try {
                            Node node = (AnchorPane) FXMLLoader.load(getClass().getResource("/sample/foodtable/Food Table.fxml"));
                            Tab tb = new Tab("Food Court", node);
                            tabpane1.getTabs().add(tb);
                            //ane2.getTabs().add(tb);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        try {
                            Node node = (AnchorPane) FXMLLoader.load(getClass().getResource("/sample/post/Post.fxml"));
                            Tab tb = new Tab("Staff Post", node);
                            tabpane1.getTabs().add(tb);
                            //ane2.getTabs().add(tb);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 4:
                        try {
                            Node node = (AnchorPane) FXMLLoader.load(getClass().getResource("/sample/ridemaster/RideMaster.fxml"));
                            Tab tb = new Tab("Ride Master", node);
                            tabpane1.getTabs().add(tb);
                            //ane2.getTabs().add(tb);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 5:
                        try {
                            Node node = (AnchorPane) FXMLLoader.load(getClass().getResource("/sample/salaryvowcher/SalaryVowcher.fxml"));
                            Tab tb = new Tab("Salary Voucher", node);
                            tabpane1.getTabs().add(tb);
                            //ane2.getTabs().add(tb);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 6:
                        try {
                            Node node = (AnchorPane) FXMLLoader.load(getClass().getResource("/sample/staff/Staff.fxml"));
                            Tab tb = new Tab("Staff Details", node);
                            tabpane1.getTabs().add(tb);
                            //ane2.getTabs().add(tb);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 7:
                        try {
                            Node node = (AnchorPane) FXMLLoader.load(getClass().getResource("/sample/ticket/tkt.fxml"));
                            Tab tb = new Tab("Ticket", node);
                            tabpane1.getTabs().add(tb);
                            //ane2.getTabs().add(tb);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 8:
                        try {
                            Node node = (AnchorPane) FXMLLoader.load(getClass().getResource("/sample/waterparkrides/WaterparkRides.fxml"));
                            Tab tb = new Tab("Waterpark Rides", node);
                            tabpane1.getTabs().add(tb);
                            //ane2.getTabs().add(tb);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                }



                    };



        });}




}
