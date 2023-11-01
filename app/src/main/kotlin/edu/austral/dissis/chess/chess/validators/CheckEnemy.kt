package edu.austral.dissis.chess.chess.validators

import edu.austral.dissis.chess.chess.Piece
import edu.austral.dissis.chess.chess.PieceColor
import edu.austral.dissis.chess.chess.Square
import edu.austral.dissis.chess.chess.validators.result.InvalidResult
import edu.austral.dissis.chess.chess.validators.result.ValidResult
import edu.austral.dissis.chess.chess.validators.result.ValidatorResult

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