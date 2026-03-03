package ranks.restaurant;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;

public class Menu {

    @FXML
    private MenuButton DrinkMenuButton;

    @FXML
    private Button btnChange;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnTotalAmount;

    @FXML
    private TextField lblAmountTendered;

    @FXML
    private TextField lblChange;

    @FXML
    private TextField lblTotalAmount;

    @FXML
    void closeWindow(ActionEvent event) {

    }

}
