package controller;

import utils.BoardPosition;

/**
 * Controls a game of Stratego in a synchronous manner by prompting users for moves, ensuring the
 * rules of the game are followed, and processing invalid moves and errors.
 */
public interface GameSyncController {
  /**
   * Runs the game of Stratego.
   */
  void run();

  /**
   * Displays information about the game from the model.
   * @param s the string to display to the user via the view
   * @param player the player the message should be displayed to
   */
  void display(String s, int player);
}
