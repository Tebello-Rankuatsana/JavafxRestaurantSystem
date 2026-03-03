package ranks.restaurant;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    private double total = 0;
    private double drinkPrice = 0;

//    the drink selection
    @FXML
    void selectDrink(ActionEvent event) {

        MenuItem selectedItem = (MenuItem) event.getSource();
        String drink = selectedItem.getText();

        DrinkMenuButton.setText(drink);

        switch (drink) {
            case "Sprite":
                drinkPrice = 15;
                break;
            case "Pepsi":
                drinkPrice = 18;
                break;
            case "Kool Aid":
                drinkPrice = 12;
                break;
            default:
                drinkPrice = 0;
        }

        updateTotal(null);
    }

//    calculating the total
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

        // Drink Price
        total += drinkPrice;

        lblTotalAmount.setText(String.format("%.2f", total));
    }

//    calculating the change
    @FXML
    void calculateChange(ActionEvent event) {

        try {
            double tendered = Double.parseDouble(lblAmountTendered.getText());
            double change = tendered - total;

            if (change < 0) {
                showAlert("Insufficient Amount!");
            } else {

                lblChange.setText(String.format("%.2f", change));

                // Save transaction to file
                saveTransaction(tendered, change);
            }

        } catch (NumberFormatException e) {
            showAlert("Enter valid number!");
        }
    }

//    this is where we are writing to the notepad
    private void saveTransaction(double tendered, double change) {

        try {

            FileWriter writer = new FileWriter("Restaurant_Transactions.txt", true);

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            String time = LocalDateTime.now().format(formatter);

            writer.write("====================================\n");
            writer.write("Transaction Time: " + time + "\n\n");

            // Dinner
            if (CheckBoxChickenBurger.isSelected())
                writer.write("Chicken Burger - R50\n");

            if (CheckBoxBeefBurger.isSelected())
                writer.write("Beef Burger - R60\n");

            if (CheckBoxCheeseBurger.isSelected())
                writer.write("Cheese Burger - R55\n");

            // Dessert
            if (RadioButtonStrawberry.isSelected())
                writer.write("Strawberry Short Cake - R40\n");

            if (RadioButtonChoco.isSelected())
                writer.write("Choco Milk-Shake - R35\n");

            if (RadioButtonVanilla.isSelected())
                writer.write("Vanilla Pound Cake - R30\n");

            // Drink
            if (drinkPrice > 0)
                writer.write(DrinkMenuButton.getText() + " - R" + drinkPrice + "\n");

            writer.write("\nTotal: R" + String.format("%.2f", total) + "\n");
            writer.write("Amount Tendered: R" + tendered + "\n");
            writer.write("Change: R" + String.format("%.2f", change) + "\n");
            writer.write("====================================\n\n");

            writer.close();

        } catch (IOException e) {
            showAlert("Error saving transaction!");
        }
    }

//    reset event
    @FXML
    void resetSystem(ActionEvent event) {

        CheckBoxChickenBurger.setSelected(false);
        CheckBoxBeefBurger.setSelected(false);
        CheckBoxCheeseBurger.setSelected(false);

        dessertGroup.selectToggle(null);

        DrinkMenuButton.setText("Apple Juice");
        drinkPrice = 0;

        lblTotalAmount.clear();
        lblAmountTendered.clear();
        lblChange.clear();

        total = 0;
    }

//   closing the window by pressing the exit button
    @FXML
    void closeWindow(ActionEvent event) {
        Platform.exit();
    }

//    the alert
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.show();
    }
}