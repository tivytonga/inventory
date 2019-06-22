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
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tivinia Tonga
 * @version 14 February 2019
 */
public class ModifyProductController implements Initializable {
    
    @FXML
    private Button searchBtn;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button addBtn;

    @FXML
    private Button delBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private Label modProductLabel;

    @FXML
    private Label idLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label invLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label maxLabel;
    
    @FXML
    private TableView<Part> addProdTbl;

    @FXML
    private TableColumn<Part, Integer> addProdPartIDCol;

    @FXML
    private TableColumn<Part, String> addProdPartNameCol;

    @FXML
    private TableColumn<Part, Integer> addProdPartInventoryCol;

    @FXML
    private TableColumn<Part, Double> addProdPartPriceCol;

    @FXML
    private TableView<Part> prodAssocPartTbl;

    @FXML
    private TableColumn<Part, Integer> assocPartIDCol;

    @FXML
    private TableColumn<Part, String> assocPartName;

    @FXML
    private TableColumn<Part, Integer> assocPartInventory;

    @FXML
    private TableColumn<Part, Double> assocPartPrice;

    @FXML
    private TextField minTextField;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField invTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField maxTextField;

    @FXML
    private Label minLabel;
    
    private Inventory inventory;
    private Product selectedProduct;
    private ObservableList<Part> prodSearchList = FXCollections.observableArrayList();
    private ObservableList<Part> prodAssocPartsList = FXCollections.observableArrayList();
    private ArrayList<Part> assocPartsList;
    private ArrayList<Part> partsList;
    
    public ModifyProductController(Inventory inventory, Product selectedProduct)
    {
        this.inventory = inventory;
        this.selectedProduct = selectedProduct;
        this.assocPartsList = selectedProduct.getAssocPartsList();
    }

    @FXML
    void addModifyProduct(ActionEvent event) {
        addSelectedParts();
    }

    @FXML
    void cancelModifyProduct(ActionEvent event) throws IOException {
        closeWindow();
        mainScreen();
    }

    @FXML
    void deleteModifyProduct(ActionEvent event) {
        deleteSelectedPart();
    }

    @FXML
    void saveModifyProduct(ActionEvent event) throws IOException {
        if (restrictInventory() && restrictPrice())
        {
            // Save any changed Text Fields
            selectedProduct.setMin(Integer.parseInt(minTextField.getText()));
            selectedProduct.setMax(Integer.parseInt(maxTextField.getText()));
            selectedProduct.setProductID(Integer.parseInt(idTextField.getText()));
            selectedProduct.setInStock(Integer.parseInt(invTextField.getText()));
            selectedProduct.setPrice(Double.parseDouble(priceTextField.getText()));
            selectedProduct.setName(nameTextField.getText());

            // Close Window
            closeWindow();

            // Open Main Screen
            mainScreen();
        }
    }

    @FXML
    void searchModifyProduct(ActionEvent event) {
        createSearchTbl();
    }
    
    private void createSearchTbl()
    {
        int search = Integer.parseInt(searchTextField.getText());
        
        Part searchForPart = inventory.lookupPart(search);
        
        addProdPartIDCol.setCellValueFactory(new PropertyValueFactory("partID"));
        addProdPartNameCol.setCellValueFactory(new PropertyValueFactory("name"));
        addProdPartInventoryCol.setCellValueFactory(new PropertyValueFactory("inStock"));
        addProdPartPriceCol.setCellValueFactory(new PropertyValueFactory("price"));
        
        prodSearchList.add(searchForPart);
        
        addProdTbl.setItems(prodSearchList);
        addProdTbl.refresh();

    }
    
    /*
     *  Check if the price of the Selected Product is greater than the cost of the parts
     */
    private boolean restrictPrice()
    {
        // Get Price from Text Field
        double price = Double.parseDouble(priceTextField.getText());
        
        // Total Cost of all Associated Parts
        double totalCost = 0;
        
        for (int i = 0; i < this.assocPartsList.size(); i++)
        {
            totalCost = totalCost + assocPartsList.get(i).getPrice();
        }
        
        // Compare Price of New Product with Total Cost of Parts
        if (price < totalCost)
        {
            Alert alertPart = new Alert(Alert.AlertType.ERROR);
            alertPart.setTitle("Invalid Price");
            alertPart.setContentText("Price must be higher than cost of all Parts. Please update.");
            alertPart.showAndWait();
            return false;
        }
        
        return true;
    }
    
    private void addSelectedParts()
    {        
        for (int i = 0; i < prodSearchList.size(); i++)
        {
            selectedProduct.addAssociatedPart(prodSearchList.get(i));
        }
        
        prodAssocPartsList.clear();
        addProdTbl.getItems().clear();
        prodAssocPartTbl.getItems().clear();
        populateSelectedProduct();
        
    }
    
    private void populateSelectedProduct()
    {
        // Set the fields to the selected product's data
        minTextField.setText(selectedProduct.getMin() + "");
        idTextField.setText(selectedProduct.getProductID() + "");
        nameTextField.setText(selectedProduct.getName());
        invTextField.setText(selectedProduct.getInStock() + "");
        priceTextField.setText(selectedProduct.getPrice() + "");
        maxTextField.setText(selectedProduct.getMax() + "");
        
        // Set columns connection
        assocPartIDCol.setCellValueFactory(new PropertyValueFactory("partID"));
        assocPartName.setCellValueFactory(new PropertyValueFactory("name"));
        assocPartInventory.setCellValueFactory(new PropertyValueFactory("inStock"));
        assocPartPrice.setCellValueFactory(new PropertyValueFactory("price"));
        
        // Display data in Parts Table View
        for (int i = 0; i < assocPartsList.size(); i++)
        {
            prodAssocPartsList.add(assocPartsList.get(i));
        }
        
        prodAssocPartTbl.setItems(prodAssocPartsList);
        prodAssocPartTbl.refresh();
        
    }
    
    /*
     *  Check if Inventory number is in correct range
     */
    private boolean restrictInventory()
    {
        // Inventory is greater than max
        if (Integer.parseInt(invTextField.getText()) > Integer.parseInt(maxTextField.getText()))
        {
            Alert alertPart = new Alert(Alert.AlertType.ERROR);
            alertPart.setTitle("Inventory Too High");
            alertPart.setContentText("Inventory number exceeds maximum. Please update.");
            alertPart.showAndWait();
            return false;
        }
        
        // Inventory is less than min
        if (Integer.parseInt(invTextField.getText()) < Integer.parseInt(minTextField.getText()))
        {
            Alert alertPart = new Alert(Alert.AlertType.ERROR);
            alertPart.setTitle("Inventory Too Low");
            alertPart.setContentText("Inventory number falls short of minimum. Please update.");
            alertPart.showAndWait();
            return false;
        }
        
        return true;
    }
    
    private void deleteSelectedPart()
    {
        Part selectedPart = prodAssocPartTbl.getSelectionModel().getSelectedItem();
        
        selectedProduct.removeAssociatedPart(selectedPart.getPartID());
        
        prodAssocPartsList.clear();
        
        populateSelectedProduct();
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
    
    private void closeWindow()
    {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateSelectedProduct();
    }    
    
}
