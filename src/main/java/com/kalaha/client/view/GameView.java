package com.kalaha.client.view;

import com.kalaha.client.service.RestClientService;
import com.kalaha.model.GameData;
import com.kalaha.model.Pit;
import com.kalaha.model.PlayData;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * View that displays the actual kalaha game interface.
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 * TODO: Figure out what @PWA tag is really used for.
 */
@PWA(name = "Vaadin Application", shortName = "Vaadin App", description = "This is an example Vaadin application.", enableInstallPrompt = false)
@Route("/kalaha")
public class GameView extends HorizontalLayout {

    private final Label player2Name;
    private final Label player1Name;
    private final VerticalLayout boardLayout;
    private final HorizontalLayout pitBoardLayout;
    private final VerticalLayout pitLayout;
    private final HorizontalLayout player2PitLayout;
    private final HorizontalLayout player1PitLayout;
    private final VerticalLayout kalaha2Layout;
    private final VerticalLayout kalaha1Layout;
    private final List<Button> buttonList = new ArrayList<>();

    /**
     * Constructs the game view.
     * @param service The service that enables client to consume web services.
     */
    public GameView(@Autowired RestClientService service) {

        player2Name = new Label("Player 2 name");
        player1Name = new Label("Player 1 name");

        boardLayout = new VerticalLayout();
        boardLayout.setAlignItems(Alignment.CENTER);
        pitBoardLayout = new HorizontalLayout();

        pitLayout = new VerticalLayout();
        player2PitLayout = new HorizontalLayout();
        player1PitLayout = new HorizontalLayout();

        kalaha1Layout = new VerticalLayout();
        kalaha2Layout = new VerticalLayout();

        pitLayout.add(player2PitLayout);
        pitLayout.add(player1PitLayout);
        pitBoardLayout.add(kalaha2Layout, pitLayout, kalaha1Layout);
        boardLayout.add(player2Name, pitBoardLayout, player1Name);
        add(boardLayout);

        GameData gameData = service.getGameData();
        List<Pit> pitList = gameData.getPits();

        addFirstPlayersPits(pitList, service);
        addSecondPlayersPits(pitList, service);
    }

    /**
     * TODO: To be refactored.
     * @param pitList
     * @param service
     */
    private void addFirstPlayersPits(List<Pit> pitList, RestClientService service) {

        int firstPlayersKalahaIndex = (pitList.size() - 2) / 2;

        for (int i = 0; i < firstPlayersKalahaIndex; i++) {

            PlayData playData = new PlayData();
            playData.setSelectedPit(i);
            playData.setPlayerID(0);

            Button button = new Button("" + pitList.get(i).getStones(),
                    e -> {
                        GameData updatedGameData = service.sendPlayData(playData);
                        refreshView(updatedGameData);
                    });

            // Primary button has a more prominent look.
            button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

            player1PitLayout.add(button);
            buttonList.add(button);
        }

        Button kalaha1Button = new Button("" + pitList.get(firstPlayersKalahaIndex).getStones());
        kalaha1Button.setSizeFull();
        kalaha1Button.setEnabled(false);
        buttonList.add(kalaha1Button);
        kalaha1Layout.add(kalaha1Button);
    }

    /**
     * TODO: To be refactored.
     * @param pitList
     * @param service
     */
    private void addSecondPlayersPits(List<Pit> pitList, RestClientService service) {

        int firstPlayersKalahaIndex = (pitList.size() - 2) / 2;
        int secondPlayersKalahaIndex = pitList.size() - 1; //last index

        for (int i = firstPlayersKalahaIndex + 1; i < secondPlayersKalahaIndex; i++) {

            PlayData playData = new PlayData();
            playData.setSelectedPit(i);
            playData.setPlayerID(1);

            Button button = new Button("" + pitList.get(i).getStones(),
                    e -> {
                        GameData updatedGameData = service.sendPlayData(playData);
                        refreshView(updatedGameData);
                    });

            // Primary button has a more prominent look.
            button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

            buttonList.add(button);
        }

        Button kalaha2Button = new Button("" + pitList.get(secondPlayersKalahaIndex).getStones());
        kalaha2Button.setSizeFull();
        kalaha2Button.setEnabled(false);
        buttonList.add(kalaha2Button);
        kalaha2Layout.add(kalaha2Button);

        for(int i = secondPlayersKalahaIndex -1; i > firstPlayersKalahaIndex; i--){
            player2PitLayout.add(buttonList.get(i));
        }
    }

    /**
     * Refreshes game view after each turn.
     * @param gameData The data to be used to refresh the view.
     */
    private void refreshView(GameData gameData) {
        List<Pit> gameDataPits = gameData.getPits();

        for (int i = 0; i < buttonList.size(); i++) {
            buttonList.get(i).setText("" + gameDataPits.get(i).getStones());
        }
    }
}