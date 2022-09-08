package model.pieces;

import utils.PlayerTheme;

public class FlagPiece extends AbstractImmovablePiece {
  public FlagPiece(PlayerTheme owner) throws IllegalArgumentException {
    super(GamePieceType.FLAG, owner);

    if (owner == null) {
      throw new IllegalArgumentException("Owner of a flag cannot be null.");
    }
  }
}
