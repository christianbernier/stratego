package model.pieces;

public class EmptyPiece extends AbstractImmovablePiece {
  public EmptyPiece() {
    super(GamePieceType.EMPTY, null);
  }
}
