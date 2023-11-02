package edu.austral.dissis.chess.common.victoryValidators.result

class NoMoreOpponentPieces: VictoryResult {
    override fun isOver(): Boolean {
        return true
    }
}