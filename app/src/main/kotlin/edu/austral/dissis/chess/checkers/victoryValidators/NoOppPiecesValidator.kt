package edu.austral.dissis.chess.checkers.victoryValidators

import edu.austral.dissis.chess.common.victoryValidators.result.ContinueResult
import edu.austral.dissis.chess.checkers.victoryValidators.result.NoMoreOpponentPieces
import edu.austral.dissis.chess.common.victoryValidators.result.VictoryResult
import edu.austral.dissis.chess.common.Piece
import edu.austral.dissis.chess.common.PieceColor
import edu.austral.dissis.chess.common.Square
import edu.austral.dissis.chess.common.victoryValidators.VictoryValidator

class NoOppPiecesValidator : VictoryValidator {
    override fun validateVictory(pieces: Map<Square, Piece>, currentPlayer: PieceColor): VictoryResult {
        val oppPieces = pieces
            .filter{ it.value.color != currentPlayer}
            .map { it.key }
        if (oppPieces.isEmpty()) {
            println("no mor opp")
            return NoMoreOpponentPieces()
        }
        return ContinueResult()
    }
}