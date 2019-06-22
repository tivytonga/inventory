/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Model.InhousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Part;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Driver for the program
 * @author Tivinia Tonga
 * @version 13 February 2019
 */
public class InventorySystem extends Application{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Inventory inventory = new Inventory();
        populateData(inventory);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
        View_Controller.MainScreenController controller = new View_Controller.MainScreenController(inventory);
        loader.setController(controller);
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /*
     *  Populates the Inventory parameter with sample data
     */
    void populateData(Inventory inventory)
    {
        // In-House Parts
        Part screen = new InhousePart(1, "screen", 39.99, 20, 5, 100, 1001);
        Part keyboard = new InhousePart(2, "keyboard", 19.99, 30, 10, 200, 1002);
        Part mouse = new InhousePart(3, "mouse", 9.99, 50, 30, 300, 1003);
        inventory.addPart(screen);
        inventory.addPart(keyboard);
        inventory.addPart(mouse);
        
        // Outsourced Parts
        Part motherboard = new OutsourcedPart(4, "motherboard", 39.99, 20, 5, 50, "Company A");
        Part tires = new OutsourcedPart(5, "tires", 19.99, 30, 20, 200, "Company B");
        Part bumper = new OutsourcedPart(6, "bumper", 9.99, 50, 5, 100, "Company C");
        inventory.addPart(motherboard);
        inventory.addPart(tires);
        inventory.addPart(bumper);
        
        // Products
        Product computer = new Product(1, "computer", 899.99, 20, 5, 25);
        Product car = new Product(2, "car", 2499.95, 10, 15, 30);
        computer.addAssociatedPart(screen);
        computer.addAssociatedPart(keyboard);
        computer.addAssociatedPart(mouse);
        computer.addAssociatedPart(motherboard);
        inventory.addProduct(computer);
        car.addAssociatedPart(tires);
        car.addAssociatedPart(bumper);
        inventory.addProduct(car);
        
    }
    
}
