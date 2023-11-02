package edu.austral.dissis.chess.common

import edu.austral.dissis.chess.common.validationEngine.ValidationEngine
import edu.austral.dissis.chess.common.validators.result.ValidatorResult

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

    fun getPieceId(): String {
        if(chessPiece == ChessPiece.HORSE) return "knight"
        return chessPiece.toString().lowercase()
    }
    fun incrementMove(){
        moves++
    }
}