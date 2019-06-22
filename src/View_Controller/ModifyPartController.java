/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.InhousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tivinia Tonga
 * @version 14 February 2019
 */
public class ModifyPartController implements Initializable {
    
     @FXML
    private ColumnConstraints addPartGridPane;

    @FXML
    private Label modPartTitle;

    @FXML
    private RadioButton inhouseRadioBtn;

    @FXML
    private ToggleGroup opt;

    @FXML
    private RadioButton outsourcedRadioBtn;

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
    private Label minLabel;

    @FXML
    private Label machineLabel;

    @FXML
    private TextField maxTextField;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField invTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField minTextField;

    @FXML
    private TextField machineTextField;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;
    
    private Inventory inventory;
    private Part partToUpdate;
    
    // Needs to take a selected Part as a parameter in order to update that part
    public ModifyPartController(Inventory inventory, Part partToUpdate)
    {
        this.inventory = inventory;
        this.partToUpdate = partToUpdate;
    }

    @FXML
    void cancelModifyPart(ActionEvent event) throws IOException {
        closeWindow();
        mainScreen();
    }

    @FXML
    void inhouseOption(ActionEvent event) {
        machineLabel.setText("Machine ID");
        machineTextField.setText("Mach ID");
    }

    @FXML
    void outsourcedOption(ActionEvent event) {
        machineLabel.setText("Company Name");
        machineTextField.setText("Comp Nm");
    }

    @FXML
    void saveModifyPart(ActionEvent event) throws IOException {
        if (restrictInventory())
        {
            int partID = partToUpdate.getPartID();
            int max = Integer.parseInt(maxTextField.getText());
            String name = nameTextField.getText();
            int inStock = Integer.parseInt(invTextField.getText());
            double price = Double.parseDouble(priceTextField.getText());
            int min = Integer.parseInt(minTextField.getText());

            if (inhouseRadioBtn.isSelected())
            {
                Part temp = new InhousePart(partID, name, price, inStock, min, max, Integer.parseInt(machineTextField.getText()));
                inventory.updatePart(temp);
            }
            else
            {
                Part temp = new OutsourcedPart(partID, name, price, inStock, min, max, machineTextField.getText());
                inventory.updatePart(temp);
            }

            closeWindow();
            mainScreen();
        }
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
    
    private void populateInfo()
    {
        idTextField.setText(partToUpdate.getPartID() + "");
        nameTextField.setText(partToUpdate.getName());
        invTextField.setText(partToUpdate.getInStock() + "");
        priceTextField.setText(partToUpdate.getPrice() + "");
        minTextField.setText(partToUpdate.getMin() + "");
        maxTextField.setText(partToUpdate.getMax() + "");
        
        if (partToUpdate instanceof InhousePart)
        {
            InhousePart temp = (InhousePart) partToUpdate;
            machineTextField.setText(temp.getMachineID() + "");
            inhouseRadioBtn.setSelected(true);
        }
        else
        {
            OutsourcedPart temp = (OutsourcedPart) partToUpdate;
            machineTextField.setText(temp.getCompanyName() + "");
            outsourcedRadioBtn.setSelected(true);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Populate screen with selected Part info
        populateInfo();
        
    }    
    
}
