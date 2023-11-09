package edu.austral.dissis.boardGames.common.validators

import edu.austral.dissis.boardGames.common.Piece
import edu.austral.dissis.boardGames.common.Square
import edu.austral.dissis.boardGames.common.validators.result.ValidatorResult

interface MovementValidator{
    fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult
}