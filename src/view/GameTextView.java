package view;

import java.io.IOException;
import java.io.PrintStream;

/**
 * Displays the necessary details of a Stratego game in a print format, using the
 * PrintStream provided to the constructor.
 */
public class GameTextView implements GameView {

  private final PrintStream output;

  public GameTextView(PrintStream output) throws IllegalArgumentException {
    if (output == null) {
      throw new IllegalArgumentException("Provided PrintStream cannot be null.");
    }

    this.output = output;
  }

  @Override
  public void displayBoard(char[][] board, int player) throws IllegalArgumentException, IOException {
    if (board == null) {
      throw new IllegalArgumentException("Provided board cannot be null.");
    }

    if (board.length == 0 || board[0].length == 0) {
      throw new IllegalArgumentException("Provided board must have at least one entry.");
    }

    if (player != 1 && player != 2) {
      throw new IllegalArgumentException("Provided player integer must be either 1 (lava) or 2 (ice).");
    }

    for (int r = 0; r < board.length; r++) {
      if (r != 0) {
        this.output.println('\n');
      }

      for (int c = 0; c < board[r].length; c++) {
        String toPrint = "";

        char value = board[r][c];

        /*
         * For values in the board array:
         *   - capital letters are lava
         *   - lowercase letters are ice
         *   - letters A through J are pieces 1-10
         *   - letter T is a trap
         *   - letter F is a flag
         *   - hyphens are invalid squares
         *   - spaces are empty squares
         */

        if (value == '-') {
          toPrint = " ";
        } else if (value == ' ') {
          toPrint = ".";
        } else {
          if (player == 1) {
            if (value == 'T' || value == 'X') {
              toPrint = "" + value;
            } else if (value >= 'A' && value <= 'Z') {
              toPrint = "" + (value - 'A' + 1);
            } else {
              toPrint = "?";
            }
          } else {
            if (value == 't' || value == 'x') {
              toPrint = "" + (char) (value - 'a' + 'A');
            } else if (value >= 'a' && value <= 'z') {
              toPrint = "" + (value - 'a' + 1);
            } else {
              toPrint = "?";
            }
          }
        }

        this.output.print(String.format("%5s", toPrint));


      }
    }
  }

  @Override
  public void displayMessage(String message) throws IllegalArgumentException, IOException {
    this.output.print(message);
  }
}
