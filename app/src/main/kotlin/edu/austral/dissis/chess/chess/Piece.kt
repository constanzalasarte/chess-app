package edu.austral.dissis.chess.chess

import edu.austral.dissis.chess.chess.validationEngine.ValidationEngine
import edu.austral.dissis.chess.chess.validators.ValidatorResult

class Piece(
//    var active: Boolean,
    var id: String,
    var color: PieceColor,
    var chessPiece: ChessPiece,
    var moves: Int,
    var validationMapper: ValidationEngine
){
    override fun toString(): String {
        return "$chessPiece $color"
    }

    fun move(to: Square, from: Square, pieces: Map<Square, Piece>): ValidatorResult {
        return validationMapper.move(to, from, pieces)
    }
}