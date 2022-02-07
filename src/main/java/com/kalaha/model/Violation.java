package com.kalaha.model;

/**
 * Stores detectable faul play types.
 */
public enum Violation {
    NOT_PLAYERS_TURN("Not your turn!"),
    NO_STONES_IN_PIT("No stones in selected pit!"),
    CANNOT_SELECT_KALAHA("Kalaha pit cannot be selected!"),
    CANNOT_SELECT_OPPONENTS_PIT("Opponent's pit cannot be selected!"),
    NON_EXISTING_PIT("Selected pit index does not exist!"),
    GAME_ALREADY_OVER("Game is over, start a new game to play!");

    /**
     * The message associated with violation.
     */
    private final String message;

    /**
     * Constructs a violation.
     * @param message the message associated with violation.
     */
    Violation(String message) {
        this.message = message;
    }
}