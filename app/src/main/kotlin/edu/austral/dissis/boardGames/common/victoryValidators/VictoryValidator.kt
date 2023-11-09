package edu.austral.dissis.boardGames.common.victoryValidators

import edu.austral.dissis.boardGames.common.victoryValidators.result.VictoryResult
import edu.austral.dissis.boardGames.common.Piece
import edu.austral.dissis.boardGames.common.PieceColor
import edu.austral.dissis.boardGames.common.Square


interface VictoryValidator {
    fun validateVictory(pieces: Map<Square, Piece>, currentPlayer: PieceColor) : VictoryResult
}