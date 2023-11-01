package edu.austral.dissis.chess.chess.victoryValidators

class CheckmateResult() : VictoryResult{
    override fun isOver(): Boolean {
        return true
    }

}