package edu.austral.dissis.chess.chess

class Board (var map: Map<Square, Piece?>){
    fun move(from: Square, to: Square) : Boolean {
        return true;
    }
    fun isSquareOccupied(square: Square) : Boolean {
        return true;
    }
}