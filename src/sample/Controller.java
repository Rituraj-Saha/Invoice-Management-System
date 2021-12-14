package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {


    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    TextField admin_username;
    @FXML
    PasswordField admin_pass;
    @FXML
    Button login_btn;

    public void switchToScene2(ActionEvent event) throws IOException {
        if(admin_username.getText().equals("admin")&&admin_pass.getText().equals("admin")) {
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
        else
        {
            Toast.show("Wrong Username/Password..",this.login_btn);
        }
    }
}
