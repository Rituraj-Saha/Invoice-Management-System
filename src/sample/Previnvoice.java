package sample;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Previnvoice {
    @FXML
    TableView<OrderCustomer> table_view;

    @FXML
    TableColumn<OrderCustomer,String> oid_col;
    @FXML
    TableColumn<OrderCustomer,String> cid_col;
    @FXML
    TableColumn<OrderCustomer,String> name_col;
    @FXML
    TableColumn<OrderCustomer,String> address_col;
    @FXML
    TableColumn<OrderCustomer,String> col_country;
    @FXML
    TableColumn<OrderCustomer,String> desc_col;
    @FXML
    TableColumn<OrderCustomer,String> gst_col;
    @FXML
    TableColumn<OrderCustomer,OrderCustomer> down_col;
    @FXML
    Button back_btn ;
    private Stage stage;
    private Scene scene;
    private Parent root;


    public void display(ArrayList<OrderCustomer> orderCustomers)
    {
        // label_two.setText(username);
        //  table_view.getColumns().addAll(customers);
      //  System.out.println(customers.get(0).getAddress());
        table_view.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//        table_view.getColumns().get(0).prefWidthProperty().bind(table_view.widthProperty().multiply(0.33));    // 33% for id column size
//        table_view.getColumns().get(1).prefWidthProperty().bind(table_view.widthProperty().multiply(0.33));   // 33% for dt column size
//        table_view.getColumns().get(2).prefWidthProperty().bind(table_view.widthProperty().multiply(0.33));    // 33% for cv column size

        oid_col.setCellValueFactory(new PropertyValueFactory<OrderCustomer, String>("oid"));
        cid_col.setCellValueFactory(new PropertyValueFactory<OrderCustomer, String>("cid"));
        name_col.setCellValueFactory(new PropertyValueFactory<OrderCustomer, String>("name"));
        address_col.setCellValueFactory(new PropertyValueFactory<OrderCustomer, String >("address"));
        col_country.setCellValueFactory(new PropertyValueFactory<OrderCustomer, String >("country"));
        desc_col.setCellValueFactory(new PropertyValueFactory<OrderCustomer, String >("desc"));
        gst_col.setCellValueFactory(new PropertyValueFactory<OrderCustomer, String >("gst"));

        //  col_invoice.setCellValueFactory(new PropertyValueFactory<customer, customer>("ok"));

        //TableColumn<customer, customer> col_invoice = column("Edit", ReadOnlyObjectWrapper<Person>::new, 60);

        // table_view.getColumns().add(col_invoice);

        down_col.setCellFactory(col -> {
            Button editButton = new Button("Download");
            TableCell<OrderCustomer, OrderCustomer> cell = new TableCell<OrderCustomer, OrderCustomer>() {
                @Override
                public void updateItem(OrderCustomer person, boolean empty) {
                    super.updateItem(person, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(editButton);
                    }
                }
            };

            editButton.setOnAction(e -> createpdf(table_view.getItems().get(cell.getIndex())));
            return cell ;
        });

        table_view.getItems().setAll(orderCustomers);
    }

    public void createpdf(OrderCustomer orderCustomer)
    {
        var document = new Document();
        try {
            PdfWriter.getInstance(document,new FileOutputStream(orderCustomer.getOid().replace("/","-")+".pdf"));
            document.open();
            var bold = new Font(Font.FontFamily.HELVETICA,18,Font.BOLD);
            var paragraph1 = new Paragraph("Oid: "+orderCustomer.getOid());
            var paragraph2 = new Paragraph("cid: "+orderCustomer.getCid());
            var paragraph3 = new Paragraph("Name: "+orderCustomer.getName());
            var paragraph4 = new Paragraph("Address: "+orderCustomer.getAddress());
            var paragraph5 = new Paragraph("Country: "+orderCustomer.getCountry());
            var paragraph6 = new Paragraph("Description: "+orderCustomer.getDesc());
            var paragraph7 = new Paragraph("Gst: "+orderCustomer.getGst());

            document.add(paragraph1);
            document.add(paragraph2);
            document.add(paragraph3);
            document.add(paragraph4);
            document.add(paragraph5);
            document.add(paragraph6);
            document.add(paragraph7);
            document.close();



        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
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
}
