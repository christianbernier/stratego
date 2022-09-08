package model.pieces;

import java.util.ArrayList;
import java.util.List;

import model.pieces.utils.GamePieceSpecialMovesUtils;
import utils.BoardPosition;
import utils.Move;
import utils.PlayerTheme;

public abstract class AbstractMovablePieceWithQuickness extends AbstractMovablePiece {
  protected AbstractMovablePieceWithQuickness(GamePieceType type, PlayerTheme owner) throws IllegalArgumentException {
    super(type, owner);
  }

  @Override
  protected List<Move> validSpecialMoves(GamePiece[][] board, BoardPosition from) throws IllegalArgumentException {
    return GamePieceSpecialMovesUtils.enemiesWithinNSpaces(board, from, 2, false);
  }

  @Override
  protected String executeSpecialMove(GamePiece[][] board, Move move) throws IllegalArgumentException {
    BoardPosition from = move.getFrom();
    BoardPosition to = move.getTo();

    // if it ends in an attack, the end piece will have an owner
    if (board[to.getR()][to.getC()].getOwner() != null) {
      if (this.attacks(board[to.getR()][to.getC()].getType()) == GamePieceAttackResult.ATTACKER_WINS
              || this.attacks(board[to.getR()][to.getC()].getType()) == GamePieceAttackResult.DRAW) {
        board[to.getR()][to.getC()] = board[from.getR()][from.getC()];
      }
    } else { // otherwise, it'll be an empty square
      board[to.getR()][to.getC()] = board[from.getR()][from.getC()];
    }

    board[from.getR()][from.getC()] = new EmptyPiece();

    return "Quickness!";
  }
}
