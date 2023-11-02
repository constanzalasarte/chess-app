package edu.austral.dissis.chess.chess.validators.move

import edu.austral.dissis.chess.common.Piece
import edu.austral.dissis.chess.common.Square
import edu.austral.dissis.chess.chess.validators.result.InvalidResult
import edu.austral.dissis.chess.chess.validators.result.ValidResult
import edu.austral.dissis.chess.chess.validators.result.ValidatorResult
import kotlin.math.abs

class LMove: MoveValidator() {
    override fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult {
        val vertical = quantitySquaresBtw(from.vertical, to.vertical)
        val horizontal = quantitySquaresBtw(from.horizontal, to.horizontal)
        var result = false
        if(abs(vertical) == 2) result = (abs(horizontal) == 1)
        else if(abs(horizontal) == 2) result = abs(vertical) == 1
        if(result && checkIncrementByColor(vertical, pieces.get(from)!!.color)) return ValidResult()
        return InvalidResult()
    }
}