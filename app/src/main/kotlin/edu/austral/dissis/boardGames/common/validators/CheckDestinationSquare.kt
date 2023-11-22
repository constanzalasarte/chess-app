package edu.austral.dissis.boardGames.common.validators

import edu.austral.dissis.boardGames.common.Piece
import edu.austral.dissis.boardGames.common.Square
import edu.austral.dissis.boardGames.common.validators.result.InvalidResult
import edu.austral.dissis.boardGames.common.validators.result.ValidResult
import edu.austral.dissis.boardGames.common.validators.result.ValidatorResult

class CheckDestinationSquare : MovementValidator {
    override fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult {
        if(colorIsDiff(pieces, from, to)) return ValidResult()
        return InvalidResult()
    }

    private fun colorIsDiff(
        pieces: Map<Square, Piece>,
        from: Square,
        to: Square
    ) = pieces[from]?.color != pieces[to]?.color
}