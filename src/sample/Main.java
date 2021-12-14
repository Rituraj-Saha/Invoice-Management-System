package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class Main extends Application {
    public static Connection getConnection(){
        try {
            String drivers = "com.mysql.cj.jdbc.Driver";
            String databaseurl="jdbc:mysql://localhost:3306/customer_tech";
            String username ="root";
            String password = "";
            Class.forName(drivers);
            Connection connection = DriverManager.getConnection(databaseurl,username,password);
            System.out.println("Database Connected");
            return connection;
        }
        catch (Exception e)
        {
            System.out.println(""+e);
        }
        return null;
    }
    @Override
    public void start(Stage primaryStage) throws Exception{



        //getData();
       // insetData();

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Invoice Creator");
        primaryStage.setScene(new Scene(root));
        Image icon = new Image("icon.jpg");
        primaryStage.getIcons().add(icon);

        primaryStage.show();
//        primaryStage.setOnCloseRequest(windowEvent -> {
//            try {
//                windowEvent.consume();
//                Logout(primaryStage);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });

    }
    public void Logout(Stage stage )throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Close !!");
        alert.setHeaderText("Are You Sure ?");
        alert.setContentText("Do you really want to close?");
        if(alert.showAndWait().get() == ButtonType.OK)
        {

            stage.close();
        }



    }

    public static void main(String[] args) {
        launch(args);

    }

    public ArrayList<customer> getData() throws Exception
    {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("Select * from customer");
        ArrayList<customer> customers = new ArrayList<>();
        while (rs.next())
        {
            System.out.println(rs.getInt(1)+" "+
                    rs.getString(2)+" "+
                    rs.getString(3)+" "+
                    rs.getString(4));
            customers.add(new customer(rs.getInt(1), rs.getString(2), rs.getString(3),  rs.getString(4)));
        }
        connection.close();
        return customers;
    }
    public void insetData(String name,String adress,String country) throws Exception
    {
        Connection connection = getConnection();
        try {
            Statement statement2 = connection.createStatement();
            String query1 = "INSERT INTO customer (Name,Address,Country)" + "VALUES "+"("+"'"+name+"'"+", "+"'"+adress+"'"+", '"+country+"'"+")";
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
}
