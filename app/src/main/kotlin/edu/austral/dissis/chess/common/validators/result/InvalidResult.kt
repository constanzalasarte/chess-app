package edu.austral.dissis.chess.common.validators.result

class InvalidResult : ValidatorResult {
    override fun isValid(): Boolean {
        return false
    }
}