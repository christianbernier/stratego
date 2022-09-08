package model;

import java.util.List;

import controller.GameSyncController;
import model.pieces.Beast;
import model.pieces.BeastRider;
import model.pieces.Dragon;
import model.pieces.Dwarf;
import model.pieces.Elf;
import model.pieces.EmptyPiece;
import model.pieces.FlagPiece;
import model.pieces.GamePiece;
import model.pieces.GamePieceType;
import model.pieces.InvalidPiece;
import model.pieces.Knight;
import model.pieces.Mage;
import model.pieces.Scout;
import model.pieces.Slayer;
import model.pieces.Sorceress;
import model.pieces.TrapPiece;
import utils.BoardPosition;
import utils.Move;
import utils.PlayerTheme;

public class GameModelImpl implements GameModel {

  private final GamePiece[][] board;
  private GameSyncController controller;

  public GameModelImpl(char[][] initialBoard) throws IllegalArgumentException {
    if (initialBoard == null || initialBoard.length == 0 || initialBoard[0].length == 0) {
      throw new IllegalArgumentException("Initial board cannot be empty or null.");
    }

    this.board = new GamePiece[initialBoard.length][initialBoard[0].length];

    for (int r = 0; r < initialBoard.length; r++) {
      for (int c = 0; c < initialBoard[r].length; c++) {
        this.board[r][c] = this.charToGamePiece(initialBoard[r][c]);
      }
    }
  }

  @Override
  public void linkController(GameSyncController controller) {
    if (controller == null) throw new IllegalArgumentException("Controller cannot be null.");
    this.controller = controller;
  }

  private static final GamePiece charToGamePiece(char c) {
    PlayerTheme owner = null;
    if (c >= 'A' && c <= 'Z') {
      owner = PlayerTheme.LAVA;
    } else if (c >= 'a' && c <= 'z') {
      owner = PlayerTheme.ICE;
    }

    switch (c) {
      case 'A':
      case 'a': return new Slayer(owner);
      case 'B':
      case 'b': return new Scout(owner);
      case 'C':
      case 'c': return new Dwarf(owner);
      case 'D':
      case 'd': return new Elf(owner);
      case 'E':
      case 'e': return new Beast(owner);
      case 'F':
      case 'f': return new Sorceress(owner);
      case 'G':
      case 'g': return new BeastRider(owner);
      case 'H':
      case 'h': return new Knight(owner);
      case 'I':
      case 'i': return new Mage(owner);
      case 'J':
      case 'j': return new Dragon(owner);
      case 'T':
      case 't': return new TrapPiece(owner);
      case 'X':
      case 'x': return new FlagPiece(owner);
      case '-': return new InvalidPiece();
      default: return new EmptyPiece();
    }
  }

  private static final char gamePieceToChar(GamePiece gp) throws IllegalArgumentException {
    char c;

    switch (gp.getType()) {
      case SLAYER: c = 'A';
        break;
      case SCOUT: c = 'B';
        break;
      case DWARF: c = 'C';
        break;
      case ELF: c = 'D';
        break;
      case BEAST: c = 'E';
        break;
      case SORCERESS: c = 'F';
        break;
      case BEAST_RIDER: c = 'G';
        break;
      case KNIGHT: c = 'H';
        break;
      case MAGE: c = 'I';
        break;
      case DRAGON: c = 'J';
        break;
      case TRAP: c = 'T';
        break;
      case FLAG: c = 'X';
        break;
      case INVALID: return '-';
      default: return ' ';
    }

    switch (gp.getOwner()) {
      case LAVA: return c;
      case ICE: return (char) (c - 'A' + 'a');
    }

    throw new IllegalArgumentException("Invalid piece type.");
  }

  @Override
  public boolean isGameOver() {
    int flags = 0;
    boolean noMovesLeft = true;

    for (int r = 0; r < this.board.length; r++) {
      for (int c = 0; c < this.board[r].length; c++) {
        GamePiece piece = this.board[r][c];

        if (piece.getType() == GamePieceType.FLAG) {
          flags++;
          continue;
        }

        List<Move> validMoves = piece.validMoves(this.board, new BoardPosition(r, c));

        if (!validMoves.isEmpty()) {
          noMovesLeft = false;
        }
      }
    }

    return noMovesLeft || flags < 2;
  }

