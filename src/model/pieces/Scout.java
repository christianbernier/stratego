package model.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import model.pieces.utils.GamePieceSpecialMovesUtils;
import utils.BoardPosition;
import utils.Move;
import utils.MoveType;
import utils.PlayerTheme;

import static model.pieces.GamePieceType.EMPTY;

public class Scout extends AbstractMovablePiece {
  public Scout(PlayerTheme owner) throws IllegalArgumentException {
    super(GamePieceType.SCOUT, owner);
  }

  @Override
  protected List<Move> validSpecialMoves(GamePiece[][] board, BoardPosition from) throws IllegalArgumentException {
    List<Move> moves = new ArrayList<>();

    Function<GamePiece, Boolean> isEmpty = gp -> gp.getType() == EMPTY;
    moves.addAll(GamePieceSpecialMovesUtils.validSpecialMovesInDirection(board, from, -1, 0, true, isEmpty));
    moves.addAll(GamePieceSpecialMovesUtils.validSpecialMovesInDirection(board, from, 1, 0, true, isEmpty));
    moves.addAll(GamePieceSpecialMovesUtils.validSpecialMovesInDirection(board, from, 0, -1, true, isEmpty));
    moves.addAll(GamePieceSpecialMovesUtils.validSpecialMovesInDirection(board, from, 0, 1, true, isEmpty));

    return moves;
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

    return "";
  }
}
