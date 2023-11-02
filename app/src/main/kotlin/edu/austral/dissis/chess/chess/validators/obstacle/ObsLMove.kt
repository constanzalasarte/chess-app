package edu.austral.dissis.chess.chess.validators.obstacle
import edu.austral.dissis.chess.common.Piece
import edu.austral.dissis.chess.common.Square
import edu.austral.dissis.chess.common.validators.result.ValidResult
import edu.austral.dissis.chess.common.validators.result.ValidatorResult
import edu.austral.dissis.chess.common.validators.obstacle.ObstaclePiece

class ObsLMove: ObstaclePiece() {
    override fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult {
        val lMove:List<Int> = getLMove(from, to)
        val vertical = lMove[0]
        val horizontal = lMove[1]
        return ValidResult()

    }

    private fun getLMove(from: Square, to: Square): List<Int>{
        val vertical = to.vertical - from.vertical
        val horizontal = to.horizontal - from.horizontal
        return listOf(vertical, horizontal)
    }
    
}