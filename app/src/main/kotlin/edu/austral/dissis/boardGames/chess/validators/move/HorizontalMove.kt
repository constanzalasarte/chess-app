package edu.austral.dissis.boardGames.chess.validators.move

import edu.austral.dissis.boardGames.common.Piece
import edu.austral.dissis.boardGames.common.Square
import edu.austral.dissis.boardGames.common.validators.result.InvalidResult
import edu.austral.dissis.boardGames.common.validators.result.ValidResult
import edu.austral.dissis.boardGames.common.validators.result.ValidatorResult
import edu.austral.dissis.boardGames.common.validators.move.MoveValidator


class HorizontalMove: MoveValidator() {
    override fun validateMove(from: Square, to: Square, pieces: Map<Square, Piece>): ValidatorResult {
        val vertical = quantitySquaresBtw(from.vertical, to.vertical)
        val horizontal = quantitySquaresBtw(from.horizontal, to.horizontal)
        if(checkHorizontal(horizontal, vertical)) return ValidResult()
        return InvalidResult()
    }

    private fun checkHorizontal(horizontal: Int, vertical: Int) = horizontal != 0 && vertical == 0
}