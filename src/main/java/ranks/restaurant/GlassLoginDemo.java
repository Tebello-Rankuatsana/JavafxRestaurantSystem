package ranks.restaurant;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GlassLoginDemo extends Application {

    private boolean fakeLoginSuccess = false; // toggle to test success/failure

    @Override
    public void start(Stage stage) {

        StackPane root = new StackPane();
//        testing pull request

//        background image
        ImageView background = new ImageView(
                new Image("https://images.unsplash.com/photo-1550547660-d9450f859349")
        );
        background.setFitWidth(1400);
        background.setFitHeight(900);
        background.setPreserveRatio(false);

        background.setEffect(new GaussianBlur(25));

        // Slow zoom animation
        ScaleTransition bgZoom = new ScaleTransition(Duration.seconds(20), background);
        bgZoom.setFromX(1);
        bgZoom.setFromY(1);
        bgZoom.setToX(1.05);
        bgZoom.setToY(1.05);
        bgZoom.setAutoReverse(true);
        bgZoom.setCycleCount(Animation.INDEFINITE);
        bgZoom.play();

        // Dark overlay
        Region overlay = new Region();
        overlay.setStyle("-fx-background-color: rgba(0,0,0,0.35);");
        overlay.prefWidthProperty().bind(root.widthProperty());
        overlay.prefHeightProperty().bind(root.heightProperty());

// glass card
        HBox card = new HBox();
        card.setPrefSize(800, 450);
        card.setAlignment(Pos.CENTER);

        card.setStyle("""
                -fx-background-color: rgba(255,255,255,0.15);
                -fx-background-radius: 20;
                -fx-border-radius: 20;
                -fx-border-color: rgba(255,255,255,0.3);
                -fx-border-width: 1;
                """);

        card.setEffect(new GaussianBlur(0)); // keeps clean

        // -------------------------
        // LEFT PANEL (Brand)
        // -------------------------
        VBox leftPanel = new VBox(20);
        leftPanel.setAlignment(Pos.CENTER);
        leftPanel.setPrefWidth(350);

        Label logo = new Label("🍔");
        logo.setFont(Font.font(60));
        logo.setTextFill(Color.WHITE);

        Label title = new Label("FASTBITE");
        title.setFont(Font.font("Arial", 28));
        title.setTextFill(Color.WHITE);

        Label slogan = new Label("Fresh. Fast. Fire.");
        slogan.setTextFill(Color.WHITE);

        leftPanel.getChildren().addAll(logo, title, slogan);

        // -------------------------
        // RIGHT PANEL (Login Form)
        // -------------------------
        VBox rightPanel = new VBox(15);
        rightPanel.setAlignment(Pos.CENTER);
        rightPanel.setPadding(new Insets(40));

        TextField username = new TextField();
        username.setPromptText("Username");

        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        CheckBox remember = new CheckBox("Remember me");
        remember.setTextFill(Color.WHITE);

        Button loginBtn = new Button("LOGIN");
        loginBtn.setPrefWidth(200);

        Label errorLabel = new Label("Invalid credentials");
        errorLabel.setTextFill(Color.RED);
        errorLabel.setOpacity(0);

        styleGlassField(username);
        styleGlassField(password);

        loginBtn.setStyle("""
                -fx-background-color: #FF5722;
                -fx-text-fill: white;
                -fx-background-radius: 25;
                -fx-font-weight: bold;
                """);

        rightPanel.getChildren().addAll(username, password, remember, loginBtn, errorLabel);

        card.getChildren().addAll(leftPanel, rightPanel);

        root.getChildren().addAll(background, overlay, card);

        Scene scene = new Scene(root, 1200, 800);

        stage.setTitle("FastBite Login");
        stage.setScene(scene);
        stage.show();

        // -------------------------
        // CARD ENTRANCE ANIMATION
        // -------------------------
        card.setOpacity(0);
        card.setScaleX(0.9);
        card.setScaleY(0.9);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), card);
        fadeIn.setToValue(1);

        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(500), card);
        scaleIn.setToX(1);
        scaleIn.setToY(1);

        ParallelTransition entrance = new ParallelTransition(fadeIn, scaleIn);
        entrance.play();

        // -------------------------
        // LOGO FADE ANIMATION
        // -------------------------
        logo.setOpacity(0);
        FadeTransition logoFade = new FadeTransition(Duration.millis(600), logo);
        logoFade.setToValue(1);
        logoFade.setDelay(Duration.millis(300));
        logoFade.play();

        // -------------------------
        // BUTTON HOVER EFFECT
        // -------------------------
        loginBtn.setOnMouseEntered(e -> scaleNode(loginBtn, 1.05));
        loginBtn.setOnMouseExited(e -> scaleNode(loginBtn, 1));

        // -------------------------
        // LOGIN ACTION
        // -------------------------
        loginBtn.setOnAction(e -> {

            loginBtn.setDisable(true);
            username.setDisable(true);
            password.setDisable(true);

            loginBtn.setText("Authenticating...");

            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event -> {

                if (fakeLoginSuccess) {
                    fadeOutScene(card);
                } else {
                    shake(card);
                    errorLabel.setOpacity(1);
                    loginBtn.setText("LOGIN");
                    loginBtn.setDisable(false);
                    username.setDisable(false);
                    password.setDisable(false);
                }
            });
            pause.play();
        });
    }

    // -------------------------
    // STYLE TEXTFIELDS
    // -------------------------
    private void styleGlassField(TextField field) {
        field.setStyle("""
                -fx-background-color: rgba(255,255,255,0.25);
                -fx-text-fill: white;
                -fx-prompt-text-fill: rgba(255,255,255,0.7);
                -fx-background-radius: 20;
                """);

        field.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                field.setStyle("""
                        -fx-background-color: rgba(255,255,255,0.35);
                        -fx-border-color: #FF5722;
                        -fx-border-radius: 20;
                        -fx-text-fill: white;
                        """);
                scaleNode(field, 1.02);
            } else {
                styleGlassField(field);
                scaleNode(field, 1);
            }
        });
    }

    // -------------------------
    // SCALE HELPER
    // -------------------------
    private void scaleNode(Node node, double scale) {
        ScaleTransition st = new ScaleTransition(Duration.millis(150), node);
        st.setToX(scale);
        st.setToY(scale);
        st.play();
    }

    // -------------------------
    // SHAKE ANIMATION
    // -------------------------
    private void shake(Node node) {
        TranslateTransition shake = new TranslateTransition(Duration.millis(50), node);
        shake.setFromX(-10);
        shake.setToX(10);
        shake.setCycleCount(6);
        shake.setAutoReverse(true);
        shake.play();
    }

    // -------------------------
    // SUCCESS FADE OUT
    // -------------------------
    private void fadeOutScene(Node node) {
        FadeTransition fade = new FadeTransition(Duration.millis(600), node);
        fade.setToValue(0);
        fade.play();
    }

    public static void main(String[] args) {
        launch();
    }
}