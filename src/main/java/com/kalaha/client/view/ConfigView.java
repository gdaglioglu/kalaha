package com.kalaha.client.view;

import com.kalaha.client.dto.GameConfig;
import com.kalaha.client.service.RestClientService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * View to capture the name of the players and configuration parameters for the game.
 */
@PageTitle("Before you start!")
@Route("")
@Theme(value = Lumo.class)
public class ConfigView extends VerticalLayout {

    /**
     * Constant for width of the form.
     */
    public static final String PX_250 = "250px";

    /**
     * Constructs configuration view page.
     * @param service the client service to initiate REST calls.
     */
    public ConfigView(@Autowired RestClientService service) {

        H3 gameConfiguration = new H3("Game configuration");

        TextField firstPlayersNameField = new TextField();
        firstPlayersNameField.setMaxLength(100);
        firstPlayersNameField.setWidthFull();

        TextField secondPlayersNameField = new TextField();
        secondPlayersNameField.setMaxLength(100);
        secondPlayersNameField.setWidthFull();

        IntegerField numberOfPitsPerPlayerField = new IntegerField();
        numberOfPitsPerPlayerField.setWidthFull();
        numberOfPitsPerPlayerField.setMin(1);
        numberOfPitsPerPlayerField.setMax(10);
        numberOfPitsPerPlayerField.setValue(6);
        numberOfPitsPerPlayerField.setHasControls(true);

        IntegerField numberOfStonesPerPitField = new IntegerField();
        numberOfStonesPerPitField.setWidthFull();
        numberOfStonesPerPitField.setMin(1);
        numberOfStonesPerPitField.setMax(10);
        numberOfStonesPerPitField.setValue(6);
        numberOfStonesPerPitField.setHasControls(true);

        Button submitButton = new Button("Start", e -> {

            String firstPlayersName = firstPlayersNameField.getValue();
            String secondPlayersName = secondPlayersNameField.getValue();

            if(firstPlayersName.isEmpty() || secondPlayersName.isEmpty()){
                Notification notification = Notification.show("Enter user names to start!");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }
            GameConfig gameConfig = new GameConfig(firstPlayersName, secondPlayersName,
                    numberOfPitsPerPlayerField.getValue(), numberOfStonesPerPitField.getValue());

            service.createGame(gameConfig);

            UI.getCurrent().navigate(GameView.class);
        });
        submitButton.addClickShortcut(Key.ENTER);
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitButton.setWidth(PX_250);

        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(firstPlayersNameField, "Player 1:");
        formLayout.addFormItem(secondPlayersNameField, "Player 2:");
        formLayout.addFormItem(numberOfPitsPerPlayerField, "Number of pits per player:");
        formLayout.addFormItem(numberOfStonesPerPitField, "Number of stones per pit:");
        formLayout.setWidth(PX_250);

        add(gameConfiguration, formLayout, submitButton);
        setHorizontalComponentAlignment(Alignment.CENTER, gameConfiguration, formLayout, submitButton);
    }
}