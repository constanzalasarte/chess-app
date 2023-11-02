package edu.austral.dissis.chess.common.validators

import edu.austral.dissis.chess.common.Piece
import edu.austral.dissis.chess.common.Square
import edu.austral.dissis.chess.common.validators.result.InvalidResult
import edu.austral.dissis.chess.common.validators.result.ValidResult
import edu.austral.dissis.chess.common.validators.result.ValidatorResult

class CheckDestinationSquare : MovementValidator {
    override fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult {
        if(pieces[from]?.color != pieces[to]?.color) return ValidResult()
        return InvalidResult()
    }
}