package edu.austral.dissis.chess.chess.validators

import edu.austral.dissis.chess.chess.Piece
import edu.austral.dissis.chess.chess.Square
import edu.austral.dissis.chess.chess.validators.result.ValidatorResult

interface MovementValidator{
    fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult
}