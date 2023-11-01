package edu.austral.dissis.chess.chess.validators.result

import edu.austral.dissis.chess.gui.InvalidMove
import edu.austral.dissis.chess.gui.MoveResult

class InvalidResult : ValidatorResult {
    override fun isValid(): Boolean {
        return false
    }
}