package edu.austral.dissis.chess.chess.validators.move

import edu.austral.dissis.chess.common.Piece
import edu.austral.dissis.chess.common.Square
import edu.austral.dissis.chess.chess.validators.result.InvalidResult
import edu.austral.dissis.chess.chess.validators.result.ValidResult
import edu.austral.dissis.chess.chess.validators.result.ValidatorResult
import kotlin.math.abs


class DiagonalMove : MoveValidator() {
    override fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult {
        val vertical = quantitySquaresBtw(from.vertical, to.vertical)
        val horizontal = quantitySquaresBtw(from.horizontal, to.horizontal)
        if (checkMove(vertical, horizontal, pieces, from)) return ValidResult()
        return InvalidResult()
    }

    private fun checkMove(
        vertical: Int,
        horizontal: Int,
        pieces: Map<Square, Piece>,
        from: Square
    ) = abs(vertical) == abs(horizontal) && vertical != 0 && checkIncrementByColor(vertical, pieces.get(from)!!.color)
}