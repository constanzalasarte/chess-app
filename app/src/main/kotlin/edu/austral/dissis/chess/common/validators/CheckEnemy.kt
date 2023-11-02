package edu.austral.dissis.chess.common.validators

import edu.austral.dissis.chess.common.Piece
import edu.austral.dissis.chess.common.PieceColor
import edu.austral.dissis.chess.common.Square
import edu.austral.dissis.chess.common.validators.result.InvalidResult
import edu.austral.dissis.chess.common.validators.result.ValidResult
import edu.austral.dissis.chess.common.validators.result.ValidatorResult

class CheckEnemy : MovementValidator {
    override fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult {
        val fromColor = getColor(from, pieces)
        val toColor = getColor(to, pieces)
        if (fromColor != toColor && toColor != null) return ValidResult()
        return InvalidResult()
    }
    fun getColor(square: Square, pieces: Map<Square, Piece>) : PieceColor? {
        val piece = pieces.get(square)
        if (piece != null) {
            return piece.color
        }
        return null
    }
}