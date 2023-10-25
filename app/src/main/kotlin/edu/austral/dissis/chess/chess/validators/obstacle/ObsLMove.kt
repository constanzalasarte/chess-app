package edu.austral.dissis.chess.chess.validators.obstacle
import edu.austral.dissis.chess.chess.Piece
import edu.austral.dissis.chess.chess.Square
import edu.austral.dissis.chess.chess.validators.TypeResult
import edu.austral.dissis.chess.chess.validators.ValidatorResult

class ObsLMove: ObstaclePiece() {
    override fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>):ValidatorResult{
        val lMove:List<Int> = getLMove(from, to)
        val vertical = lMove[0]
        val horizontal = lMove[1]
        return ValidatorResult(TypeResult.VALID)

    }

    private fun getLMove(from: Square, to: Square): List<Int>{
        val vertical = to.vertical - from.vertical
        val horizontal = to.horizontal - from.horizontal
        return listOf(vertical, horizontal)
    }
    
}