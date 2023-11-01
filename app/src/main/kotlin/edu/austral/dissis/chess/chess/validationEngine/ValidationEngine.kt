package edu.austral.dissis.chess.chess.validationEngine

import edu.austral.dissis.chess.chess.Piece
import edu.austral.dissis.chess.chess.Square
import edu.austral.dissis.chess.chess.validators.result.ValidatorResult

interface ValidationEngine {
    fun move(from: Square, to: Square, pieces: Map<Square, Piece>) : ValidatorResult
}