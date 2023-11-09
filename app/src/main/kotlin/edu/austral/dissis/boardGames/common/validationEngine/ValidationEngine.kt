package edu.austral.dissis.boardGames.common.validationEngine

import edu.austral.dissis.boardGames.common.Piece
import edu.austral.dissis.boardGames.common.Square
import edu.austral.dissis.boardGames.common.validators.result.ValidatorResult

interface ValidationEngine {
    fun move(from: Square, to: Square, pieces: Map<Square, Piece>) : ValidatorResult
}