package edu.austral.dissis.chess.chess.validators.result

import edu.austral.dissis.chess.common.Square

class ValidWExecutionResult(val pieceId: String = "",
                            val fromSquare: Square = Square(-1, -1),
                            val toSquare: Square = Square(-1, -1)
) : ValidatorResult {
    override fun isValid(): Boolean {
        return true
    }

}