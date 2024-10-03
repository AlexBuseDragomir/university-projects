package manager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController extends ShowScreenController {

    @FXML
    private Label profileButton;

    @FXML
    private Label logoutButton;

    @FXML
    public void logoutUser() {

        showScreen(logoutButton, "/views/startup.fxml");

    }

    @FXML
    public void gotoAccountsScreen() {

        showScreen(profileButton, "/views/accounts.fxml");

    }

    @FXML
    public void gotoPayScreen() {

        showScreen(profileButton, "/views/pay.fxml");

    }

    @FXML
    public void gotoTransferScreen() {

        showScreen(profileButton, "/views/transfer.fxml");

    }

    @FXML
    public void gotoProfileScreen() {

        showScreen(profileButton, "/views/profile.fxml");

    }
}
