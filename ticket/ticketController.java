package sample.ticket;



import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.converter.DateTimeStringConverter;
import sample.DbManager;

import javax.swing.text.DateFormatter;

import com.mysql.jdbc.PreparedStatement;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.text.SimpleDateFormat;

import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;



public class ticketController implements Initializable
{
	   TableView tv1 = new TableView();
	   TableView tv2 = new TableView();

    @FXML
    private TextArea txtticketid;

    @FXML
    private TextArea txtticketcolorcode;

    @FXML
    private TextArea txtticketquota;

    @FXML
    private Button btnticketnew;

    @FXML
    private Button btnticketupdate;

    @FXML
    private Button btnticketsave;

    @FXML
    private Button btnticketdelete;

    @FXML
    private TextArea txtTicketDate;
    
    @FXML
    private TextArea txtDateTicketMaster;

    @FXML
    private ComboBox<?> cbBactch;

    @FXML
    private TextArea txtAmount;

    @FXML
    private Spinner<Integer> spinnerTo;

    @FXML
    private Spinner<Integer> spinerFrom;

    @FXML
    private Rectangle rectangle1;

    @FXML
    private TextArea txtTotalQtys;

    @FXML
    private TextArea txtPaybleAmount;

    @FXML
    private Button btnNewTickitMaster;

    @FXML
    private Button btnDeleteTicketMaster;

    @FXML
    private Button btnConfirmTicket;


    @FXML
    private AnchorPane ancherTicketMaster;
    
    @FXML
    private AnchorPane ancher1;
    
    DbManager dbsql;
    CallableStatement st;
    String query;
 
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {

        dbsql = new DbManager();

        query = "SELECT BatchId,BatchType FROM Batch";
        dbsql.loadDDL(query, cbBactch);

        btnNewTicketClick(new ActionEvent());
        btnNewTicketMaster(new ActionEvent());
        clickItem_Ticket();
        clickItem_TicketMaster();

        txtTotalQtys.setEditable(false);
      
        try
        {
       DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
       Date date = new Date();

		txtDateTicketMaster.setText(dateFormat.format(date));
        }
        catch(Exception e)
        {
        		System.out.println(e.toString());
        }
        

	}
	
   @FXML
    void btnNewTicketClick(ActionEvent event) {
	 	int ticketid = dbsql.getRecordNumber("ticket","TicketId");
	 	txtticketid.setText((ticketid)+"");
	 
	  	  tv1=dbsql.buildData("SELECT *FROM viewticket");
	      ancher1.getChildren().add(tv1);
    }

