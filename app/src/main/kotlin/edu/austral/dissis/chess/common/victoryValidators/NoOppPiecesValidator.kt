package edu.austral.dissis.chess.common.victoryValidators

import edu.austral.dissis.chess.common.victoryValidators.result.ContinueResult
import edu.austral.dissis.chess.common.victoryValidators.result.NoMoreOpponentPieces
import edu.austral.dissis.chess.common.victoryValidators.result.VictoryResult
import edu.austral.dissis.chess.common.Piece
import edu.austral.dissis.chess.common.PieceColor
import edu.austral.dissis.chess.common.Square

class NoOppPiecesValidator : VictoryValidator {
    override fun validateVictory(pieces: Map<Square, Piece>, currentPlayer: PieceColor): VictoryResult {
        val oppPieces = pieces
            .filter{ it.value.color != currentPlayer}
            .map { it.key }
        if (oppPieces.isEmpty()) return NoMoreOpponentPieces()
        return ContinueResult()
    }
}