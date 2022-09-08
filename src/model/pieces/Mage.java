package model.pieces;

import java.util.ArrayList;
import java.util.List;

import model.pieces.utils.GamePieceSpecialMovesUtils;
import utils.BoardPosition;
import utils.Move;
import utils.PlayerTheme;

public class Mage extends AbstractMovablePiece {
  public Mage(PlayerTheme owner) throws IllegalArgumentException {
    super(GamePieceType.MAGE, owner);
  }

  @Override
  protected List<Move> validSpecialMoves(GamePiece[][] board, BoardPosition from) throws IllegalArgumentException {
    return GamePieceSpecialMovesUtils.enemiesWithinNSpaces(board, from, 2, true);
  }

  @Override
  protected String executeSpecialMove(GamePiece[][] board, Move move) throws IllegalArgumentException {
    BoardPosition to = move.getTo();

    GamePiece piece = board[to.getR()][to.getC()];
    String pieceName = piece.getType().name();

    return "Detect Enemy! Revealed: " + pieceName;
  }
}