   @FXML
   void btnSaveTicket(ActionEvent event) {
	   Alert alert = new Alert(Alert.AlertType.INFORMATION);
	   try
       {       	
	            dbsql.connectMySql();
	            st=dbsql.conn.prepareCall("{call spInsertTicket(?,?,?)}");
	
	            int ticketid=Integer.parseInt(txtticketid.getText());
	            st.setInt(1,ticketid);
	            st.setString(2,txtticketcolorcode.getText());
	            st.setInt(3,Integer.parseInt(txtticketquota.getText()));
	            
	            st.execute();
	            alert = new Alert(Alert.AlertType.INFORMATION);
	            alert.setContentText("New Tickit Type Created");
	            alert.showAndWait();
	            btnNewTicketMaster(new ActionEvent() );
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
   void btnUpdateTicketClick(ActionEvent event) {
	   Alert alert = new Alert(Alert.AlertType.INFORMATION);
	   try
       {       	
	            dbsql.connectMySql();
	            st=dbsql.conn.prepareCall("{call spUpdateTicket(?,?,?)}");
	
	            int ticketid=Integer.parseInt(txtticketid.getText());
	            st.setInt(1,ticketid);
	            st.setString(2,txtticketcolorcode.getText());
	            st.setInt(3,Integer.parseInt(txtticketquota.getText()));
	            
	            st.execute();
	            alert = new Alert(Alert.AlertType.INFORMATION);
	            alert.setContentText("Tickit Type Updated");
	            alert.showAndWait();
	            btnNewTicketMaster(new ActionEvent() );
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
   void btnDeleteTicketClick(ActionEvent event) {
	   Alert alert = new Alert(Alert.AlertType.INFORMATION);
	   try
       {       	
	            dbsql.connectMySql();
	            st=dbsql.conn.prepareCall("{call spDeleteTicket(?)}");
	
	            int ticketid=Integer.parseInt(txtticketid.getText());
	            st.setInt(1,ticketid);
	      
	            
	            st.execute();
	            alert = new Alert(Alert.AlertType.INFORMATION);
	            alert.setContentText("Tickit Type Deleted");
	            alert.showAndWait();
	            btnNewTicketMaster(new ActionEvent() );
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
    void btnNewTicketMaster(ActionEvent event) {
        tv2=dbsql.buildData("SELECT *FROM viewtaskmanager");
        ancherTicketMaster.getChildren().add(tv2);
        
    	try
    	{
        int TicketMasterId =  dbsql.getRecordNumber("ticketmaster","tmid");
        final int initialValue = 3;
        
        // Value factory.
        SpinnerValueFactory<Integer> valueFactory; 
        		valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(TicketMasterId,TicketMasterId , initialValue);
 
        spinerFrom.setValueFactory(valueFactory);
        
		valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(TicketMasterId,TicketMasterId+10, initialValue);

		spinnerTo.setValueFactory(valueFactory);   
	
    	}
    	catch(Exception ex)
    	{
    		System.out.println(ex.toString());
    	}

    	
    }
    
    @FXML
    void btnConfirmTicketMaster(ActionEvent event) {
        try
        {
        	int to = spinnerTo.getValue();
        	int from  = spinerFrom.getValue();
        	
           for(int i=from;i<=to;i++)
           {
	            dbsql.connectMySql();
	            st=dbsql.conn.prepareCall("{call spInsertTicketMaster(?,?,?)}");
	
	            st.setInt(1,i);
	            st.setString(2,txtDateTicketMaster.getText());
	            st.setString(3,cbBactch.getValue()+"");
	            
	            st.execute();
	            btnNewTicketMaster(new ActionEvent() );
           }
        }
	        catch (Exception ex)
	        {
	        	 Alert alert = new Alert(Alert.AlertType.INFORMATION);
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
    void btnDeleteTicketMaster(ActionEvent event) {
 	   Alert alert = new Alert(Alert.AlertType.INFORMATION);
 	   try
        {       	
 	            dbsql.connectMySql();
 	            st=dbsql.conn.prepareCall("{call spDeleteTicketMaster(?)}");
 	
 	            int ticketid=Integer.parseInt(spinerFrom.getValue()+"");
 	            st.setInt(1,ticketid);
 	      
 	            
 	            st.execute();
 	            alert = new Alert(Alert.AlertType.INFORMATION);
 	            alert.setContentText("Tickit Deleted");
 	            alert.showAndWait();
 	            btnNewTicketMaster(new ActionEvent() );
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
    void cbBatchSelected(ActionEvent event) {
    
    	
    	String colorname="";
    	 try
         {
    		 
             dbsql.connectMySql();
             st=dbsql.conn.prepareCall( "SELECT *FROM viewBatch WHERE Type='"+cbBactch.getValue()+"'");

             ResultSet r = st.executeQuery();
             if(r.next())
             {            	 
            	 colorname=r.getString(4);
            	 txtAmount.setText(r.getInt(5)+"");
             }
             
             Color c = Color.web(colorname);
             rectangle1.setFill(c);
             
             try
             {
	         	int amount = Integer.parseInt(txtAmount.getText());
	         	
	         	int q =Integer.parseInt(txtTotalQtys.getText());
	        	int totalamt = amount*q;
	        	
	        	txtPaybleAmount.setText(totalamt+"");
             }
             catch(Exception e) {}
          }
         catch (Exception e)
         {
             Alert alert = new Alert(Alert.AlertType.WARNING);
             alert.setContentText("\n Failed to Communicate Database "+e.toString() + "\n" + colorname);
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
    void SpinnerToSelected(MouseEvent event) {
    	int to = spinnerTo.getValue();
    	int from  = spinerFrom.getValue();
    	
    	int q = to-from;
    	txtTotalQtys.setText(q+"");
    	
    	int amount = Integer.parseInt(txtAmount.getText());
    	int totalamt = amount*q;
    	
    	txtPaybleAmount.setText(totalamt+"");
    }

    @FXML
    void clickItem_Ticket()
    {
        tv1.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                Object selecteditems = tv1.getSelectionModel().getSelectedItems().get(0);
                
                String ef = (selecteditems.toString().split(",")[0].substring(1));
                txtticketid.setText(ef);
                
                String de = selecteditems.toString().split(",")[1].substring(1);
                txtticketcolorcode.setText(de);
                
                txtticketquota.setText(selecteditems.toString().split(",")[2].substring(1));
            }
        });
    }
    
    @FXML
    void clickItem_TicketMaster()
    {
        tv2.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
            	try
            	{
                Object selecteditems = tv2.getSelectionModel().getSelectedItems().get(0);
                String ef = (selecteditems.toString().split(",")[0].substring(1));
                int id=Integer.parseInt(ef);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
                SpinnerValueFactory<Integer> valueFactory; 
        		valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(id,id , 3);
 
        		spinerFrom.setValueFactory(valueFactory); 
            	}
            	catch(Exception ex)
            	{
            	    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText(" "+ex);
                    alert.showAndWait();	
            	}
               
            }
        });
    }
}

