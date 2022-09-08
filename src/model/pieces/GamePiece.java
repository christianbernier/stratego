package model.pieces;

import java.util.List;
import java.util.Map;

import controller.GameSyncController;
import utils.BoardPosition;
import utils.Move;
import utils.MoveType;
import utils.PlayerTheme;

public interface GamePiece {
  GamePieceAttackResult attacks(GamePieceType defender);
  GamePieceType getType();
  PlayerTheme getOwner();
  List<Move> validMoves(GamePiece[][] board, BoardPosition from) throws IllegalArgumentException;
  String executeMove(GamePiece[][] board, Move move) throws IllegalArgumentException;
  void changeSides();
}
