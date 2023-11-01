package edu.austral.dissis.chess.chess

import edu.austral.dissis.chess.gui.*
import edu.austral.dissis.chess.gui.ChessPiece
import edu.austral.dissis.chess.chess.validators.result.InvalidResult
import edu.austral.dissis.chess.chess.validators.result.ValidResult
import edu.austral.dissis.chess.chess.validators.result.ValidWExecutionResult
import edu.austral.dissis.chess.chess.validators.result.ValidatorResult
import edu.austral.dissis.chess.chess.victoryValidators.CheckmateResult
import edu.austral.dissis.chess.chess.victoryValidators.CheckmateValidator
import edu.austral.dissis.chess.chess.victoryValidators.NoMoreOpponentPieces
import edu.austral.dissis.chess.chess.victoryValidators.VictoryResult

class ClassicGame(val map: MutableMap<Square, Piece>, private var currentColor: PlayerColor, private val boardSize: Int) : GameEngine {
    private val victoryValidator = CheckmateValidator()

    override fun init(): InitialState {
        return InitialState(BoardSize(boardSize, boardSize), getChessPieces(), currentColor)
    }

    override fun applyMove(move: Move): MoveResult {
        val fromSquare = positionToSquare(move.component1())
        val toSquare = positionToSquare(move.component2())

        val piece = getPiece(fromSquare)
        if(fromSquare == toSquare) return InvalidMove("Invalid move")
        if(piece == null) return InvalidMove("No piece in (" + fromSquare.horizontal + ", " + fromSquare.vertical + ')')
        else if (pieceToPlayerColor(piece.color) != currentColor)
            return InvalidMove("Piece does not belong to current player")


        when (val result: ValidatorResult = piece.move(fromSquare, toSquare, map)) {
            is InvalidResult -> return InvalidMove("Invalid move")
            is ValidResult -> movePiece(fromSquare, toSquare)
            is ValidWExecutionResult -> {
                applyMove(Move(squareToPosition(result.fromSquare), squareToPosition(result.toSquare)))
                movePiece(fromSquare, toSquare)
                changeCurrentColor()
            }
        }

        return statusGame()
    }

    private fun statusGame(): MoveResult{
        val chessPieces = getChessPieces()

        val victoryResult = getVictoryResult()
        if(victoryResult is CheckmateResult)
            return (GameOver(currentColor))
        else if (victoryResult is NoMoreOpponentPieces){
            println("TIE!")
            return (GameOver(currentColor))
        }

        changeCurrentColor()
        return NewGameState(chessPieces, currentColor)
    }

    private fun getVictoryResult(): VictoryResult {
        return victoryValidator.validateVictory(map, playerToPieceColor(currentColor))
    }

    private fun getChessPieces(): ArrayList<ChessPiece>{
        val pieces = ArrayList<ChessPiece>()
        for((square, piece) in map){
            pieces.add(getChessPiece(piece, square))
        }
        return pieces
    }

    private fun getChessPiece(piece: Piece, square: Square): ChessPiece{
        return ChessPiece(piece.id, pieceToPlayerColor(piece.color), squareToPosition(square), piece.getPieceId())
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
        val horizontal = coordinateUIToSquare(position.component2()-1)
        return Square(vertical, horizontal)
    }

    private fun getPiece(square: Square): Piece?{
        return map[square]
    }

    private fun movePiece(from: Square, to: Square){
        map[to] = map.remove(from)!!
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