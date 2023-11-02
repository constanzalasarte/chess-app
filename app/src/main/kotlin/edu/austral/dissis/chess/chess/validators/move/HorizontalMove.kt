package edu.austral.dissis.chess.chess.validators.move

import edu.austral.dissis.chess.common.Piece
import edu.austral.dissis.chess.common.Square
import edu.austral.dissis.chess.common.validators.result.InvalidResult
import edu.austral.dissis.chess.common.validators.result.ValidResult
import edu.austral.dissis.chess.common.validators.result.ValidatorResult
import edu.austral.dissis.chess.common.validators.move.MoveValidator


class HorizontalMove: MoveValidator() {
    override fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult {
        val vertical = quantitySquaresBtw(from.vertical, to.vertical)
        val horizontal = quantitySquaresBtw(from.horizontal, to.horizontal)
        if(horizontal != 0 && vertical == 0) return ValidResult()
        return InvalidResult()
    }
}