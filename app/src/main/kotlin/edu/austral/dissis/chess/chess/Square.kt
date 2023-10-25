package edu.austral.dissis.chess.chess

class Square (
    val vertical: Int,
    val horizontal: Int
    ){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Square) return false
        return other.vertical == this.vertical && other.horizontal == this.horizontal
    }

    override fun hashCode(): Int {
        var result = vertical
        result = 31 * result + horizontal
        return result
    }

    override fun toString(): String {
        return "(" + vertical + ", " + horizontal + ")"
    }
}