  @Override
  public int winner() throws IllegalStateException {
    if (!this.isGameOver()) {
      throw new IllegalStateException("Cannot get the winner of a game that is not over.");
    }

    int movableIcePieces = 0;
    int movableLavaPieces = 0;
    boolean iceFlagExists = false;
    boolean lavaFlagExists = false;

    for (int r = 0; r < this.board.length; r++) {
      for (int c = 0; c < this.board[r].length; c++) {
        GamePiece piece = this.board[r][c];
        PlayerTheme owner = piece.getOwner();

        if (piece.getType() == GamePieceType.FLAG) {
          if (owner == PlayerTheme.LAVA) {
            lavaFlagExists = true;
          } else if (owner == PlayerTheme.ICE) {
            iceFlagExists = true;
          }

          continue;
        }

        List<Move> validMoves = piece.validMoves(this.board, new BoardPosition(r, c));

        if (!validMoves.isEmpty()) {
          if (owner == PlayerTheme.LAVA) {
            movableLavaPieces++;
          } else if (owner == PlayerTheme.ICE) {
            movableIcePieces++;
          }
        }
      }
    }

    if (!iceFlagExists && lavaFlagExists) {
      return 1;
    } else if (iceFlagExists && !lavaFlagExists) {
      return 2;
    } else if (iceFlagExists && lavaFlagExists) {
      if (movableIcePieces == 0 && movableLavaPieces > 0) {
        return 1;
      } else if (movableIcePieces > 0 && movableLavaPieces == 0) {
        return 2;
      } else if (movableIcePieces == 0 && movableLavaPieces == 0) {
        return 0;
      }
    }

    throw new IllegalStateException("Illegal game state.");
  }

  @Override
  public char[][] getBoard() {
    char[][] charBoard = new char[this.board.length][this.board[0].length];

    for (int r = 0; r < this.board.length; r++) {
      for (int c = 0; c < this.board[r].length; c++) {
        charBoard[r][c] = this.gamePieceToChar(this.board[r][c]);
      }
    }

    return charBoard;
  }

  @Override
  public List<Move> validMoves(BoardPosition from) throws IllegalArgumentException {
    GamePiece fromPiece = this.gamePieceFromBoard(from);

    return fromPiece.validMoves(this.board, from);
  }

  @Override
  public void executeMove(Move move) throws IllegalArgumentException {
    if (move == null) {
      throw new IllegalArgumentException("Provided move cannot be null.");
    }

    GamePiece fromPiece = this.gamePieceFromBoard(move.getFrom());

    String moveDetailText = fromPiece.executeMove(this.board, move);

    if (!moveDetailText.equals("")) {
      int displayTo = (fromPiece.getOwner() == PlayerTheme.LAVA) ? 2 : 1;

      this.controller.display(moveDetailText, displayTo);
    }
  }

  private GamePiece gamePieceFromBoard(BoardPosition bp) throws IllegalArgumentException {
    if (bp == null) {
      throw new IllegalArgumentException("Board position cannot be null.");
    }

    int r = bp.getR();
    int c = bp.getC();

    if (r < 0 || r >= this.board.length || c < 0 || c >= this.board[0].length) {
      throw new IllegalArgumentException("Board position must be within the board.");
    }

    return this.board[r][c];
  }

  @Override
  public String specialName(char piece) throws IllegalArgumentException {
    switch (piece) {
      case 'A':
      case 'a': return "Slayer";
      case 'B':
      case 'b': return "Scout";
      case 'C':
      case 'c': return "Dwarf";
      case 'D':
      case 'd': return "Elf";
      case 'E': return "Lava Beast";
      case 'e': return "Yeti";
      case 'F':
      case 'f': return "Sorceress";
      case 'G':
      case 'g': return "Beast Rider";
      case 'H':
      case 'h': return "Knight";
      case 'I':
      case 'i': return "Mage";
      case 'J':
      case 'j': return "Dragon";
      case 'T':
      case 't': return "Trap";
      case 'X':
      case 'x': return "Flag";
    }

    throw new IllegalArgumentException("Cannot find piece type: " + piece);
  }
}
