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

public class Dragon extends AbstractMovablePiece {
  public Dragon(PlayerTheme owner) throws IllegalArgumentException {
    super(GamePieceType.DRAGON, owner);
  }

  @Override
  protected List<Move> validSpecialMoves(GamePiece[][] board, BoardPosition from) throws IllegalArgumentException {
    List<Move> moves = new ArrayList<>();

    Function<GamePiece, Boolean> isOccupied = gp -> gp.getOwner() != null;
    moves.addAll(GamePieceSpecialMovesUtils.validSpecialMovesInDirection(board, from, -1, 0, false, isOccupied));
    moves.addAll(GamePieceSpecialMovesUtils.validSpecialMovesInDirection(board, from, 1, 0, false, isOccupied));
    moves.addAll(GamePieceSpecialMovesUtils.validSpecialMovesInDirection(board, from, 0, -1, false, isOccupied));
    moves.addAll(GamePieceSpecialMovesUtils.validSpecialMovesInDirection(board, from, 0, 1, false, isOccupied));

    return moves;
  }

  @Override
  protected String executeSpecialMove(GamePiece[][] board, Move move) throws IllegalArgumentException {
    BoardPosition from = move.getFrom();
    BoardPosition to = move.getTo();

    board[to.getR()][to.getC()] = board[from.getR()][from.getC()];
    board[from.getR()][from.getC()] = new EmptyPiece();

    return "Flight!";
  }
}
