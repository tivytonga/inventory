/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tivinia Tonga
 * @version 13 February 2019
 */
public class MainScreenController implements Initializable {
    
    @FXML
    private Pane prodPane;

    @FXML
    private Label productsLabel;

    @FXML
    private Button productsSearchBtn;

    @FXML
    private TextField searchProductsTextField;

    @FXML
    private Button prodAddBtn;

    @FXML
    private Button prodModBtn;

    @FXML
    private Button prodDelBtn;

    @FXML
    private TableView<Product> prodMainTbl;

    @FXML
    private TableColumn<Product, Integer> productIDColumn;

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TableColumn<Product, Integer> productInventoryColumn;

    @FXML
    private TableColumn<Product, Double> productPriceColumn;

    @FXML
    private Pane partsPane;

    @FXML
    private Button partsModBtn;

    @FXML
    private Label partsLabel;

    @FXML
    private Button partsSearchBtn;

    @FXML
    private TextField searchPartsTextField;

    @FXML
    private TableView<Part> partsMainTbl;

    @FXML
    private TableColumn<Part, Integer> partIDColumn;

    @FXML
    private TableColumn<Part, String> partNameColumn;

    @FXML
    private TableColumn<Part, Integer> partInventoryColumn;

    @FXML
    private TableColumn<Part, Double> partPriceColumn;

    @FXML
    private Button partsAddBtn;

    @FXML
    private Button partsDelBtn;

    @FXML
    private Label mainTitle;

    @FXML
    private Button mainExitBtn;
    
    private Inventory inventory;
    private ObservableList<Part> partList = FXCollections.observableArrayList();
    private ObservableList<Part> partSearchList = FXCollections.observableArrayList();
    private ArrayList<Part> partsArrayList;
    private ObservableList<Product> productList = FXCollections.observableArrayList();
    private ObservableList<Product> productSearchList = FXCollections.observableArrayList();
    private ArrayList<Product> productsArrayList;
    
    public MainScreenController(Inventory inventory)
    {
        this.inventory = inventory;
        partsArrayList = inventory.getPartsList();
        productsArrayList = inventory.getProdList();
    }
    
    private void createPartsTbl()
    {
        partIDColumn.setCellValueFactory(new PropertyValueFactory("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory("inStock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory("price"));
    
        for (int i = 0; i < partsArrayList.size(); i++)
        {
            partList.add(partsArrayList.get(i));
        }
        
        partsMainTbl.setItems(partList);
        partsMainTbl.refresh();
    }
    
    private void createPartSearchTbl()
    {
        partSearchList.clear();
        
        partIDColumn.setCellValueFactory(new PropertyValueFactory("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory("inStock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory("price"));
        
        int search = Integer.parseInt(searchPartsTextField.getText());
    
        partSearchList.add(inventory.lookupPart(search));
        
        
        partsMainTbl.setItems(partSearchList);
        partsMainTbl.refresh();
    }
    
    private void createProdTbl()
    {
         productIDColumn.setCellValueFactory(new PropertyValueFactory("productID"));
         productNameColumn.setCellValueFactory(new PropertyValueFactory("name"));
         productInventoryColumn.setCellValueFactory(new PropertyValueFactory("inStock"));
         productPriceColumn.setCellValueFactory(new PropertyValueFactory("price"));
         
         for (int i = 0; i < productsArrayList.size(); i++)
        {
            productList.add(productsArrayList.get(i));
        }
        
        prodMainTbl.setItems(productList);
        prodMainTbl.refresh();
    }
    
    private void createProductSearchTbl()
    {
        productSearchList.clear();
        
        partIDColumn.setCellValueFactory(new PropertyValueFactory("productID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory("inStock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory("price"));
        
        int search = Integer.parseInt(searchProductsTextField.getText());
    
        productSearchList.add(inventory.lookupProduct(search));
        
        
        prodMainTbl.setItems(productSearchList);
        prodMainTbl.refresh();
    }

    @FXML
    void addParts(ActionEvent event) throws IOException {
        closeWindow();
                
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/AddPart.fxml"));
        View_Controller.AddPartController controller = new View_Controller.AddPartController(inventory);
        loader.setController(controller);
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void addProducts(ActionEvent event) throws IOException {
        closeWindow();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/AddProduct.fxml"));
        View_Controller.AddProductController controller = new View_Controller.AddProductController(inventory);
        loader.setController(controller);
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void deleteParts(ActionEvent event) {
        deleteSelectedPart();
    }
    
    private void deleteSelectedPart()
    {
        Part selectedPart = partsMainTbl.getSelectionModel().getSelectedItem();
        
        // Remove selected part from Inventory
        inventory.deletePart(selectedPart.getPartID());
        
        // TODO: Update Associated Products???
           // Loop through each product and remove the selected part
        ArrayList<Product> tempProd = inventory.getProdList();
        for (int i = 0; i < tempProd.size(); i++)
        {
            tempProd.get(i).removeAssociatedPart(selectedPart.getPartID());
        }
        
        // Reset the partsArrayList to the updated Inventory Parts List
        partsArrayList = inventory.getPartsList();
        
        // Empty the Parts Table
        partsMainTbl.getItems().clear();
        
        // Refresh the Parts Table
        createPartsTbl();
    }

    @FXML
    void deleteProducts(ActionEvent event) {
        deleteSelectedProduct();
    }
    
    private void deleteSelectedProduct()
    {
        Product selectedProduct = prodMainTbl.getSelectionModel().getSelectedItem();
        
        // Remove selected product from Inventory
        inventory.removeProduct(selectedProduct.getProductID());
        
        // Reset the prodArrayList to the updated Inventory Products List
        productsArrayList = inventory.getProdList();
        
        // Empty the Products Table
        prodMainTbl.getItems().clear();
        
        // Refresh the Product Table
        createProdTbl();
    }

    @FXML
    void exitInventory(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void modifyParts(ActionEvent event) throws IOException{
        try{
        
            Part selectedPart = partsMainTbl.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/ModifyPart.fxml"));
            View_Controller.ModifyPartController controller = new View_Controller.ModifyPartController(inventory, selectedPart);
            loader.setController(controller);

            closeWindow();

            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            }
        catch(Exception e)
        {
            Alert alertPart = new Alert(AlertType.ERROR);
            alertPart.setTitle("Error");
            alertPart.setContentText("No Part selected. Make a selection to modify.");
            alertPart.showAndWait();
            closeWindow();
            mainScreen();
        }
    }

    @FXML
    void modifyProducts(ActionEvent event) throws IOException{
        try{
            Product selectedProduct = prodMainTbl.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/ModifyProduct.fxml"));
            View_Controller.ModifyProductController controller = new View_Controller.ModifyProductController(inventory, selectedProduct);
            loader.setController(controller);

            closeWindow();

            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            }
        catch(Exception e)
        {
            Alert alertProd = new Alert(AlertType.ERROR);
            alertProd.setTitle("Error");
            alertProd.setContentText("No Product selected. Make a selection to modify.");
            alertProd.showAndWait();
            closeWindow();
            mainScreen();
        }
    }

    @FXML
    void searchParts(ActionEvent event) {
        createPartSearchTbl();
    }

    @FXML
    void searchProducts(ActionEvent event) {
        createProductSearchTbl();
    }
    
    private void closeWindow()
    {
        Stage main = (Stage) mainTitle.getScene().getWindow();
        main.close();
    }
    
    private void mainScreen() throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
        View_Controller.MainScreenController controller = new View_Controller.MainScreenController(inventory);
        loader.setController(controller);
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createPartsTbl();
        createProdTbl();
    }    
    
}
