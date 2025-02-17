package edu.austral.dissis.boardGames.common.validators.move

import edu.austral.dissis.boardGames.common.validators.result.InvalidResult
import edu.austral.dissis.boardGames.common.validators.result.ValidResult
import edu.austral.dissis.boardGames.common.validators.result.ValidatorResult
import edu.austral.dissis.boardGames.common.Piece
import edu.austral.dissis.boardGames.common.Square
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