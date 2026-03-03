package ranks.restaurant;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Menu {

    @FXML private CheckBox CheckBoxBeefBurger;
    @FXML private CheckBox CheckBoxCheeseBurger;
    @FXML private CheckBox CheckBoxChickenBurger;

    @FXML private RadioButton RadioButtonChoco;
    @FXML private RadioButton RadioButtonStrawberry;
    @FXML private RadioButton RadioButtonVanilla;

    @FXML private ToggleGroup dessertGroup;

    @FXML private MenuButton DrinkMenuButton;

    @FXML private Button btnChange;
    @FXML private Button btnExit;
    @FXML private Button btnReset;
    @FXML private Button btnTotalAmount;

    @FXML private TextField lblAmountTendered;
    @FXML private TextField lblChange;
    @FXML private TextField lblTotalAmount;

    double total = 0;

//    update tool amount
    @FXML
    void updateTotal(ActionEvent event) {

        total = 0;

        // Dinner Prices
        if (CheckBoxChickenBurger.isSelected()) total += 50;
        if (CheckBoxBeefBurger.isSelected()) total += 60;
        if (CheckBoxCheeseBurger.isSelected()) total += 55;

        // Dessert Prices
        if (RadioButtonStrawberry.isSelected()) total += 40;
        if (RadioButtonChoco.isSelected()) total += 35;
        if (RadioButtonVanilla.isSelected()) total += 30;

        lblTotalAmount.setText(String.valueOf(total));
    }

//    calculating change method
    @FXML
    void calculateChange(ActionEvent event) {

        try {
            double tendered = Double.parseDouble(lblAmountTendered.getText());
            double change = tendered - total;

            if (change < 0) {
                showAlert("Insufficient Amount!");
            } else {
                lblChange.setText(String.valueOf(change));
            }

        } catch (NumberFormatException e) {
            showAlert("Enter valid number!");
        }
    }

//    reseting
    @FXML
    void resetSystem(ActionEvent event) {

        CheckBoxChickenBurger.setSelected(false);
        CheckBoxBeefBurger.setSelected(false);
        CheckBoxCheeseBurger.setSelected(false);

        dessertGroup.selectToggle(null);

        lblTotalAmount.clear();
        lblAmountTendered.clear();
        lblChange.clear();

        total = 0;
    }

//    closing the window
    @FXML
    void closeWindow(ActionEvent event) {
        Platform.exit();
    }

//    the alert method
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.show();
    }
}