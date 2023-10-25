package edu.austral.dissis.chess.chess.validators.move

import edu.austral.dissis.chess.chess.Piece
import edu.austral.dissis.chess.chess.Square
import edu.austral.dissis.chess.chess.validators.TypeResult
import edu.austral.dissis.chess.chess.validators.ValidatorResult


class HorizontalMove: MoveValidator() {
    override fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult {
        val vertical = quantitySquaresBtw(from.vertical, to.vertical)
        val horizontal = quantitySquaresBtw(from.horizontal, to.horizontal)
        if(horizontal != 0 && vertical == 0) return ValidatorResult(TypeResult.VALID)
        return ValidatorResult(TypeResult.INVALID, "The movement is not horizontal")
    }
}