package edu.austral.dissis.boardGames

import edu.austral.dissis.chess.gui.*
import edu.austral.dissis.chess.gui.ChessPiece
import edu.austral.dissis.boardGames.common.validators.result.InvalidResult
import edu.austral.dissis.boardGames.common.validators.result.ValidResult
import edu.austral.dissis.boardGames.chess.validators.result.ValidWExecutionResult
import edu.austral.dissis.boardGames.common.Piece
import edu.austral.dissis.boardGames.common.PieceColor
import edu.austral.dissis.boardGames.common.Square
import edu.austral.dissis.boardGames.common.validators.result.ValidatorResult
import edu.austral.dissis.boardGames.common.victoryValidators.result.VictoryResult
import edu.austral.dissis.chess.gui.InitialState

class Adapter(
    var currentColor: PlayerColor,
    var adapter: Game
    ) : GameEngine {

    override fun init(): InitialState {
        return InitialState(BoardSize(adapter.getMaxCol(), adapter.getMaxRow()), getChessPieces(), currentColor)
    }


    private fun checkCurrentPieces(): Boolean {
        val squares = adapter.getSquares(currentColor)
        val opponentSquares = adapter.getSquares(getOpponentColor())
        return squares.isNotEmpty() && opponentSquares.isNotEmpty()
    }

    override fun applyMove(move: Move): MoveResult {
        if(checkCurrentPieces()){
            val fromSquare = positionToSquare(move.component1())
            val toSquare = positionToSquare(move.component2())
            val checkPiece = adapter.checkPiece(fromSquare, toSquare, currentColor)
            if (checkPiece != null) return checkPiece
            when (val result: ValidatorResult = adapter.checkMove(fromSquare, toSquare)) {
                is InvalidResult -> return InvalidMove("Invalid move")
                is ValidResult -> {
                    adapter = adapter.movePiece(fromSquare, toSquare)
                }
                is ValidWExecutionResult -> {
                    applyMove(Move(squareToPosition(result.fromSquare), squareToPosition(result.toSquare)))
                    adapter = adapter.movePiece(fromSquare, toSquare)
                    currentColor = adapter.getNextColor(currentColor)
                }
            }
            return statusGame()
        }
        return InvalidMove("GAME OVER. TIE!")
    }



    private fun statusGame(): MoveResult{
        val chessPieces = getChessPieces()

        if(getLostResult().isOver()){
            return GameOver(currentColor)
        }
        currentColor = adapter.getNextColor(currentColor)
        return NewGameState(chessPieces, currentColor)
    }

    private fun getLostResult(): VictoryResult {
        return adapter.getResult(getOpponentColor())
    }

    private fun getVictoryResult(): VictoryResult {
        return adapter.getResult(currentColor)
    }

    private fun getChessPieces(): ArrayList<ChessPiece>{
        val pieces = ArrayList<ChessPiece>()
        for((square, piece) in adapter.getMap()){
            pieces.add(getChessPiece(piece, square))
        }
        return pieces
    }
    private fun getChessPiece(piece: Piece, square: Square): ChessPiece{
        return ChessPiece(piece.id, pieceToPlayerColor(piece.color), squareToPosition(square), piece.getPieceId())
    }
    private fun pieceToPlayerColor(color: PieceColor) : PlayerColor {
        if(isWhite(color)) return PlayerColor.WHITE
        return PlayerColor.BLACK
    }

    private fun isWhite(color: PieceColor) = color == PieceColor.WHITE
    private fun squareToPosition(square: Square) : Position {
        return Position(coordinateUIToSquare(square.vertical), square.horizontal+1)
    }


    private fun positionToSquare(position: Position): Square {
        val vertical = coordinateUIToSquare(position.component1())
        val horizontal = position.component2()-1
        return Square(vertical, horizontal)
    }


    private fun getOpponentColor(): PlayerColor{
        return if(checkWhite()) PlayerColor.BLACK
        else PlayerColor.WHITE
    }

    private fun checkWhite() = currentColor == PlayerColor.WHITE

    private fun coordinateUIToSquare(coordinate: Int) : Int {
        return adapter.getMaxCol() - coordinate
    }
}