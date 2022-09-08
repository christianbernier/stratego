package model.pieces;

import utils.PlayerTheme;

public class Knight extends AbstractMovablePieceWithQuickness {
  public Knight(PlayerTheme owner) throws IllegalArgumentException {
    super(GamePieceType.KNIGHT, owner);
  }
}
