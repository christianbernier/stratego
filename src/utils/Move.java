package utils;

import java.util.Objects;

public class Move {
  private final MoveType type;
  private final BoardPosition from;
  private final BoardPosition to;

  public Move(MoveType type, BoardPosition from, BoardPosition to) throws IllegalArgumentException {
    if (type == null) {
      throw new IllegalArgumentException("Provided type cannot be null.");
    }

    if (from == null) {
      throw new IllegalArgumentException("Provided from position cannot be null.");
    }

    if (to == null) {
      throw new IllegalArgumentException("Provided to position cannot be null.");
    }

    this.type = type;
    this.from = from;
    this.to = to;
  }

  public MoveType getType() {
    return this.type;
  }

  public BoardPosition getFrom() {
    return this.from;
  }

  public BoardPosition getTo() {
    return this.to;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (! (o instanceof Move)) return false;

    Move other = (Move) o;
    return this.type == other.type && this.from.equals(other.from) && this.to.equals(other.to);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.type, this.from, this.to);
  }
}
