package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    // New solution which works best for larger sets
    var rightPosition = 0
    var wrongPosition = 0
    val notMatchedChars = hashMapOf<Char, Int>()
    for(i in 0..3) {
        if(guess[i] == secret[i]) {
            rightPosition++
        }
        else {
            if(notMatchedChars.containsKey(secret[i])) {
                notMatchedChars[secret[i]] = notMatchedChars[secret[i]]!! + 1
            } else {
                notMatchedChars[secret[i]] = 1
            }
        }
    }
    print(notMatchedChars.toString())
    for(i in 0..3) {
        if(guess[i] == secret[i]) {}
        else {
            if(notMatchedChars.containsKey(guess[i])) {
                if(notMatchedChars[guess[i]]!! > 0) {
                    wrongPosition++
                    notMatchedChars[guess[i]] = notMatchedChars[guess[i]]!! - 1
                }
            }
        }
    }
    return Evaluation(rightPosition, wrongPosition)
}