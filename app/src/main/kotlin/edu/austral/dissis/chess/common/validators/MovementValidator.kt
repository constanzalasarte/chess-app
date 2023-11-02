package edu.austral.dissis.chess.common.validators

import edu.austral.dissis.chess.common.Piece
import edu.austral.dissis.chess.common.Square
import edu.austral.dissis.chess.common.validators.result.ValidatorResult

interface MovementValidator{
    fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult
}