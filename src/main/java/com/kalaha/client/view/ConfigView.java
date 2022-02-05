package com.kalaha.client.view;

import com.kalaha.client.dto.GameConfig;
import com.kalaha.client.service.RestClientService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.NavigationHandler;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * View to capture the name of the players and configuration parameters regarding the game.
 */
@PageTitle("Enter player names!")
@Route("/startGame")
public class ConfigView extends HorizontalLayout {

    public ConfigView(@Autowired RestClientService service) {

        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(Alignment.CENTER);

        TextField player1NameField = new TextField("Player 1:");
        TextField player2NameField = new TextField("Player 2:");

        IntegerField numberOfPitsPerPlayerField = new IntegerField("Number of pits per player:");
        numberOfPitsPerPlayerField.setWidth("192px");
        IntegerField numberOfStonesPerPitField = new IntegerField("Number of stones per pit:");
        numberOfStonesPerPitField.setWidth("192px");

        Button submitButton = new Button("Start", e -> {

            GameConfig gameConfig = new GameConfig(player1NameField.getValue(), player2NameField.getValue(),
                    numberOfPitsPerPlayerField.getValue(), numberOfStonesPerPitField.getValue());

            service.createGame(gameConfig);

            UI.getCurrent().getPage().setLocation("kalaha");
        });
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        layout.add(player1NameField, player2NameField, numberOfPitsPerPlayerField, numberOfStonesPerPitField, submitButton);
        add(layout);
    }
}