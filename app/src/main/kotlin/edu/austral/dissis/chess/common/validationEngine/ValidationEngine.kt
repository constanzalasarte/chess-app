package edu.austral.dissis.chess.common.validationEngine

import edu.austral.dissis.chess.common.Piece
import edu.austral.dissis.chess.common.Square
import edu.austral.dissis.chess.common.validators.result.ValidatorResult

interface ValidationEngine {
    fun move(from: Square, to: Square, pieces: Map<Square, Piece>) : ValidatorResult
}