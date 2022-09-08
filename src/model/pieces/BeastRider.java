package model.pieces;

import utils.PlayerTheme;

public class BeastRider extends AbstractMovablePieceWithQuickness {
  public BeastRider(PlayerTheme owner) throws IllegalArgumentException {
    super(GamePieceType.BEAST_RIDER, owner);
  }
}
