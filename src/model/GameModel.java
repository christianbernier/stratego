package model;

import java.util.List;

import controller.GameSyncController;
import utils.BoardPosition;
import utils.Move;

public interface GameModel {
  void linkController(GameSyncController controller);
  boolean isGameOver();

  int winner();

  char[][] getBoard();

  List<Move> validMoves(BoardPosition from);

  void executeMove(Move move);

  String specialName(char piece);
}
