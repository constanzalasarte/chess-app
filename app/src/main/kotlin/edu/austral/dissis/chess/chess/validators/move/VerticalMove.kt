package edu.austral.dissis.chess.chess.validators.move

import edu.austral.dissis.chess.common.Piece
import edu.austral.dissis.chess.common.Square
import edu.austral.dissis.chess.chess.validators.result.InvalidResult
import edu.austral.dissis.chess.chess.validators.result.ValidResult
import edu.austral.dissis.chess.chess.validators.result.ValidatorResult

class VerticalMove: MoveValidator() {
    override fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>) : ValidatorResult {
        val vertical = quantitySquaresBtw(from.vertical, to.vertical)
        val horizontal = quantitySquaresBtw(from.horizontal, to.horizontal)
        if(vertical != 0 && horizontal == 0 && checkIncrementByColor(vertical, pieces.get(from)!!.color)) return ValidResult()
        return InvalidResult()
    }
}