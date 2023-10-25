package edu.austral.dissis.chess.chess.validators

import edu.austral.dissis.chess.chess.Piece
import edu.austral.dissis.chess.chess.Square


class QuantityOfPieceMove(val quantity: Int) : MovementValidator{
    override fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult {
        val piece = pieces[from] ?: return ValidatorResult(TypeResult.INVALID)
        if (piece.moves == quantity) return ValidatorResult(TypeResult.VALID)
        return ValidatorResult(TypeResult.INVALID)
    }
}