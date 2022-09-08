package view;

import java.io.IOException;

/**
 * Displays the necessary details to play a game of Stratego.
 */
public interface GameView {
  /**
   * Displays the provided board from the perspective of the provided player, meaning
   * pieces from the other player are hidden as question marks.
   * @param board the state of the game board to be displayed to the user
   * @param player the player whose perspective will be shown to the user (1 is lava; 2 is ice)
   * @throws IllegalArgumentException if the provided board is null, if the provided board has
   *     no elements in it, or if the provided player integer is not 1 or 2.
   * @throws IOException when the view encounters an exception when printing
   */
  void displayBoard(char[][] board, int player) throws IllegalArgumentException, IOException;

  /**
   * Displays the provided message to the user
   * @param message the message to be displayed
   * @throws IllegalArgumentException when the provided message is {@code null}.
   * @throws IOException when the view encounters an exception when printing
   */
  void displayMessage(String message) throws IllegalArgumentException, IOException;
}
