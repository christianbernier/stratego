package utils;

public enum MoveType {
  // moving to an adjacent square, not diagonally, and not attacking
  REGULAR("regular move"),

  // moving to a square, dependent on the piece (can sometimes overlap with regular moves or attacks)
  SPECIAL("special move"),

  // moving to an adjacent square, not diagonally, and attacking the piece there
  ATTACK("attack");

  private final String name;
  MoveType(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }
}
