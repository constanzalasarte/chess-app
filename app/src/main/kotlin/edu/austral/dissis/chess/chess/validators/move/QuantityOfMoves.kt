package edu.austral.dissis.chess.chess.validators.move


import edu.austral.dissis.chess.chess.Piece
import edu.austral.dissis.chess.chess.Square
import edu.austral.dissis.chess.chess.validators.TypeResult
import edu.austral.dissis.chess.chess.validators.ValidatorResult
import kotlin.math.abs

class QuantityOfMoves(private val quantity: Int, private val type: MoveType) : MoveValidator() {
    override fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult {
        if(type == MoveType.VERTICAL || type == MoveType.DIAGONAL) return checkVertical(from, to)
        return checkHorizontal(from, to)
    }

    private fun checkVertical(from: Square, to: Square): ValidatorResult {
        val vertical = quantitySquaresBtw(from.vertical, to.vertical)
        if(abs(vertical) == quantity) return ValidatorResult(TypeResult.VALID)
        return ValidatorResult(TypeResult.INVALID, "It can't move $quantity squares")
    }

    private fun checkHorizontal(from: Square, to: Square): ValidatorResult {
        val horizontal = quantitySquaresBtw(from.horizontal, to.horizontal)
        if(abs(horizontal) == quantity) return ValidatorResult(TypeResult.VALID)
        return ValidatorResult(TypeResult.INVALID, "It can't move $quantity squares")
    }
}