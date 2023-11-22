package edu.austral.dissis.boardGames.common.validators.move

import edu.austral.dissis.boardGames.common.validators.result.InvalidResult
import edu.austral.dissis.boardGames.common.validators.result.ValidResult
import edu.austral.dissis.boardGames.common.validators.result.ValidatorResult
import edu.austral.dissis.boardGames.common.Piece
import edu.austral.dissis.boardGames.common.Square
import kotlin.math.abs

class QuantityOfMoves(private val quantity: Int, private val type: MoveType) : MoveValidator() {
    override fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult {
        if(isVerticalOrDiagonal()) return checkVertical(from, to)
        return checkHorizontal(from, to)
    }

    private fun isVerticalOrDiagonal() = type == MoveType.VERTICAL || type == MoveType.DIAGONAL

    private fun checkVertical(from: Square, to: Square): ValidatorResult {
        val vertical = quantitySquaresBtw(from.vertical, to.vertical)
        if(checkQuantity(vertical)) return ValidResult()
        return InvalidResult()
    }

    private fun checkHorizontal(from: Square, to: Square): ValidatorResult {
        val horizontal = quantitySquaresBtw(from.horizontal, to.horizontal)
        if(checkQuantity(horizontal)) return ValidResult()
        return InvalidResult()
    }

    private fun checkQuantity(vertical: Int) = abs(vertical) == quantity
}