package edu.austral.dissis.chess.chess.validators.move


import edu.austral.dissis.chess.chess.Piece
import edu.austral.dissis.chess.chess.Square
import edu.austral.dissis.chess.chess.validators.result.InvalidResult
import edu.austral.dissis.chess.chess.validators.result.ValidResult
import edu.austral.dissis.chess.chess.validators.result.ValidatorResult
import kotlin.math.abs

class QuantityOfMoves(private val quantity: Int, private val type: MoveType) : MoveValidator() {
    override fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult {
        if(type == MoveType.VERTICAL || type == MoveType.DIAGONAL) return checkVertical(from, to)
        return checkHorizontal(from, to)
    }

    private fun checkVertical(from: Square, to: Square): ValidatorResult {
        val vertical = quantitySquaresBtw(from.vertical, to.vertical)
        if(abs(vertical) == quantity) return ValidResult()
        return InvalidResult()
    }

    private fun checkHorizontal(from: Square, to: Square): ValidatorResult {
        val horizontal = quantitySquaresBtw(from.horizontal, to.horizontal)
        if(abs(horizontal) == quantity) return ValidResult()
        return InvalidResult()
    }
}