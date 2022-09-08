package model.pieces;

public enum GamePieceType {
  SLAYER(1),
  SCOUT(2),
  DWARF(3),
  ELF(4),
  BEAST(5),
  SORCERESS(6),
  BEAST_RIDER(7),
  KNIGHT(8),
  MAGE(9),
  DRAGON(10),
  TRAP(),
  FLAG(),
  EMPTY(),
  INVALID();
  private final int strength;

  GamePieceType() {
    this.strength = 0;
  }

  GamePieceType(int strength) {
    this.strength = strength;
  }

  public int getStrength() {
    return this.strength;
  }
}
