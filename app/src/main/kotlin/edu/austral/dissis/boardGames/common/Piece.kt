package edu.austral.dissis.boardGames.common

import edu.austral.dissis.boardGames.common.validationEngine.ValidationEngine
import edu.austral.dissis.boardGames.common.validators.result.ValidatorResult

class Piece(
//    var active: Boolean,
    var id: String,
    var color: PieceColor,
    var chessPiece: MyPiece,
    var moves: Int,
    var validationMapper: ValidationEngine
){
    override fun toString(): String {
        return "$chessPiece $color"
    }

    fun move(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult {
        return validationMapper.move(from, to, pieces)
    }

    fun getPieceId(): String {
        if(checkHorsePiece()) return "knight"
        return chessPiece.toString().lowercase()
    }

    private fun checkHorsePiece() = chessPiece == MyPiece.HORSE
    fun incrementMove(){
        moves++
    }
}