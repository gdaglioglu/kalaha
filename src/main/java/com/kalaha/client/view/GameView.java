package com.kalaha.client.view;

import com.kalaha.client.dto.*;
import com.kalaha.client.service.RestClientService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * View that displays the actual kalaha game interface.
 * A new instance of this class is created for every new user and every browser tab/window.
 */
@PageTitle("Let's play!")
@Route("/kalaha")
@Theme(value = Lumo.class)
public class GameView extends VerticalLayout {

    private final HorizontalLayout firstPlayerPitLayout;
    private final HorizontalLayout secondPlayerPitLayout;
    private final List<Button> buttonList = new ArrayList<>();
    private final Map<Player, VerticalLayout> playerToKalaha;

    /**
     * Constructs the game view.
     *
     * @param service The service that enables client to consume web services.
     */
    public GameView(@Autowired RestClientService service) {

        firstPlayerPitLayout = new HorizontalLayout();
        secondPlayerPitLayout = new HorizontalLayout();

        VerticalLayout firstPlayersKalahaLayout = new VerticalLayout();
        VerticalLayout kalaha2Layout = new VerticalLayout();

        GameData gameData = service.getGameData();
        Player firstPlayer = gameData.getFirstPlayer();
        Player secondPlayer = gameData.getSecondPlayer();
        playerToKalaha = Map.of(firstPlayer, firstPlayersKalahaLayout, secondPlayer, kalaha2Layout);

        H3 player1Name = new H3(firstPlayer.getName());
        H3 player2Name = new H3(secondPlayer.getName());

        HorizontalLayout pitBoardLayout = new HorizontalLayout();

        VerticalLayout pitLayout = new VerticalLayout();
        pitLayout.add(secondPlayerPitLayout);
        pitLayout.add(firstPlayerPitLayout);
        pitBoardLayout.add(kalaha2Layout, pitLayout, firstPlayersKalahaLayout);
        Button restartButton = new Button("Restart", e -> UI.getCurrent().navigate(ConfigView.class));
        restartButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(player2Name, pitBoardLayout, player1Name, restartButton);
        setWidthFull();
        setAlignItems(Alignment.CENTER);

        setHorizontalComponentAlignment(Alignment.CENTER, player2Name, pitBoardLayout, player1Name, restartButton);

        addPlayerPits(gameData, service);
    }

    /**
     * Adds pits for players to the view.
     * @param gameData the representation of the game data.
     * @param service the client service to invoke REST APIs.
     */
    private void addPlayerPits(GameData gameData, RestClientService service) {

        List<Pit> pits = gameData.getPits();

        int firstPlayersKalahaIndex = (pits.size() - 2) / 2;
        int secondPlayersKalahaIndex = pits.size() - 1;

        pits.forEach(pit -> {
            PlayData playData = new PlayData();
            // TODO: change it to pit rather than id
            int pitIndex = pits.indexOf(pit);

            playData.setSelectedPit(pitIndex);
            playData.setPlayer(pit.getPlayer());

            Button button = new Button("" + pit.getStones(), e -> {
                GameData updatedGameData = service.sendPlayData(playData);

                for (Violation violation : updatedGameData.getViolationInfo()) {
                    Notification notification = Notification.show(String.join(System.lineSeparator(), violation.getMessage()));
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
                refreshView(updatedGameData);
            });
            button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

            if (pitIndex == firstPlayersKalahaIndex || pitIndex == secondPlayersKalahaIndex) {
                button.setEnabled(false);
                button.setSizeFull();
                playerToKalaha.get(pit.getPlayer()).add(button);
            }

            buttonList.add(button);
        });

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
     * Refreshes game view after each turn.
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
            Notification notification = Notification.show("Congratulations " + gameInfo.getWinner().getName());
            notification.setPosition(Notification.Position.TOP_CENTER);
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.setDuration(10000);
            buttonList.forEach(button -> button.setEnabled(false));
        }
    }
}