package edu.austral.dissis.boardGames.checkers.victoryValidators.result

import edu.austral.dissis.boardGames.common.victoryValidators.result.VictoryResult

class NoMoreOpponentPieces: VictoryResult {
    override fun isOver(): Boolean {
        return true
    }
}