package com.kalaha.client.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

/**
 * View to capture the name of the players and configuration parameters regarding the game.
 */
@Route
public class ConfigView extends VerticalLayout {

    /**
     * TODO: This is just a template for now, logic needs to be written.
     */
    public ConfigView() {

        TextField textField = new TextField("Your name");
        textField.addThemeName("bordered");

        Button button = new Button("Say hello", e -> Notification.show("Hello Player"));
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(textField, button);
    }
}