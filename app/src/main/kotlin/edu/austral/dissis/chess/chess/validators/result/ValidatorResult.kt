package edu.austral.dissis.chess.chess.validators.result

import edu.austral.dissis.chess.gui.MoveResult


interface ValidatorResult{
    fun isValid(): Boolean
}