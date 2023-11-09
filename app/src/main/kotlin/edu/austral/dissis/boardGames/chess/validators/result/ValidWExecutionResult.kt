package edu.austral.dissis.boardGames.chess.validators.result

import edu.austral.dissis.boardGames.common.Square
import edu.austral.dissis.boardGames.common.validators.result.ValidatorResult

class ValidWExecutionResult(val pieceId: String = "",
                            val fromSquare: Square = Square(-1, -1),
                            val toSquare: Square = Square(-1, -1)
) : ValidatorResult {
    override fun isValid(): Boolean {
        return true
    }

}