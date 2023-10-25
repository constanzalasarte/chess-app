package edu.austral.dissis.chess.chess

import edu.austral.dissis.chess.gui.*
import edu.austral.dissis.chess.gui.ChessPiece
import edu.austral.dissis.chess.chess.validators.TypeResult
import edu.austral.dissis.chess.chess.validators.ValidatorResult
import edu.austral.dissis.chess.chess.victoryValidators.CheckmateValidator
import edu.austral.dissis.chess.chess.victoryValidators.TypeVictoryResult

class ClassicGame(val map: MutableMap<Square, Piece>, var currentColor: PlayerColor, val boardSize: Int) : GameEngine {
    private val victoryValidator = CheckmateValidator()

    override fun init(): InitialState {
        return InitialState(BoardSize(boardSize, boardSize), getChessPieces(), currentColor)
    }

    override fun applyMove(move: Move): MoveResult {
        val fromSquare = positionToSquare(move.component1())
        val toSquare = positionToSquare(move.component2())

        val piece = getPiece(fromSquare)
        if(fromSquare == toSquare) return InvalidMove("Invalid move")
        if(piece == null) return InvalidMove("No piece in (" + move.from.row + ", " + move.from.column + ')')
        else if (pieceToPlayerColor(piece.color) != currentColor)
            return InvalidMove("Piece does not belong to current player")


        val result: ValidatorResult = piece.move(fromSquare, toSquare, map)
        when (result.typeResult) {
            TypeResult.INVALID -> return InvalidMove("Invalid move")
            TypeResult.VALID -> movePiece(fromSquare, toSquare)
            else -> {
                movePiece(fromSquare, toSquare)
                applyMove(Move(squareToPosition(result.fromSquare), squareToPosition(result.toSquare)))
            }
        }

        return statusGame()
    }

    private fun statusGame(): MoveResult{
        val chessPieces = getChessPieces()

        val victoryResult = getVictoryResult()
        if(victoryResult ==  TypeVictoryResult.CHECKMATE)
            return (GameOver(currentColor))
        else if (victoryResult == TypeVictoryResult.NO_MORE_OPPONENT_PIECES){
            println("TIE!")
            return (GameOver(currentColor))
        }

        changeCurrentColor()
        return NewGameState(chessPieces, currentColor)
    }

    private fun getVictoryResult(): TypeVictoryResult {
        return victoryValidator.validateVictory(map, playerToPieceColor(currentColor)).victoryResult
    }

    private fun getChessPieces(): ArrayList<ChessPiece>{
        val pieces = ArrayList<ChessPiece>()
        for((square, piece) in map){
            pieces.add(getChessPiece(piece.color, piece.id, square, piece.chessPiece))
        }
        return pieces
    }

    private fun getChessPiece(color: PieceColor, id: String, square: Square, piece: edu.austral.dissis.chess.chess.ChessPiece): ChessPiece{
        val pieceId = when (piece){
            edu.austral.dissis.chess.chess.ChessPiece.PAWN -> "pawn"
            edu.austral.dissis.chess.chess.ChessPiece.HORSE -> "knight"
            edu.austral.dissis.chess.chess.ChessPiece.BISHOP -> "bishop"
            edu.austral.dissis.chess.chess.ChessPiece.ROOK -> "rook"
            edu.austral.dissis.chess.chess.ChessPiece.QUEEN -> "queen"
            edu.austral.dissis.chess.chess.ChessPiece.KING -> "king"
        }
        return ChessPiece(id, pieceToPlayerColor(color), squareToPosition(square), pieceId)
    }
    private fun pieceToPlayerColor(color: PieceColor) : PlayerColor{
        if(color==PieceColor.WHITE) return PlayerColor.WHITE
        return PlayerColor.BLACK
    }
    private fun playerToPieceColor(color: PlayerColor) : PieceColor{
        if(color==PlayerColor.WHITE) return PieceColor.WHITE
        return PieceColor.BLACK
    }
    private fun squareToPosition(square: Square) : Position {
        return Position(coordinateUIToSquare(square.vertical)+1, coordinateUIToSquare(square.horizontal)+1)
    }


    private fun positionToSquare(position: Position): Square {
        val vertical = coordinateUIToSquare(position.component1() - 1)
        val horizontal = coordinateUIToSquare(position.component2() - 1)
        return Square(vertical, horizontal)
    }

    private fun getPiece(square: Square): Piece?{
        return map[square]
    }

    private fun movePiece(from: Square, to: Square){
        map.put(to, map.remove(from)!!)

    }


    private fun changeCurrentColor(){
        currentColor = if(currentColor == PlayerColor.WHITE) PlayerColor.BLACK
        else PlayerColor.WHITE
    }

    private fun coordinateUIToSquare(coordinate: Int) : Int {
        return when (coordinate) {
            7 -> 0
            6 -> 1
            5 -> 2
            4 -> 3
            3 -> 4
            2 -> 5
            1 -> 6
            0 -> 7
            else -> 0
        }
    }
}