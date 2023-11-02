package edu.austral.dissis.chess.common.victoryValidators.result

class ContinueResult: VictoryResult {
    override fun isOver(): Boolean {
        return false
    }
}