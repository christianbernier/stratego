package model.pieces;

import java.util.ArrayList;
import java.util.List;

import utils.BoardPosition;
import utils.Move;
import utils.PlayerTheme;

import static model.pieces.GamePieceType.EMPTY;
import static model.pieces.GamePieceType.FLAG;
import static model.pieces.GamePieceType.INVALID;
import static model.pieces.GamePieceType.TRAP;

public abstract class AbstractMovablePiece extends AbstractGamePiece {
  protected AbstractMovablePiece(GamePieceType type, PlayerTheme owner) throws IllegalArgumentException {
    super(type, owner);

    if (type == TRAP || type == FLAG || type == EMPTY || type == INVALID) {
      throw new IllegalArgumentException("Not a movable piece type.");
    }
  }

  @Override
  public List<Move> validMoves(GamePiece[][] board, BoardPosition from) throws IllegalArgumentException {
    if (board == null) {
      throw new IllegalArgumentException("Provided board cannot be null.");
    }

    if (from == null) {
      throw new IllegalArgumentException("Provided from position cannot be null.");
    }

    List<Move> moves = new ArrayList<>();
    moves.addAll(super.validMoves(board, from));
    moves.addAll(this.validSpecialMoves(board, from));

    return moves;
  }

  protected abstract List<Move> validSpecialMoves(GamePiece[][] board, BoardPosition from) throws IllegalArgumentException;

  @Override
  public GamePieceAttackResult attacks(GamePieceType defender) {
    GamePieceType type = this.getType();

    if (defender == FLAG) {
      return GamePieceAttackResult.ATTACKER_WINS;
    } else if (defender == TRAP) {
      return GamePieceAttackResult.DEFENDER_WINS;
    }

    int strengthDifference = type.getStrength() - defender.getStrength();

    if (strengthDifference > 0) {
      return GamePieceAttackResult.ATTACKER_WINS;
    } else if (strengthDifference < 0) {
      return GamePieceAttackResult.DEFENDER_WINS;
    }

    return GamePieceAttackResult.DRAW;
  }
}
