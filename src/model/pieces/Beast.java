package model.pieces;

import java.util.ArrayList;
import java.util.List;

import utils.BoardPosition;
import utils.Move;
import utils.MoveType;
import utils.PlayerTheme;

public class Beast extends AbstractMovablePiece {
  public Beast(PlayerTheme owner) throws IllegalArgumentException {
    super(GamePieceType.BEAST, owner);
  }

  @Override
  protected List<Move> validSpecialMoves(GamePiece[][] board, BoardPosition from) throws IllegalArgumentException {
    List<Move> moves = new ArrayList<>();

    for (int dr = -1; dr <= 1; dr++) {
      for (int dc = -1; dc <= 1; dc++) {
        if (dr != 0 && dc != 0) continue;
        if (dr == 0 && dc == 0) continue;

        int r = from.getR() + dr;
        int c = from.getC() + dc;

        if (r < 0 || r >= board.length || c < 0 || c >= board[0].length) continue;

        if (board[r][c].getType() == GamePieceType.EMPTY) {
          moves.add(new Move(MoveType.SPECIAL, from, new BoardPosition(r, c)));
        }
      }
    }

    return moves;
  }

  @Override
  protected String executeSpecialMove(GamePiece[][] board, Move move) throws IllegalArgumentException {
    BoardPosition from = move.getFrom();
    BoardPosition to = move.getTo();

    boolean attackerDies = false;

    for (int dr = -1; dr <= 1; dr++) {
      for (int dc = -1; dc <= 1; dc++) {
        if (dr == 0 && dc == 0) continue;

        int toR = to.getR() + dr;
        int toC = to.getC() + dc;

        switch (this.attacks(board[toR][toC].getType())) {
          case ATTACKER_WINS:
            board[toR][toC] = new EmptyPiece();
            break;
          case DRAW:
          case DEFENDER_WINS:
            attackerDies = true;
        }
      }
    }

    if (attackerDies) {
      board[from.getR()][from.getC()] = new EmptyPiece();
    }

    board[from.getR()][from.getC()] = new EmptyPiece();

    return "Rampage!";
  }
}
