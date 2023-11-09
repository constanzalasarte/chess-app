package edu.austral.dissis.boardGames.chess.victoryValidators

import edu.austral.dissis.boardGames.common.victoryValidators.result.VictoryResult

class CheckmateResult() : VictoryResult {
    override fun isOver(): Boolean {
        return true
    }

}