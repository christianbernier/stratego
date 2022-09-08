package model.pieces;

import java.util.ArrayList;
import java.util.List;

import utils.BoardPosition;
import utils.Move;
import utils.PlayerTheme;

public abstract class AbstractImmovablePiece extends AbstractGamePiece {

  protected AbstractImmovablePiece(GamePieceType type, PlayerTheme owner) throws IllegalArgumentException {
    super(type, owner);
  }

  @Override
  public final GamePieceAttackResult attacks(GamePieceType defender) {
    throw new UnsupportedOperationException("Cannot attack with an immovable piece.");
  }

  @Override
  public final List<Move> validMoves(GamePiece[][] board, BoardPosition from) throws IllegalArgumentException {
    return new ArrayList<>();
  }

  @Override
  public final String executeMove(GamePiece[][] board, Move move) throws IllegalArgumentException {
    throw new UnsupportedOperationException("Cannot move this piece.");
  }

  @Override
  protected final String executeSpecialMove(GamePiece[][] board, Move move) throws IllegalArgumentException {
    throw new UnsupportedOperationException("Cannot move this piece.");
  }
}
