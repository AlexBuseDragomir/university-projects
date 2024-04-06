package com.aceucv.fla.controller;

import com.aceucv.fla.model.RegexUtility;
import javafx.scene.control.TextField;

public class Controller {

    public TextField patternField;
    public TextField textField;
    public TextField responseField;

    // what happens when you press the checkButton
    public void handleButtonClick() {
        if(!this.patternField.getText().equals("") && !this.textField.getText().equals("")) {
            boolean result = RegexUtility.doesStringMatchRegex(this.textField.getText(),
                    this.patternField.getText());
            if (result) {
                this.responseField.setText("TRUE");
            } else {
                this.responseField.setText("FALSE");
            }
        } else {
            this.responseField.setText("Fill both fields above!");
        }
    }
}
