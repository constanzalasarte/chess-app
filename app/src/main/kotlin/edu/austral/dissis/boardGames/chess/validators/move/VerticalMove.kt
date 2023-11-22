package edu.austral.dissis.boardGames.chess.validators.move

import edu.austral.dissis.boardGames.common.Piece
import edu.austral.dissis.boardGames.common.Square
import edu.austral.dissis.boardGames.common.validators.result.InvalidResult
import edu.austral.dissis.boardGames.common.validators.result.ValidResult
import edu.austral.dissis.boardGames.common.validators.result.ValidatorResult
import edu.austral.dissis.boardGames.common.validators.move.MoveValidator

class VerticalMove: MoveValidator() {
    override fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>) : ValidatorResult {
        val vertical = quantitySquaresBtw(from.vertical, to.vertical)
        val horizontal = quantitySquaresBtw(from.horizontal, to.horizontal)
        if(checkVertical(vertical, horizontal) && checkIncrementByColor(vertical, pieces.get(from)!!.color)) return ValidResult()
        return InvalidResult()
    }

    private fun checkVertical(vertical: Int, horizontal: Int) = vertical != 0 && horizontal == 0
}