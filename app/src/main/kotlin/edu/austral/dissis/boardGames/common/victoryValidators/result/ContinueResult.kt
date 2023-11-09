package edu.austral.dissis.boardGames.common.victoryValidators.result

class ContinueResult: VictoryResult {
    override fun isOver(): Boolean {
        return false
    }
}