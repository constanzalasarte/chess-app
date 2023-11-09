package edu.austral.dissis.boardGames.checkers.victoryValidators

import edu.austral.dissis.boardGames.common.victoryValidators.result.ContinueResult
import edu.austral.dissis.boardGames.checkers.victoryValidators.result.NoMoreOpponentPieces
import edu.austral.dissis.boardGames.common.victoryValidators.result.VictoryResult
import edu.austral.dissis.boardGames.common.Piece
import edu.austral.dissis.boardGames.common.PieceColor
import edu.austral.dissis.boardGames.common.Square
import edu.austral.dissis.boardGames.common.victoryValidators.VictoryValidator

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