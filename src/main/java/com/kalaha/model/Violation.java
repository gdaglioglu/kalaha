package com.kalaha.model;

public enum Violation {
    NOT_PLAYERS_TURN("Not your turn!"),
    NO_STONES_IN_PIT("No stones in selected pit!"),
    CANNOT_SELECT_KALAHA("Kalaha pit cannot be selected!"),
    CANNOT_SELECT_OPPONENTS_PIT("Opponent's pit cannot be selected!"),
    NON_EXISTING_PIT("Selected pit index does not exist!"),
    GAME_ALREADY_OVER("Game is over, start a new game to play!");

    private String message;

    Violation(String message) {
        this.message = message;
    }
}
