package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class Controller_customerMangement {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    Label label_two;
    @FXML
    private AnchorPane scene_pane;
    @FXML
    TableView<customer> table_view;

    @FXML
    TableColumn<customer,Integer> col_id;
    @FXML
    TableColumn<customer,String> col_name;
    @FXML
    TableColumn<customer,String> col_country;
    @FXML
    TableColumn<customer,String> col_address;
    @FXML
    TableColumn<customer,customer> col_invoice;

    public void display(ArrayList<customer> customers)
    {
       // label_two.setText(username);
      //  table_view.getColumns().addAll(customers);
        System.out.println(customers.get(0).getAddress());
        table_view.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//        table_view.getColumns().get(0).prefWidthProperty().bind(table_view.widthProperty().multiply(0.33));    // 33% for id column size
//        table_view.getColumns().get(1).prefWidthProperty().bind(table_view.widthProperty().multiply(0.33));   // 33% for dt column size
//        table_view.getColumns().get(2).prefWidthProperty().bind(table_view.widthProperty().multiply(0.33));    // 33% for cv column size

        col_id.setCellValueFactory(new PropertyValueFactory<customer, Integer>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<customer, String>("name"));
        col_address.setCellValueFactory(new PropertyValueFactory<customer, String>("address"));
        col_country.setCellValueFactory(new PropertyValueFactory<customer, String >("country"));
      //  col_invoice.setCellValueFactory(new PropertyValueFactory<customer, customer>("ok"));

        //TableColumn<customer, customer> col_invoice = column("Edit", ReadOnlyObjectWrapper<Person>::new, 60);

       // table_view.getColumns().add(col_invoice);

        col_invoice.setCellFactory(col -> {
            Button editButton = new Button("Add Invoice");
            TableCell<customer, customer> cell = new TableCell<customer, customer>() {
                @Override
                public void updateItem(customer person, boolean empty) {
                    super.updateItem(person, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(editButton);
                    }
                }
            };

            editButton.setOnAction(e -> edit(table_view.getItems().get(cell.getIndex()),e));
            return cell ;
        });

        table_view.getItems().setAll(customers);
    }
public void edit(customer customer,ActionEvent event)
{
    System.out.println(customer.getId());
    try {
        switchToSceneInvoice(event,customer);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    public void switchToScene1(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log Out!!");
        alert.setHeaderText("Are You Sure ?");
        alert.setContentText("Do you really want to log out?");
        if(alert.showAndWait().get() == ButtonType.OK)
        {
            root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            stage = (Stage) scene_pane.getScene().getWindow();

            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }
    public void switchToSceneInvoice(ActionEvent event,customer customer) throws IOException {

//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Log Out!!");
//        alert.setHeaderText("Are You Sure ?");
//        alert.setContentText("Do you really want to log out?");
//        if(alert.showAndWait().get() == ButtonType.OK)
//        {
          //  root = FXMLLoader.load(getClass().getResource("order.fxml"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("order.fxml"));
        root = loader.load();
        Order order = loader.getController();
        order.setlabel(customer);
//            stage = (Stage) scene_pane.getScene().getWindow();
//            scene = new Scene(root);
//            stage.setScene(scene);
//            stage.show();
        stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
//        }

    }

    public void insertCustomer()
    {

//        TextInputDialog td = new TextInputDialog("Enter name");
//        td.setTitle("New Customer");
//        td.getDialogPane().setContentText("Name: ");
//        Optional<String> result = td.showAndWait();
//        TextField input = td.getEditor();
//        if(input.getText() != null && input.getText().toString().length() !=0)
//        {
//            System.out.println(""+input.getText().toString());
//        }
        ArrayList<String> arrayList = new ArrayList<>();
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("TestName");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField name = new TextField();
        name.setPromptText("Name");
        TextField address = new TextField();
        address.setPromptText("Address");
        TextField country = new TextField();
        address.setPromptText("Country");
        gridPane.add(new Label("Name: "), 0, 0);
        gridPane.add(name, 1, 0);
        gridPane.add(new Label("Address: "), 2, 0);
        gridPane.add(address, 3, 0);
        gridPane.add(new Label("Country: "), 4, 0);
        gridPane.add(country, 5, 0);

        dialog.getDialogPane().setContent(gridPane);

        // Request focus on the username field by default.
        Platform.runLater(() -> name.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                arrayList.add(name.getText());
                arrayList.add(address.getText());
                arrayList.add(country.getText());
                return new Pair<>(name.getText(), address.getText());

            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(pair -> {
            System.out.println("name=" + pair.getKey() + ", address=" + pair.getValue());
            System.out.println("name=" + arrayList.get(0)+ ", address=" + arrayList.get(1)+arrayList.get(2));
        });

        Main main = new Main();
        try {
            main.insetData(arrayList.get(0),arrayList.get(1),arrayList.get(2));
            ArrayList<customer> customers = new ArrayList<>();
            try {

                customers = main.getData();
            }
            catch (Exception e)
            {
                System.out.println(e);
            }

            display(customers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
