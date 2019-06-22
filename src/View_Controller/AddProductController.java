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
public class AddProductController implements Initializable {
    
    
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
    private Label addProductLabel;

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
    private TableView<Part> searchTbl;

    @FXML
    private TableColumn<Part, Integer> searchPartIDCol;

    @FXML
    private TableColumn<Part, String> searchPartNameCol;

    @FXML
    private TableColumn<Part, Integer> searchPartInventoryCol;

    @FXML
    private TableColumn<Part, Double> searchPartPriceCol;

    @FXML
    private TableView<Part> assocPartsTbl;

    @FXML
    private TableColumn<Part, Integer> assocPartIDCol;

    @FXML
    private TableColumn<Part, String> assocPartNameCol;

    @FXML
    private TableColumn<Part, Integer> assocPartInventoryCol;

    @FXML
    private TableColumn<Part, Double> assocPartPriceCol;

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
    private Product newProduct;
    private boolean productCreated; 
    
    public AddProductController(Inventory inventory)
    {
        this.inventory = inventory;
        partsList = inventory.getPartsList();
        productCreated = false;
    }

    @FXML
    void addNewProduct(ActionEvent event) {
        // Adds the parts in the Search Table to the AssociatedParts list of the New Product
        addToAssocPartsList();
    }
    
    private void createProduct()
    {
        int min = Integer.parseInt(minTextField.getText());
        int ID = inventory.getProdList().size() + 1;
        String name = nameTextField.getText();
        int inStock = Integer.parseInt(invTextField.getText());
        double price = Double.parseDouble(priceTextField.getText());
        int max = Integer.parseInt(maxTextField.getText());
        
        newProduct = new Product(ID, name, price, inStock, min, max);
        productCreated = true;
    }

    /*
     *  Closes the current AddProduct window without saving any changes
     */
    @FXML
    void cancelAddProduct(ActionEvent event) throws IOException {
        closeWindow();
        mainScreen();
    }

    @FXML
    void delAddProduct(ActionEvent event) {
        deleteSelectedPart();
    }

    @FXML
    void saveAddProduct(ActionEvent event) throws IOException {
        
        if (restrictInventory() && restrictPrice())
        {
            createProduct();
            inventory.addProduct(newProduct);
            closeWindow();
            mainScreen();
        }
    }

    /*
     *  Displays the Search Results in the Search Table (Top Table)
     */
    @FXML
    void searchAddProduct(ActionEvent event) {
        if (!productCreated)
            createProduct();
        createSearchTbl();
    }
    
    /*
     *  Creates the Search Table based on the Search TextField 
     */
    private void createSearchTbl()
    {
        int search = Integer.parseInt(searchTextField.getText());
        
        Part searchForPart = inventory.lookupPart(search);
        
        searchPartIDCol.setCellValueFactory(new PropertyValueFactory("partID"));
        searchPartNameCol.setCellValueFactory(new PropertyValueFactory("name"));
        searchPartInventoryCol.setCellValueFactory(new PropertyValueFactory("inStock"));
        searchPartPriceCol.setCellValueFactory(new PropertyValueFactory("price"));
        
        prodSearchList.add(searchForPart);
        
        searchTbl.setItems(prodSearchList);
        searchTbl.refresh();
    }
    
    /*
     *  Adds the list of Parts in the Search Table to the Associated Parts Table
     *   so that each part is associated with the New Product
     */
    private void addToAssocPartsList()
    {
        // Loop through Search Table and add to Associated Parts list
        for (int i = 0; i < prodSearchList.size(); i++)
        {
            newProduct.addAssociatedPart(prodSearchList.get(i));
        }
        
        createAssocPartsTbl();
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
    
    /*
     *  Check if the price of the New Product is greater than the cost of the parts
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
    
    /*
     *  Create the Associated Parts Table based on the New Product's Associated Parts   
     */
    private void createAssocPartsTbl()
    {
        prodAssocPartsList.clear();
        
        assocPartIDCol.setCellValueFactory(new PropertyValueFactory("partID"));
        assocPartNameCol.setCellValueFactory(new PropertyValueFactory("name"));
        assocPartInventoryCol.setCellValueFactory(new PropertyValueFactory("inStock"));
        assocPartPriceCol.setCellValueFactory(new PropertyValueFactory("price"));
        
        assocPartsList = newProduct.getAssocPartsList();
        
        for (int i = 0; i < assocPartsList.size(); i++)
        {
            prodAssocPartsList.add(assocPartsList.get(i));
        }
        
        assocPartsTbl.setItems(prodAssocPartsList);
        assocPartsTbl.refresh();
    }
    
    private void deleteSelectedPart()
    {
        Part selectedPart = assocPartsTbl.getSelectionModel().getSelectedItem();
        
        newProduct.removeAssociatedPart(selectedPart.getPartID());
        
        createAssocPartsTbl();
    }
    
    /*
     *  Opens the Main Screen with the Updated Inventory
     */
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
    
    /*
     * Closes current AddProduct Window
     */
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
        
    }    
    
}
