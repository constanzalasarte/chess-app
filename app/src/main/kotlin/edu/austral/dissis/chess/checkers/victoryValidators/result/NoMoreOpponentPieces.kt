package edu.austral.dissis.chess.checkers.victoryValidators.result

import edu.austral.dissis.chess.common.victoryValidators.result.VictoryResult

class NoMoreOpponentPieces: VictoryResult {
    override fun isOver(): Boolean {
        return true
    }
}