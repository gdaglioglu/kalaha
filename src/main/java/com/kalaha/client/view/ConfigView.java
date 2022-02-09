package com.kalaha.client.view;

import com.kalaha.client.dto.GameConfig;
import com.kalaha.client.service.RestClientService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
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
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.net.URI;

/**
 * View to capture the name of the players and configuration parameters for the game.
 */
@PageTitle("Before you start!")
@Route("")
@Slf4j
@Theme(value = Lumo.class)
public class ConfigView extends VerticalLayout {

    /**
     * Constant for width of the form.
     */
    public static final String PX_250 = "250px";

    /**
     * The game endpoint
     */
    @Value("${server.kalaha.endpoint}")
    private String endPoint;

    /**
     * Constructs configuration view page.
     *
     * @param service the client service to initiate REST calls.
     */
    public ConfigView(@Autowired RestClientService service) {

        H3 gameConfiguration = new H3("Game configuration");

        TextField firstPlayersNameField = getPlayersNameField();
        TextField secondPlayersNameField = getPlayersNameField();

        IntegerField numberOfPitsPerPlayerField = getIntegerField();
        IntegerField numberOfStonesPerPitField = getIntegerField();

        Button submitButton = new Button("Start", getStartButtonListener(service, firstPlayersNameField, secondPlayersNameField, numberOfPitsPerPlayerField, numberOfStonesPerPitField));
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

        log.debug("Config view initialized");
    }

    /**
     * Gets an {@link IntegerField} after setting common attributes.
     *
     * @return the customised integer field.
     */
    private IntegerField getIntegerField() {
        IntegerField integerField = new IntegerField();
        integerField.setWidthFull();
        integerField.setMin(1);
        integerField.setMax(10);
        integerField.setValue(6);
        integerField.setHasControls(true);
        return integerField;
    }

    /**
     * Gets an {@link TextField} after setting common attributes.
     *
     * @return the customised text field.
     */
    private TextField getPlayersNameField() {
        TextField textField = new TextField();
        textField.setMaxLength(100);
        textField.setWidthFull();
        return textField;
    }

    /**
     * Once clicked, this button will trigger:
     * <ul>
     *     <li>A validation that enforces player names to be entered</li>
     *     <li>A REST call to create game</li>
     *     <li>A page redirect to game view</li>
     * </ul>
     *
     * @param service                    the service to communicate REST APIs.
     * @param firstPlayersNameField      first player's name text field
     * @param secondPlayersNameField     second player's name text field
     * @param numberOfPitsPerPlayerField the number of pits per player field
     * @param numberOfStonesPerPitField  the number of stones per pit field
     * @return the custom event listener to be attached to the button.
     */
    private ComponentEventListener<ClickEvent<Button>> getStartButtonListener(RestClientService service, TextField firstPlayersNameField, TextField secondPlayersNameField, IntegerField numberOfPitsPerPlayerField, IntegerField numberOfStonesPerPitField) {
        return e -> {

            String firstPlayersName = firstPlayersNameField.getValue();
            String secondPlayersName = secondPlayersNameField.getValue();

            if (firstPlayersName.isEmpty() || secondPlayersName.isEmpty()) {
                Notification notification = Notification.show("Enter user names to start!");
                log.error("Player names not entered");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }
            GameConfig gameConfig = new GameConfig(firstPlayersName, secondPlayersName,
                    numberOfPitsPerPlayerField.getValue(), numberOfStonesPerPitField.getValue());

            URI uri = service.createGame(gameConfig);
            String id = uri.getPath().substring(endPoint.length() + 1);

            UI.getCurrent().navigate(GameView.class, new RouteParameters("id", id));
        };
    }
}