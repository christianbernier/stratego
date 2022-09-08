package controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.GameModel;
import utils.BoardPosition;
import utils.Move;
import view.GameView;

/**
 * Synchronously controls a game of Stratego using the provided view and model.
 */
public class GameSyncControllerImpl implements GameSyncController {
  private final GameView view;
  private final GameModel model;
  private final Scanner input;
  private int currentPlayer;

  public GameSyncControllerImpl(GameView view, GameModel model, InputStream inputStream) throws IllegalArgumentException {
    if (view == null) {
      throw new IllegalArgumentException("Provided view cannot be null.");
    }

    if (model == null) {
      throw new IllegalArgumentException("Provided model cannot be null.");
    }

    if (inputStream == null) {
      throw new IllegalArgumentException("Provided input stream cannot be null.");
    }

    this.view = view;
    this.model = model;
    this.input = new Scanner(inputStream);
    this.currentPlayer = 1;

    this.model.linkController(this);
  }

  @Override
  public void run() {
    this.sendMessage("Welcome to Stratego!\n");

    while (!this.model.isGameOver()) {
      try {
        this.view.displayBoard(this.model.getBoard(), this.currentPlayer);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

      Move m = this.getNextMove();
      if (m != null) {
        this.explainMove(m);
        this.model.executeMove(m);
      }

      if (this.currentPlayer == 1) {
        this.currentPlayer = 2;
      } else {
        this.currentPlayer = 1;
      }
    }

    this.sendMessage("Thanks for playing Stratego!");
  }

  @Override
  public void display(String s, int player) {
    if (s == null) return;

    this.sendMessage("\n" + s + "\n\n");
  }

  private void explainMove(Move m) {
    if (m == null) return;

    StringBuilder summary = new StringBuilder();
    summary.append("\n\nPlayer " + this.currentPlayer + " ");

    BoardPosition from = m.getFrom();
    BoardPosition to = m.getTo();

    switch (m.getType()) {
      case REGULAR:
        summary.append("moved their piece from ");
        break;
      case ATTACK:
      case SPECIAL:
        summary.append("revealed a "
                + this.model.specialName(this.model.getBoard()[from.getR()][from.getC()])
                + " at ");
        break;
    }

    summary.append("( " + from.getR() + ", " + from.getC() + " ) ");

    switch (m.getType()) {
      case REGULAR:
        summary.append("to ");
        break;
      case ATTACK:
        summary.append("and attacked your "
                + this.model.specialName(this.model.getBoard()[to.getR()][to.getC()])
                + " at ");
        break;
      case SPECIAL:
        summary.append("and executed its special move, ending at ");
        break;
    }

    summary.append("( " + to.getR() + ", " + to.getC() + " ).\n");

    this.sendMessage(summary.toString());
  }

  private Move getNextMove() {
    boolean validMove;
    boolean quit = false;

    do {
      validMove = true;
      this.sendMessage("\n\nPlayer " + this.currentPlayer + ": What is your next move?");
      this.sendMessage("\n\nEnter four numbers representing the row and column of the piece");
      this.sendMessage("\nyou'd like to move and the row and column of the place you'd like");
      this.sendMessage("\nto move it to, or \"quit\" if you'd like to quit: ");
      int fromRow = this.input.nextInt();
      int fromCol = this.input.nextInt();
      int toRow = this.input.nextInt();
      int toCol = this.input.nextInt();

      BoardPosition from = new BoardPosition(fromRow, fromCol);
      BoardPosition to = new BoardPosition(toRow, toCol);

      List<Move> validMoves = this.model.validMoves(from);
      List<Move> selectedMoves = new ArrayList<>();

      for (Move m : validMoves) {
        if (m.getFrom().equals(from) && m.getTo().equals(to)) {
          selectedMoves.add(m);
        }
      }

      if (selectedMoves.isEmpty()) {
        this.sendMessage("\nInvalid move! Please try again.");
        validMove = false;
      } else if (selectedMoves.size() == 1) {
        return selectedMoves.get(0);
      } else {
        int choice = -1;

        while (choice <= 0 || choice > selectedMoves.size()) {
          this.sendMessage("\nWhich of " + this.model.specialName(this.model.getBoard()[fromRow][fromCol]) + "'s moves would you like to do?");
          for (int i = 1; i <= selectedMoves.size(); i++) {
            this.sendMessage("\n" + i + ": " + selectedMoves.get(i - 1).getType().getName());
          }

          this.sendMessage("\n");

          choice = this.input.nextInt();

          if (choice <= 0 || choice > selectedMoves.size()) {
            this.sendMessage("\n\nInvalid choice. Please try again.");
          } else {
            return selectedMoves.get(choice - 1);
          }
        }
      }

    } while (!validMove || quit);

    return null;
  }

  private void sendMessage(String message) {
    try {
      this.view.displayMessage(message);
    } catch (IOException e) {
      throw new RuntimeException("Error with IO.");
    }
  }
}
