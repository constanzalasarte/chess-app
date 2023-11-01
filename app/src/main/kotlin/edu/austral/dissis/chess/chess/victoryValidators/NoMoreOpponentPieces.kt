package edu.austral.dissis.chess.chess.victoryValidators

class NoMoreOpponentPieces: VictoryResult {
    override fun isOver(): Boolean {
        return true
    }
}