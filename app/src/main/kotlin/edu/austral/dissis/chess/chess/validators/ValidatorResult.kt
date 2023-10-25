package edu.austral.dissis.chess.chess.validators

import edu.austral.dissis.chess.chess.ChessPiece
import edu.austral.dissis.chess.chess.Square


class ValidatorResult (val typeResult: TypeResult,
                       val data: String = "",
                       val piece: ChessPiece = ChessPiece.ROOK,
                       val fromSquare: Square = Square(-1, -1),
                       val toSquare: Square = Square(-1, -1),
    ){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ValidatorResult) return false
        return other.typeResult == this.typeResult && other.data == this.data
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return "$typeResult $data"
    }

}