package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import static sample.Main.getConnection;

public class Order {


    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    Button back_btn;
    @FXML
    Label cid_lbl;
    @FXML
    Label name_lbl;
    @FXML
    Label address_lbl;
    @FXML
    Label country_lbl;
    @FXML
    Button save_btn;

    @FXML
    CheckBox gst_chk;
    @FXML
    TextField gst_no_tf;

    customer customer;
    @FXML
    DatePicker date_pick;

    @FXML
    TextArea desc_ta;
    @FXML
    Button prev_invoice_btn;


    @FXML
    TableView<desc> tablr_desc;
    @FXML
    TableColumn<desc,String> col_slno;
    @FXML
    TableColumn<desc,String> col_item;
    @FXML
    TableColumn<desc,String> col_amt;


    @FXML
    TextField txt_item;
    @FXML
    TextField txt_amt;
    @FXML
    Button btn_add;


    public void switchToScene2(ActionEvent event) throws IOException {

            // root = FXMLLoader.load(getClass().getResource("customer_management.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("customer_management.fxml"));
            root = loader.load();
            Controller_customerMangement controller_customerMangement = loader.getController();
            ArrayList<customer> customers = new ArrayList<>();
            Main main = new Main();
            try {
                customers = main.getData();
            }
            catch (Exception e)
            {
                System.out.println(e);
            }

            controller_customerMangement.display(customers);

            stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);

            stage.show();

    }

    public void isgst(ActionEvent event)
    {
        if(gst_chk.isSelected())
        {
            gst_no_tf.setVisible(true);
        }
        else
        {
            gst_no_tf.setVisible(false);
        }

    }
    public void save_data()
    {
        System.out.println(validation());
        if(validation()){
        LocalDate date = date_pick.getValue();
        int cid = customer.getId();
        int count =0;
        try {
             count = getData();
             count++;
            String oid = "TW/"+date.getYear()+"/"+date.getMonth().toString()+"/"+count;
            System.out.println(oid);
            try {


                    if(gst_no_tf.getText().equals(""))
                        insetData(oid,customer.getId(),desc_ta.getText(),"NA");
                    else
                        insetData(oid,customer.getId(),desc_ta.getText(),gst_no_tf.getText());


            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        }
        else
        {
            System.out.println("validation failed");
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Validation");
//                    alert.setHeaderText("Field can not be empty");
//                    alert.setContentText("Date or description can not be empty");
//
//                    alert.showAndWait();

            Toast.show("validation failed",this.save_btn);
        }
       // System.out.println(date.toString()+" "+cid+" "+count);

    }

    public void setlabel(customer customer)
    {
        cid_lbl.setText(String.valueOf(customer.getId()));
        this.customer = customer;
        address_lbl.setText(customer.getAddress());
        name_lbl.setText(customer.getName());
        country_lbl.setText(customer.getCountry());

    }
    public boolean validation()
    {

       // System.out.println(date_pick.getPromptText());
        if(date_pick.getValue() == null)
        {
            return false;
        }
        if(desc_ta.getText().equals(""))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public void insetData(String oid,int cid,String order,String gst) throws Exception
    {
        Connection connection = getConnection();
        try {
            Statement statement2 = connection.createStatement();
            String query1 = "INSERT INTO "+ "`"+"order"+"` " + "VALUES "+"("+"'"+oid+"'"+","+" '"+cid+"'"+", "+"'"+order+"' ,"+"'"+gst+"'"+")";
            System.out.println(query1);
            statement2.executeUpdate(query1);

            System.out.println("Record is inserted in the table successfully..................");
        }
        catch (SQLException excep) {
            excep.printStackTrace();
        } catch (Exception excep) {
            excep.printStackTrace();
        }
        connection.close();

    }
    public int getData() throws Exception
    {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("Select * from "+"`"+"order"+"`");
        int count =0;

        while (rs.next())
        {
            System.out.println(rs.getString(1)+" "+
                    rs.getString(2)+" "+
                    rs.getString(3)+" "+
                    rs.getString(4));
            count++;
            //customers.add(new customer(rs.getInt(1), rs.getString(2), rs.getString(3),  rs.getString(4)));
        }
        connection.close();
        return count;
    }
    public void switchToprevOrder(ActionEvent event) throws IOException {

        // root = FXMLLoader.load(getClass().getResource("customer_management.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("previnvoice.fxml"));
        root = loader.load();
        Previnvoice previnvoice = loader.getController();

        try {
           if(getcustomData(this.customer).size()!=0)
           {
               ArrayList<OrderCustomer> orderCustomers = getcustomData(this.customer);
               previnvoice.display(orderCustomers);
           }
           else
           {
               Toast.show("No data found",this.prev_invoice_btn);
           }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //   controller_customerMangement.display(customers);

        stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

        stage.show();

    }
    public ArrayList<OrderCustomer> getcustomData(customer customer) throws Exception
    {
        ArrayList<OrderCustomer> orderCustomers = new ArrayList<>();
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("Select * from "+"`"+"order"+"`"+"WHERE"+" cid = "+customer.getId());
        int count =0;

        while (rs.next())
        {
            System.out.println(rs.getString(1)+" "+
                    rs.getString(2)+" "+
                    rs.getString(3)+" "+
                    rs.getString(4));
            count++;
            orderCustomers.add(new OrderCustomer(rs.getString(1), rs.getString(2), rs.getString(3),  rs.getString(4),customer.getName(),customer.getAddress(),customer.getCountry()));
        }
        connection.close();
        return orderCustomers;
    }
    ArrayList<desc> descs = new ArrayList<>();
     void insertToTable()
    {

        tablr_desc.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        col_slno.setCellValueFactory(new PropertyValueFactory<desc, String>("slno"));
        col_item.setCellValueFactory(new PropertyValueFactory<desc, String>("item"));
        col_amt.setCellValueFactory(new PropertyValueFactory<desc, String>("amount"));

        tablr_desc.getItems().setAll(descs);
        String description="";
        for(desc d:descs)
        {
            description = "\n"+description+"Sl No: "+d.getSlno()+"   "+"Item: "+d.getItem()+"   "+"Amount: "+d.getAmount()+"\n";
        }
        desc_ta.setText(description);

    }
   public void setNew()
    {
        String Slno = String.valueOf(descs.size()+1);
        if(!txt_item.getText().equals("") && !txt_amt.getText().equals(""))
        {
            String item = txt_item.getText().toString();
            String amt = txt_amt.getText().toString();
            descs.add(new desc(Slno,item,amt));
            insertToTable();
            txt_amt.setText("");
            txt_item.setText("");
        }
        else
            Toast.show("Field can not be null..",this.btn_add);
    }

}
