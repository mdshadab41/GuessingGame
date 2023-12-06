package com.example.guessinggame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//GameViewModel needs to extends ViewModel
class GameViewModel:ViewModel() {
    //words list
  private  val words = listOf("Android", "Activity", "Fragment")
    //the user has to guess
   private val secretWord = words.random().uppercase()
    //how the word is displayed
   private val _secretWordDisplay = MutableLiveData<String>()
    val secretWordDisplay:LiveData<String>
        get() = _secretWordDisplay

    //correct & incorrect guesses made
   private var correctGuesses = ""
   private val _incorrectGuesses = MutableLiveData<String>("")
    val incorrectGuesses: LiveData<String>
        get() = _incorrectGuesses
    //the number of lives left
   private val _livesLeft = MutableLiveData<Int>(8)
    val livesLeft: LiveData<Int>
        get() = _livesLeft

    private val _gameOver = MutableLiveData<Boolean>(false)
    val gameOver: LiveData<Boolean>
        get() = _gameOver

    init {
        //Derive how secret word should be displayed and update the screen
        _secretWordDisplay.value = deriveSecretWordDisplay()
    }
    //This builds a string for how the secret word should be displayed on the screen
    private fun deriveSecretWordDisplay(): String {

        var display = ""
        secretWord.forEach {
            //call check letter for each letter
            // in secret-word, and add its return value to the end pf the display variables
            display += checkLetter(it.toString())

        }
        return display


    }
    //this checks whether the secret word contains the letter
    // the user has guessed. If so, it returns the letter . If not , it returns "_"
    private fun checkLetter(str: String) = when(correctGuesses.contains(str)) {
        true -> str
        false -> "_"


    }


    //this gets called each time user makes a guess
    fun makeGuess(guess: String) {
        if (guess.length == 1){
            if (secretWord.contains(guess)){
                correctGuesses += guess
                _secretWordDisplay.value = deriveSecretWordDisplay()
            }else{
                _incorrectGuesses.value += guess
                _livesLeft.value = _livesLeft.value?.minus(1)
            }
            if (isWon() || isLost()) _gameOver.value = true
        }

    }
   private fun isWon() = secretWord.equals(secretWordDisplay.value, true)
    private fun isLost() = livesLeft.value ?: 0 <=0

    fun wonLostMessage(): String {
        var message = ""
        if (isWon()) message = "You Won!!"
        else if (isLost()) message = "You Lost!"
        message += "The word was $secretWord"
        return message

    }

    fun finishGame(){
        _gameOver.value = true
    }
}