package ranks.restaurant;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.util.Duration;

public class LoginController {

    @FXML
    private StackPane root;

    @FXML
    private ImageView backgroundImg;

    @FXML
    private VBox leftVBox;

    @FXML
    private ImageView logoImage;

    @FXML
    private VBox rightVBox;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    public void initialize() {

//        zooming background slowly
        backgroundImg.fitWidthProperty().bind(root.widthProperty());
        backgroundImg.fitHeightProperty().bind(root.heightProperty());
        backgroundImg.setPreserveRatio(false);

        ScaleTransition bgZoom = new ScaleTransition(Duration.seconds(20), backgroundImg);
        bgZoom.setFromX(1.0);
        bgZoom.setFromY(1.0);
        bgZoom.setToX(1.05);
        bgZoom.setToY(1.05);
        bgZoom.setAutoReverse(true);
        bgZoom.setCycleCount(Animation.INDEFINITE);
        bgZoom.play();

//        glass entrance
        Node loginCard = rightVBox.getParent(); // HBox containing leftVBox + rightVBox
        loginCard.setOpacity(0);
        loginCard.setScaleX(0.9);
        loginCard.setScaleY(0.9);

        FadeTransition cardFade = new FadeTransition(Duration.millis(500), loginCard);
        cardFade.setToValue(1);

        ScaleTransition cardScale = new ScaleTransition(Duration.millis(500), loginCard);
        cardScale.setToX(1);
        cardScale.setToY(1);

        new ParallelTransition(cardFade, cardScale).play();

//        logo animation, scaling
        logoImage.setOpacity(0);
        logoImage.setScaleX(0.8);
        logoImage.setScaleY(0.8);

        FadeTransition logoFade = new FadeTransition(Duration.millis(600), logoImage);
        logoFade.setToValue(1);
        logoFade.setDelay(Duration.millis(400));

        ScaleTransition logoScale = new ScaleTransition(Duration.millis(600), logoImage);
        logoScale.setToX(1);
        logoScale.setToY(1);
        logoScale.setDelay(Duration.millis(400));

        new ParallelTransition(logoFade, logoScale).play();

//        left vbox children fading
        for (Node node : leftVBox.getChildren()) {
            if (node != logoImage) {
                node.setOpacity(0);
                FadeTransition fade = new FadeTransition(Duration.millis(700), node);
                fade.setToValue(1);
                fade.setDelay(Duration.millis(800)); // after logo
                fade.play();
            }
        }

//        login button with hover animation
        btnLogin.setOnMouseEntered(e -> scaleButtonHover(btnLogin, 1.05));
        btnLogin.setOnMouseExited(e -> scaleButtonHover(btnLogin, 1.0));
    }

//  scale button on hover
    private void scaleButtonHover(Button btn, double scale) {
        ScaleTransition st = new ScaleTransition(Duration.millis(150), btn);
        st.setToX(scale);
        st.setToY(scale);
        st.play();
    }

//    shake animation for incorrect credentials
    public void shake(Node node) {
        TranslateTransition shake = new TranslateTransition(Duration.millis(50), node);
        shake.setFromX(-10);
        shake.setToX(10);
        shake.setCycleCount(6);
        shake.setAutoReverse(true);
        shake.play();
    }
}