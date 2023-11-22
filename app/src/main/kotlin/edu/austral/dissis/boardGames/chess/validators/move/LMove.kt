package edu.austral.dissis.boardGames.chess.validators.move

import edu.austral.dissis.boardGames.common.Piece
import edu.austral.dissis.boardGames.common.Square
import edu.austral.dissis.boardGames.common.validators.result.InvalidResult
import edu.austral.dissis.boardGames.common.validators.result.ValidResult
import edu.austral.dissis.boardGames.common.validators.result.ValidatorResult
import edu.austral.dissis.boardGames.common.validators.move.MoveValidator
import kotlin.math.abs

class LMove: MoveValidator() {
    override fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult {
        val vertical = quantitySquaresBtw(from.vertical, to.vertical)
        val horizontal = quantitySquaresBtw(from.horizontal, to.horizontal)
        var result = false
        if(equalsToTwo(vertical)) result = (abs(horizontal) == 1)
        else if(equalsToTwo(horizontal)) result = abs(vertical) == 1
        if(result && checkIncrementByColor(vertical, pieces.get(from)!!.color)) return ValidResult()
        return InvalidResult()
    }

    private fun equalsToTwo(vertical: Int) = abs(vertical) == 2
}