package utils;

import java.util.Objects;

/**
 * Represents a position on a board of Stratego.
 */
public class BoardPosition {
  private final int r;
  private final int c;

  /**
   * Creates a new {@code BoardPosition} object with the provided row and column
   * positions.
   * @param r row on the board, zero-indexed
   * @param c column on the board, zero-indexed
   * @throws IllegalArgumentException if either value is negative
   */
  public BoardPosition(int r, int c) throws IllegalArgumentException {
    if (r < 0 || c < 0) {
      throw new IllegalArgumentException("Provided row and column must be at least zero.");
    }

    this.r = r;
    this.c = c;
  }

  /**
   * Returns the row of this position on the board.
   * @return the row on the board, zero-indexed
   */
  public int getR() {
    return r;
  }

  /**
   * Returns the column of this position on the board.
   * @return the column on the board, zero-indexed
   */
  public int getC() {
    return c;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof BoardPosition)) {
      return false;
    }

    BoardPosition other = (BoardPosition) o;
    return other.r == r && other.c == c;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.r, this.c);
  }
}
