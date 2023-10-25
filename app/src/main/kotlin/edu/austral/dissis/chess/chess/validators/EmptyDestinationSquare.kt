package edu.austral.dissis.chess.chess.validators

import edu.austral.dissis.chess.chess.Piece
import edu.austral.dissis.chess.chess.Square

class EmptyDestinationSquare : MovementValidator {
    override fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult {
        if(pieces[to]== null) return ValidatorResult(TypeResult.VALID)
        return ValidatorResult(TypeResult.INVALID)
    }
}