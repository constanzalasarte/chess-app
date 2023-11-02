package edu.austral.dissis.chess.common.victoryValidators

import edu.austral.dissis.chess.common.victoryValidators.result.VictoryResult
import edu.austral.dissis.chess.common.Piece
import edu.austral.dissis.chess.common.PieceColor
import edu.austral.dissis.chess.common.Square


interface VictoryValidator {
    fun validateVictory(pieces: Map<Square, Piece>, currentPlayer: PieceColor) : VictoryResult
}