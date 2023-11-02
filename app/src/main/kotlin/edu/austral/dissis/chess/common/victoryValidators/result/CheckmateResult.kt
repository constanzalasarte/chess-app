package edu.austral.dissis.chess.common.victoryValidators.result

class CheckmateResult() : VictoryResult {
    override fun isOver(): Boolean {
        return true
    }

}