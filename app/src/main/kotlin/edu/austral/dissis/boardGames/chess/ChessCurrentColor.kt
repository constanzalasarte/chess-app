package edu.austral.dissis.boardGames.chess

import edu.austral.dissis.boardGames.common.GameNextColor
import edu.austral.dissis.boardGames.common.Piece
import edu.austral.dissis.boardGames.common.Square
import edu.austral.dissis.chess.gui.PlayerColor

class ChessNextColor : GameNextColor {
    override fun getNextColor(pieces: Map<Square, Piece>, currentPlayer: PlayerColor): PlayerColor {
        return if(currentPlayer == PlayerColor.WHITE) PlayerColor.BLACK
        else PlayerColor.WHITE
    }

    override fun movePiece(pieces: Map<Square, Piece>, from: Square, to: Square): Map<Square, Piece> {
        val piece : Piece = pieces[from] ?: return emptyMap()
        piece.incrementMove()
        return pieces.filter { (square, _) ->  square != from} + (to to piece)
    }

    override fun invalidMove(pieces: Map<Square, Piece>, from: Square, to: Square): Map<Square, Piece> {
        TODO("Not yet implemented")
    }
}