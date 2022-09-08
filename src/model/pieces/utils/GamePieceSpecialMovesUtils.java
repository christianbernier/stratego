package model.pieces.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import model.pieces.GamePiece;
import utils.BoardPosition;
import utils.Move;
import utils.MoveType;

import static model.pieces.GamePieceType.EMPTY;

public class GamePieceSpecialMovesUtils {
  public static List<Move> enemiesWithinNSpaces(GamePiece[][] board, BoardPosition from, int n, boolean diagonal) throws IllegalArgumentException {
    List<Move> moves = new ArrayList<>();

    for (int r = from.getR() - n; r <= from.getR() + n; r++) {
      for (int c = from.getC() - n; c <= from.getC() + n; c++) {
        if (r < 0 || r >= board.length || c < 0 || c >= board[0].length) continue;
        if (!diagonal && !(r == from.getR() || c == from.getC())) continue;

        if (board[r][c].getOwner() != null && board[r][c].getOwner() != board[from.getR()][from.getC()].getOwner()) {
          moves.add(new Move(MoveType.SPECIAL, from, new BoardPosition(r, c)));
        }
      }
    }

    return moves;
  }

  public static List<Move> validSpecialMovesInDirection(GamePiece[][] board, BoardPosition from, int dr, int dc, boolean allowEndAttack, Function<GamePiece, Boolean> condition) {
    List<Move> moves = new ArrayList<>();

    int r = from.getR() + dr;
    int c = from.getC() + dc;

    while (r >= 0 && r < board.length && c >= 0 && c < board[0].length && condition.apply(board[r][c])) {
      moves.add(new Move(MoveType.SPECIAL, from, new BoardPosition(r, c)));

      r += dr;
      c += dc;
    }

    if (allowEndAttack) {
      if (r >= 0 && r < board.length && c >= 0 && c < board[0].length
              && board[r][c].getOwner() != board[from.getR()][from.getC()].getOwner()) {
        moves.add(new Move(MoveType.SPECIAL, from, new BoardPosition(r, c)));
      }
    }

    return moves;
  }
}
