package edu.austral.dissis.chess.common.validators.move

import edu.austral.dissis.chess.common.validators.result.InvalidResult
import edu.austral.dissis.chess.common.validators.result.ValidResult
import edu.austral.dissis.chess.common.validators.result.ValidatorResult
import edu.austral.dissis.chess.common.Piece
import edu.austral.dissis.chess.common.Square
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