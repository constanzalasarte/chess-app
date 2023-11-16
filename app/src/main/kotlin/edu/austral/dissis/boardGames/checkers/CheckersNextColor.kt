package edu.austral.dissis.boardGames.checkers

import edu.austral.dissis.boardGames.checkers.victoryValidators.result.NoMoreOpponentPieces
import edu.austral.dissis.boardGames.common.*
import edu.austral.dissis.boardGames.common.validators.result.ValidatorResult
import edu.austral.dissis.boardGames.common.victoryValidators.result.ContinueResult
import edu.austral.dissis.boardGames.common.victoryValidators.result.VictoryResult
import kotlin.math.abs

data class InitialState(val squares: List<Square>, val opponentSquares: List<Square>)

class CheckersNextColor: GameNextColor {

    override fun getNextColor(pieces: Map<Square, Piece>, pieceColor: PieceColor): PieceColor {
        val result = validate(pieces, pieceColor)
        return if(result.isOver()) pieceColor
        else opponentColor(pieceColor)
    }

    override fun movePiece(pieces: Map<Square, Piece>, from: Square, to: Square): Map<Square, Piece> {
        if(getSquareDistance(from.vertical, to.vertical) == 1) return classicMovePiece(pieces, from, to)
        val square = getObstacleSquare(from, to)
        val newMap = pieces.filter { (s, _) ->  s != square}
        return classicMovePiece(newMap, from, to)
    }

    override fun invalidMove(pieces: Map<Square, Piece>, from: Square, to: Square): Map<Square, Piece> {
        return pieces
    }

    private fun getObstacleSquare(from: Square, to: Square): Square {
        val vertical = quantitySquaresBtw(from.vertical, to.vertical)
        val horizontal = quantitySquaresBtw(from.horizontal, to.horizontal)
        return Square(from.vertical + vertical, from.horizontal + horizontal)
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

    private fun opponentColor(pieceColor: PieceColor): PieceColor{
        return if(pieceColor == PieceColor.WHITE) PieceColor.BLACK
        else PieceColor.WHITE
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
        if(state.opponentSquares != initialState.opponentSquares) return NoMoreOpponentPieces()
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
        val vertical = quantitySquaresOut(from.vertical, to.vertical)
        val horizontal = quantitySquaresOut(from.horizontal, to.horizontal)
        val square = Square(from.vertical + vertical, from.horizontal + horizontal)
        return pieces[from]!!.validationMapper.move(from, square, pieces)
    }
    private fun quantitySquaresOut(from: Int, to: Int): Int {
        val difference = to - from
        return if(difference<0) difference - 1
        else difference + 1
    }

    private fun getInitialState(pieces: Map<Square, Piece>, currentPlayer: PieceColor): InitialState {
        val squares = getSquaresByColor(pieces, currentPlayer)
        val opponentSquares = getSquaresByColor(pieces, getOpponentColor(currentPlayer))
        return InitialState(squares, opponentSquares)

    }

}