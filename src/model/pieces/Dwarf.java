package model.pieces;

import java.util.ArrayList;
import java.util.List;

import utils.BoardPosition;
import utils.Move;
import utils.PlayerTheme;

public class Dwarf extends AbstractMovablePiece {
  public Dwarf(PlayerTheme owner) throws IllegalArgumentException {
    super(GamePieceType.DWARF, owner);
  }

  @Override
  protected List<Move> validSpecialMoves(GamePiece[][] board, BoardPosition from) throws IllegalArgumentException {
    return new ArrayList<>();
  }

  @Override
  protected String executeSpecialMove(GamePiece[][] board, Move move) throws IllegalArgumentException {
    throw new IllegalStateException("Cannot execute special move of Dwarf.");
  }

  @Override
  public GamePieceAttackResult attacks(GamePieceType defender) {
    if (defender == GamePieceType.TRAP) {
      return GamePieceAttackResult.ATTACKER_WINS;
    }

    return super.attacks(defender);
  }
}
