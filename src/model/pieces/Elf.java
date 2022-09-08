package model.pieces;

import java.util.ArrayList;
import java.util.List;

import model.pieces.utils.GamePieceSpecialMovesUtils;
import utils.BoardPosition;
import utils.Move;
import utils.MoveType;
import utils.PlayerTheme;

import static model.pieces.GamePieceAttackResult.ATTACKER_WINS;

public class Elf extends AbstractMovablePiece {
  public Elf(PlayerTheme owner) throws IllegalArgumentException {
    super(GamePieceType.ELF, owner);
  }

  @Override
  protected List<Move> validSpecialMoves(GamePiece[][] board, BoardPosition from) throws IllegalArgumentException {
    return GamePieceSpecialMovesUtils.enemiesWithinNSpaces(board, from, 2, true);
  }

  @Override
  protected String executeSpecialMove(GamePiece[][] board, Move move) throws IllegalArgumentException {
    BoardPosition from = move.getFrom();
    BoardPosition to = move.getTo();

    // from rules: "If the revealed piece is ranked lower than the attacking piece, the revealed
    // piece is captured. If it is ranked higher than the attacking piece or has the same rank,
    // nothing happens."
    if (board[from.getR()][from.getC()].attacks(board[to.getR()][to.getC()].getType()) == ATTACKER_WINS) {
        board[to.getR()][to.getC()] = new EmptyPiece();
    }

    return "Arrow Strike!";
  }
}
