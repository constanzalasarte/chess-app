package edu.austral.dissis.boardGames.common

import edu.austral.dissis.chess.gui.PlayerColor

interface GameNextColor {
    fun getNextColor(pieces: Map<Square, Piece>, pieceColor: PieceColor): PieceColor
    fun movePiece(pieces: Map<Square, Piece>, from: Square, to: Square): Map<Square, Piece>
    fun invalidMove(pieces: Map<Square, Piece>, from: Square, to: Square): Map<Square, Piece>
}