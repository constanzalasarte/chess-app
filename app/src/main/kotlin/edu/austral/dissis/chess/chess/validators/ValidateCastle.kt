package edu.austral.dissis.chess.chess.validators

import edu.austral.dissis.chess.chess.ChessPiece
import edu.austral.dissis.chess.chess.Piece
import edu.austral.dissis.chess.chess.Square
import kotlin.math.abs

class ValidateCastle : MovementValidator {
    override fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult {
        val availablePieces = ArrayList<Square>()

        for ((square, piece) in pieces.entries)
            if (checkPieceRook(piece.chessPiece) &&
                checkPieceColor(piece, pieces[from]) && piece.moves == 0)
                availablePieces.add(square)
        if(availablePieces.isEmpty()) return ValidatorResult(TypeResult.INVALID)
        return minSquareDistance(to, availablePieces, pieces)
    }
    private fun checkPieceRook(chessPiece: ChessPiece): Boolean{
        return chessPiece == ChessPiece.ROOK
    }
    private fun checkPieceColor(piece: Piece, other: Piece?): Boolean{
        return piece.color == other?.color
    }
    private fun minSquareDistance(to: Square, squares: List<Square>, map: Map<Square, Piece>): ValidatorResult {
        var minDistance = to.horizontal - squares[0].horizontal
        var minSquareDistance = squares[0]
        for(square in squares) {
            if(abs(minDistance) > abs(to.horizontal - square.horizontal)){
                minDistance = abs(to.horizontal - square.horizontal)
                minSquareDistance = square
            }
        }
        val toSquare = Square(to.vertical, horizontalToSquare(minDistance, minSquareDistance))
        return ValidatorResult(TypeResult.VALID_WITH_EXECUTION, data = map[minSquareDistance]!!.id,fromSquare = minSquareDistance, toSquare = toSquare)
    }
    private fun horizontalToSquare(minDistance: Int, square: Square): Int {
        if(minDistance > 0) return minDistance + 1
        return square.horizontal - minDistance - 1
    }
}