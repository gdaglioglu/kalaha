package com.kalaha.client.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Custom error page to be displayed in case of an error occurs, e.g. display game page is requested before creating the game etc.
 */
@PageTitle("Error occurred!")
@Route("/error")
@Theme(value = Lumo.class)
public class ErrorView extends HorizontalLayout {

    /**
     * Logger instance.
     */
    public static final Logger logger = LoggerFactory.getLogger(ErrorView.class);

    /**
     * Constructor for the custom error page.
     */
    public ErrorView() {

        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(Alignment.CENTER);

        Label errorLabel = new Label("An error occurred, please try restarting your game by clicking New Game button!");
        Button newGameButton = new Button("New Game", e -> UI.getCurrent().navigate(ConfigView.class));
        newGameButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        layout.add(errorLabel, newGameButton);
        add(layout);

        logger.debug("Error view initialized");
    }
}