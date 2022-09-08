package model.pieces;

import java.util.ArrayList;
import java.util.List;

import utils.BoardPosition;
import utils.Move;
import utils.MoveType;
import utils.PlayerTheme;

import static model.pieces.GamePieceAttackResult.ATTACKER_WINS;
import static model.pieces.GamePieceAttackResult.DRAW;

public abstract class AbstractGamePiece implements GamePiece {

  private final GamePieceType type;
  private PlayerTheme owner;

  protected AbstractGamePiece(GamePieceType type, PlayerTheme owner) throws IllegalArgumentException {
    if (type == null) {
      throw new IllegalArgumentException("Provided type cannot be null.");
    }

    this.type = type;
    this.owner = owner;
  }

  @Override
  public GamePieceType getType() {
    return this.type;
  }

  @Override
  public PlayerTheme getOwner() {
    return this.owner;
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

    int r = from.getR();
    int c = from.getC();

    if (r < 0 || r >= board.length || c < 0 || c >= board[0].length) {
      return new ArrayList<>();
    }

    for (int dr = -1; dr < 2; dr++) {
      for (int dc = -1; dc < 2; dc++) {
        if (dr != 0 && dc != 0) continue;
        if (dr == 0 && dc == 0) continue;

        int newR = r + dr;
        int newC = c + dc;

        if (newR >= 0
                && newR <= board.length - 1
                && newC >= 0
                && newC <= board[newR].length - 1) {
          if (board[newR][newC].getOwner() == null) {
            moves.add(new Move(MoveType.REGULAR, from, new BoardPosition(newR, newC)));
          } else if (board[newR][newC].getOwner() != this.getOwner()){
            moves.add(new Move(MoveType.ATTACK, from, new BoardPosition(newR, newC)));
          }
        }
      }
    }

    return moves;
  }

  @Override
  public String executeMove(GamePiece[][] board, Move move) throws IllegalArgumentException {
    if (board == null) {
      throw new IllegalArgumentException("Provided board cannot be null.");
    }

    if (move == null) {
      throw new IllegalArgumentException("Provided move cannot be null.");
    }

    if (!this.validMoves(board, move.getFrom()).contains(move)) {
      throw new IllegalArgumentException("Provided move is invalid.");
    }

    int fromR = move.getFrom().getR();
    int fromC = move.getFrom().getC();
    int toR = move.getTo().getR();
    int toC = move.getTo().getC();

    String specialText = "";

    switch (move.getType()) {
      case REGULAR:
        board[toR][toC] = board[fromR][fromC];
        board[fromR][fromC] = new EmptyPiece();
        break;
      case ATTACK:
        GamePiece defender = board[toR][toC];
        GamePieceAttackResult result = this.attacks(defender.getType());
        if (result == ATTACKER_WINS) {
          board[toR][toC] = board[fromR][fromC];
        } else if (result == DRAW) {
          board[toR][toC] = new EmptyPiece();
        }
        board[fromR][fromC] = new EmptyPiece();
        break;
      case SPECIAL:
        specialText = this.executeSpecialMove(board, move);
    }

    return specialText;
  }

  protected abstract String executeSpecialMove(GamePiece[][] board, Move move) throws IllegalArgumentException;

  @Override
  public void changeSides() {
    if (this.getOwner() == PlayerTheme.ICE) this.owner = PlayerTheme.LAVA;
    if (this.getOwner() == PlayerTheme.LAVA) this.owner = PlayerTheme.ICE;
  }
}
