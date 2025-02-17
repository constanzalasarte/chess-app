package edu.austral.dissis.boardGames.common

import edu.austral.dissis.chess.gui.*
import edu.austral.dissis.chess.gui.ChessPiece
import edu.austral.dissis.boardGames.common.validators.result.InvalidResult
import edu.austral.dissis.boardGames.common.validators.result.ValidResult
import edu.austral.dissis.boardGames.chess.validators.result.ValidWExecutionResult
import edu.austral.dissis.boardGames.common.validators.result.ValidatorResult
import edu.austral.dissis.boardGames.common.victoryValidators.*
import edu.austral.dissis.boardGames.common.victoryValidators.result.ContinueResult
import edu.austral.dissis.boardGames.common.victoryValidators.result.VictoryResult
import edu.austral.dissis.chess.gui.InitialState

class Game(
    var map: Map<Square, Piece>,
    private var currentColor: PlayerColor,
    private val boardSize: Int,
    private val victoryValidators: List<VictoryValidator>,
    private val nextColor: GameNextColor
    ) : GameEngine {

    override fun init(): InitialState {
        return InitialState(BoardSize(boardSize, boardSize), getChessPieces(), currentColor)
    }

    private fun checkCurrentPieces(): Boolean {
        val squares = map.filter{ it.value.color == playerToPieceColor(currentColor) }
                         .map { it.key }
        val opponentSquares = map.filter{ it.value.color == playerToPieceColor(currentColor) }
                                 .map { it.key }
        return squares.isNotEmpty() && opponentSquares.isNotEmpty()
    }

    override fun applyMove(move: Move): MoveResult {
        if(checkCurrentPieces()){
            val fromSquare = positionToSquare(move.component1())
            val toSquare = positionToSquare(move.component2())

            val piece = getPiece(fromSquare)
            if(fromSquare == toSquare) return InvalidMove("Invalid move")
            if(piece == null) return InvalidMove("No piece in (" + fromSquare.horizontal + ", " + fromSquare.vertical + ')')
            else if (pieceToPlayerColor(piece.color) != currentColor)
                return InvalidMove("Piece does not belong to current player")

            val result: ValidatorResult = piece.move(fromSquare, toSquare, map)

            when (result) {
                is InvalidResult -> return InvalidMove("Invalid move")
                is ValidResult -> {
                    map = nextColor.movePiece(map, fromSquare, toSquare)
                }
                is ValidWExecutionResult -> {
                    applyMove(Move(squareToPosition(result.fromSquare), squareToPosition(result.toSquare)))
                    map = nextColor.movePiece(map, fromSquare, toSquare)
                    changeCurrentColor()
                }
            }

            return statusGame()
        }
        return InvalidMove("GAME OVER")
    }



    private fun statusGame(): MoveResult{
        val chessPieces = getChessPieces()

        val victoryResult = getVictoryResult()
        if(victoryResult.isOver())
            return (GameOver(currentColor))
        else {
            if(getLostResult().isOver())
                return (GameOver(getOpponentColor()))
        }
        currentColor = nextColor.getNextColor(map, currentColor)
        return NewGameState(chessPieces, currentColor)
    }

    private fun getLostResult(): VictoryResult {
        var result : VictoryResult = ContinueResult()
        victoryValidators.forEach{
            result = it.validateVictory(map, playerToPieceColor(getOpponentColor()))
            if(result.isOver()) return result
        }
        return result
    }

    private fun getVictoryResult(): VictoryResult {
        var result : VictoryResult = ContinueResult()
        victoryValidators.forEach{
            result = it.validateVictory(map, playerToPieceColor(currentColor))
            if(result.isOver()) return result
        }
        return result
    }

    private fun getChessPieces(): ArrayList<ChessPiece>{
        val pieces = ArrayList<ChessPiece>()
        for((square, piece) in map){
            pieces.add(getChessPiece(piece, square))
        }
        return pieces
    }
    private fun changeCurrentColor() {
        currentColor = getOpponentColor()
    }
    private fun getChessPiece(piece: Piece, square: Square): ChessPiece{
        return ChessPiece(piece.id, pieceToPlayerColor(piece.color), squareToPosition(square), piece.getPieceId())
    }
    private fun pieceToPlayerColor(color: PieceColor) : PlayerColor{
        if(color== PieceColor.WHITE) return PlayerColor.WHITE
        return PlayerColor.BLACK
    }
    private fun playerToPieceColor(color: PlayerColor) : PieceColor {
        if(color==PlayerColor.WHITE) return PieceColor.WHITE
        return PieceColor.BLACK
    }
    private fun squareToPosition(square: Square) : Position {
        return Position(coordinateUIToSquare(square.vertical), square.horizontal+1)
    }


    private fun positionToSquare(position: Position): Square {
        val vertical = coordinateUIToSquare(position.component1())
        val horizontal = position.component2()-1
        return Square(vertical, horizontal)
    }

    private fun getPiece(square: Square): Piece?{
        return map[square]
    }


    private fun getOpponentColor(): PlayerColor{
        return if(currentColor == PlayerColor.WHITE) PlayerColor.BLACK
        else PlayerColor.WHITE
    }

    private fun coordinateUIToSquare(coordinate: Int) : Int {
        return boardSize - coordinate
    }
}