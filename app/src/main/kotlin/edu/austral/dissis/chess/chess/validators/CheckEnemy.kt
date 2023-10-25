package edu.austral.dissis.chess.chess.validators

import edu.austral.dissis.chess.chess.Piece
import edu.austral.dissis.chess.chess.PieceColor
import edu.austral.dissis.chess.chess.Square

class CheckEnemy : MovementValidator {
    override fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult {
        val fromColor = getColor(from, pieces)
        val toColor = getColor(to, pieces)
        if (fromColor != toColor && toColor != null) return ValidatorResult(TypeResult.VALID)
        return ValidatorResult(TypeResult.INVALID)
    }
    fun getColor(square: Square, pieces: Map<Square, Piece>) : PieceColor? {
        val piece = pieces.get(square)
        if (piece != null) {
            return piece.color
        }
        return null
    }
}