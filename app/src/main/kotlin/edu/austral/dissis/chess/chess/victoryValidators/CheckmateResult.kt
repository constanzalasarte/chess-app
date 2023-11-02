package edu.austral.dissis.chess.chess.victoryValidators

import edu.austral.dissis.chess.common.victoryValidators.result.VictoryResult

class CheckmateResult() : VictoryResult {
    override fun isOver(): Boolean {
        return true
    }

}