package edu.austral.dissis.chess.chess.victoryValidators

import edu.austral.dissis.chess.chess.Piece
import edu.austral.dissis.chess.chess.PieceColor
import edu.austral.dissis.chess.chess.Square


interface VictoryValidator {
    fun validateVictory(pieces: Map<Square, Piece>, currentPlayer: PieceColor) : VictoryResult
}