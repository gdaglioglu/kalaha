package com.kalaha.client.view;

import com.kalaha.client.dto.*;
import com.kalaha.client.service.RestClientService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * View that displays the actual kalaha game interface.
 * A new instance of this class is created for every new user and every browser tab/window.
 */
@PageTitle("Let's play!")
@Route("/kalaha/:id([0-9]*)")
@Slf4j
@Theme(value = Lumo.class)
public class GameView extends VerticalLayout implements BeforeEnterObserver {

    /**
     * The layout to store first player's non-kalaha pits.
     */
    private final HorizontalLayout firstPlayerPitLayout = new HorizontalLayout();

    /**
     * The layout to store second player's non-kalaha pits.
     */
    private final HorizontalLayout secondPlayerPitLayout = new HorizontalLayout();

    /**
     * The references to the list of buttons created - to be used in button enable/disable logic.
     */
    private final List<Button> buttonList = new ArrayList<>();

    /**
     * The references to the layout that holds the first player's kalaha.
     */
    private final VerticalLayout firstPlayerKalahaLayout = new VerticalLayout();

    /**
     * The references to the layout that holds the second player's kalaha.
     */
    private final VerticalLayout secondPlayerKalahaLayout = new VerticalLayout();

    /**
     * The game id reference.
     */
    private long gameId;

    /**
     * Constructs the game view.
     *
     * @param service The service that enables client to consume web services.
     */
    public GameView(@Autowired RestClientService service) {

        GameData gameData = service.getGameData(gameId);
        Player firstPlayer = gameData.getFirstPlayer();
        Player secondPlayer = gameData.getSecondPlayer();

        H3 firstPlayerName = new H3(firstPlayer.getName());
        H3 secondPlayerName = new H3(secondPlayer.getName());

        HorizontalLayout pitBoardLayout = new HorizontalLayout();

        VerticalLayout pitLayout = new VerticalLayout();
        pitLayout.add(secondPlayerPitLayout);
        pitLayout.add(firstPlayerPitLayout);
        pitBoardLayout.add(secondPlayerKalahaLayout, pitLayout, firstPlayerKalahaLayout);
        Button restartButton = new Button("New Game", e -> UI.getCurrent().navigate(ConfigView.class));
        restartButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(secondPlayerName, pitBoardLayout, firstPlayerName, restartButton);
        setWidthFull();
        setAlignItems(Alignment.CENTER);
        setHorizontalComponentAlignment(Alignment.CENTER, secondPlayerName, pitBoardLayout, firstPlayerName, restartButton);
        addPlayerPits(gameData, service);

        log.debug("Game view initialized: id = {}", gameId);
    }

    /**
     * Adds pits for players to the view.
     *
     * @param gameData the representation of the game data.
     * @param service  the client service to invoke REST APIs.
     */
    private void addPlayerPits(GameData gameData, RestClientService service) {

        List<Pit> pits = gameData.getPits();
        int firstPlayersKalahaIndex = (pits.size() - 2) / 2;
        int secondPlayersKalahaIndex = pits.size() - 1;

        pits.forEach(pit -> {
            int pitIndex = pits.indexOf(pit);
            setButtonConfig(service, gameData, pit, pitIndex);
        });

        Button firstPlayerKalahaButton = buttonList.get(firstPlayersKalahaIndex);
        firstPlayerKalahaButton.setSizeFull();
        firstPlayerKalahaLayout.add(firstPlayerKalahaButton);

        Button secondPlayerKalahaButton = buttonList.get(secondPlayersKalahaIndex);
        secondPlayerKalahaButton.setSizeFull();
        secondPlayerKalahaLayout.add(secondPlayerKalahaButton);

        // Second player's pits are shown in reverse order.
        int count = buttonList.size() / 2 - 1;
        int i = 0;
        int j = buttonList.size() - 2;
        while (count > 0) {
            firstPlayerPitLayout.add(buttonList.get(i++));
            secondPlayerPitLayout.add(buttonList.get(j--));
            count--;
        }
    }

    /**
     * Creates button configuration for players pits.
     *
     * @param service  the client service to initiate REST calls.
     * @param gameData the data that represents the game.
     * @param pit      the data that represents the game.
     * @param pitIndex the index of the pit.
     */
    private void setButtonConfig(RestClientService service, GameData gameData, Pit pit, int pitIndex) {

        PlayData playData = new PlayData();
        playData.setSelectedPit(pitIndex);
        playData.setPlayer(pit.getPlayer());

        Button button = new Button("" + pit.getStones(), getPitClickEventListener(service, playData));
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        TurnInfo turnInfo = gameData.getTurnInfo();
        button.setEnabled(turnInfo.getToPlay().equals(pit.getPlayer()));

        buttonList.add(button);
    }

    /**
     * Once clicked, this button will trigger:
     *  <ul>
     *      <li>A validation that enforces player names to be entered</li>
     *      <li>Notifications if there are issues detected with the user input</li>
     *     <li>A page refresh to reflect the updated game view</li>
     * </ul>
     *
     * @param service  the service to communicate REST APIs.
     * @param playData the data that represents a turn for a player.
     * @return the custom event listener to be attached to pit buttons.
     */
    private ComponentEventListener<ClickEvent<Button>> getPitClickEventListener(RestClientService service, PlayData playData) {
        return e -> {
            GameData updatedGameData = service.sendPlayData(playData);

            for (Violation violation : updatedGameData.getViolationInfo()) {
                String violationMessage = String.join(System.lineSeparator(), violation.getMessage());
                Notification notification = Notification.show(violationMessage);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                log.error("Input violation occurred: {}", violationMessage);
            }
            refreshView(updatedGameData);
        };
    }

    /**
     * Refreshes the game view after each turn by:
     *  <ul>
     *      <li>Enabling/disabling buttons based on turn information</li>
     *      <li>Displaying a notification to announce winner when game is finished</li>
     * </ul>
     *
     * @param gameData The data to be used to refresh the view.
     */
    private void refreshView(GameData gameData) {

        List<Pit> pits = gameData.getPits();
        Player playerToPlay = gameData.getTurnInfo().getToPlay();

        for (int i = 0; i < buttonList.size(); i++) {
            Button button = buttonList.get(i);
            Pit pit = pits.get(i);
            button.setText("" + pit.getStones());
            button.setEnabled(pit.getPlayer().equals(playerToPlay));
        }

        GameInfo gameInfo = gameData.getGameInfo();
        if (gameInfo.getGameStatus() == GameStatus.FINISHED) {
            Notification notification = Notification.show("Winner: " + gameInfo.getWinner().getName() +
                    System.lineSeparator() + "Congratulations!");
            notification.setPosition(Notification.Position.TOP_CENTER);
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.setDuration(10000);
            buttonList.forEach(button -> button.setEnabled(false));
        }

        log.debug("Game view refreshed: {}", gameData);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        RouteParameters routeParameters = beforeEnterEvent.getRouteParameters();
        gameId = Long.parseLong(routeParameters.get("id").orElse("0"));
    }
}