package model.pieces;

import utils.PlayerTheme;

public class TrapPiece extends AbstractImmovablePiece {
  public TrapPiece(PlayerTheme owner) throws IllegalArgumentException {
    super(GamePieceType.TRAP, owner);

    if (owner == null) {
      throw new IllegalArgumentException("Owner of a trap cannot be null.");
    }
  }
}
