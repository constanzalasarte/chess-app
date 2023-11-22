package edu.austral.dissis.boardGames.common.validators

import edu.austral.dissis.boardGames.common.Piece
import edu.austral.dissis.boardGames.common.Square
import edu.austral.dissis.boardGames.common.validators.result.InvalidResult
import edu.austral.dissis.boardGames.common.validators.result.ValidResult
import edu.austral.dissis.boardGames.common.validators.result.ValidatorResult

class EmptyDestinationSquare : MovementValidator {
    override fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult {
        if(squareEmpty(pieces, to)) return ValidResult()
        return InvalidResult()
    }

    private fun squareEmpty(
        pieces: Map<Square, Piece>,
        to: Square
    ) = pieces[to] == null
}