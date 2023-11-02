package edu.austral.dissis.chess.checkers

import edu.austral.dissis.chess.chess.victoryValidators.CheckmateResult
import edu.austral.dissis.chess.common.*
import edu.austral.dissis.chess.common.validators.result.ValidatorResult
import edu.austral.dissis.chess.common.victoryValidators.result.ContinueResult
import edu.austral.dissis.chess.common.victoryValidators.result.VictoryResult
import edu.austral.dissis.chess.gui.PlayerColor
import kotlin.math.abs

data class InitialState(val squares: List<Square>, val opponentSquares: List<Square>)

class CheckersNextColor: GameNextColor {

    override fun getNextColor(pieces: Map<Square, Piece>, currentPlayer: PlayerColor): PlayerColor {
        val pieceColor: PieceColor = if(currentPlayer == PlayerColor.WHITE) PieceColor.WHITE
        else PieceColor.BLACK
        val result = validate(pieces, pieceColor)
        if(result.isOver()) return currentPlayer
        else return opponentColor(pieceColor)
    }

    override fun movePiece(pieces: Map<Square, Piece>, from: Square, to: Square): Map<Square, Piece> {
        if(getSquareDistance(from.vertical, to.vertical) == 1) return classicMovePiece(pieces, from, to)
        val square = getObstacleSquare(from, to)
        val newMap = pieces.filter { (s, _) ->  s != square}
        return classicMovePiece(newMap, from, to)
    }

    private fun getObstacleSquare(from: Square, to: Square): Square {
        val diff = quantitySquaresBtw(from.vertical, to.vertical)
        return Square(from.vertical + diff, from.horizontal + diff)

    }
    private fun quantitySquaresBtw(from: Int, to: Int): Int {
        val difference = to - from
        return if(difference<0) difference + 1
        else difference - 1
    }

    private fun getSquareDistance(from: Int, to: Int): Int{
        return abs(to - from)
    }

    private fun classicMovePiece(pieces: Map<Square, Piece>, from: Square, to: Square): Map<Square, Piece> {
        val piece : Piece = pieces[from] ?: return emptyMap()
        piece.incrementMove()
        return pieces.filter { (square, _) ->  square != from} + (to to piece)
    }

    private fun opponentColor(pieceColor: PieceColor): PlayerColor{
        return if(pieceColor == PieceColor.WHITE) PlayerColor.BLACK
        else PlayerColor.WHITE
    }

    private fun validate(pieces: Map<Square, Piece>, currentPlayer: PieceColor): VictoryResult {
        val initialState = getInitialState(pieces, currentPlayer)
        var state = getInitialState(pieces, currentPlayer)

        var oppIndex : Int
        var typeResult : ValidatorResult
        for (square in state.squares){
            oppIndex = 0
            while (checkSizeOfOppSquares(state, oppIndex)){
                typeResult = getValidatorResult(square, state.opponentSquares[oppIndex], pieces)
                if(typeResult.isValid())
                    state = changeState(state, oppIndex)
                else oppIndex++
            }
        }
        if(state.opponentSquares != initialState.opponentSquares) return CheckmateResult()
        return ContinueResult()
    }

    private fun checkSizeOfOppSquares(
        state: InitialState,
        oppIndex: Int
    ) = state.opponentSquares.isNotEmpty() && oppIndex < state.opponentSquares.size

    private fun changeState(
        state: InitialState,
        oppIndex: Int
    ) = InitialState(state.squares, state.opponentSquares.filterIndexed { index, _ -> index != oppIndex })

    private fun getSquaresByColor(pieces: Map<Square, Piece>, color: PieceColor): List<Square> {
        return pieces
            .filter{ it.value.color == color}
            .map { it.key }
    }
    private fun getOpponentColor(currentPlayer: PieceColor): PieceColor {
        if(currentPlayer == PieceColor.BLACK) return PieceColor.WHITE
        return PieceColor.BLACK
    }
    private fun getValidatorResult(from: Square, to: Square, pieces:Map<Square, Piece>): ValidatorResult {
        return pieces[from]!!.validationMapper.move(from, to, pieces)
    }

    private fun getInitialState(pieces: Map<Square, Piece>, currentPlayer: PieceColor): InitialState {
        val squares = getSquaresByColor(pieces, currentPlayer)
        val opponentSquares = getSquaresByColor(pieces, getOpponentColor(currentPlayer))
        return InitialState(squares, opponentSquares)

    }

}