package edu.austral.dissis.chess.common.validators

import edu.austral.dissis.chess.common.Piece
import edu.austral.dissis.chess.common.Square
import edu.austral.dissis.chess.common.validators.result.InvalidResult
import edu.austral.dissis.chess.common.validators.result.ValidResult
import edu.austral.dissis.chess.common.validators.result.ValidatorResult


class QuantityOfPieceMove(val quantity: Int) : MovementValidator {
    override fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult {
        val piece = pieces[from] ?: return InvalidResult()
        if (piece.moves == quantity) return ValidResult()
        return InvalidResult()
    }
}