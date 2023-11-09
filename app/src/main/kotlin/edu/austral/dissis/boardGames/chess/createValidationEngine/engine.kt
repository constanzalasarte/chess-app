package edu.austral.dissis.boardGames.chess.createValidationEngine
import edu.austral.dissis.boardGames.common.validationEngine.ValidationEngine
interface Engine {
    fun pawnEngine(): ValidationEngine
    fun horseEngine(): ValidationEngine
    fun bishopEngine(): ValidationEngine
    fun rookEngine(): ValidationEngine
    fun queenEngine(): ValidationEngine
    fun kingEngine(): ValidationEngine
}