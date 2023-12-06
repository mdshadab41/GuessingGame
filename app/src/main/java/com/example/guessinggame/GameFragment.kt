package com.example.guessinggame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.findNavController
import com.example.guessinggame.databinding.FragmentGameBinding


class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    lateinit var viewmodel:GameViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
        viewmodel = ViewModelProvider(this).get(GameViewModel::class.java)

        binding.gameViewModel = viewmodel//set the data binding variable
        binding.lifecycleOwner = viewLifecycleOwner//this lets the layout respond to live data updates

//        viewmodel.incorrectGuesses.observe(viewLifecycleOwner, Observer { newValue ->
//            binding.incorrectGuesses.text = "Incorrect Guesses: $newValue"
//        })
//
//        viewmodel.livesLeft.observe(viewLifecycleOwner, Observer { newValue ->
//            binding.lives.text = "You have $newValue lives left"
//        })
//
//        viewmodel.secretWordDisplay.observe(viewLifecycleOwner, Observer { newValue->
//            binding.word.text = newValue
//        })
        //If the user has won or lost ,
        // navigate to result fragment, passing it to the wonlostmessage() return value
        viewmodel.gameOver.observe(viewLifecycleOwner, Observer { newValue ->
            if (newValue){
                val action = GameFragmentDirections
                    .actionGameFragmentToResultFragment(viewmodel.wonLostMessage())
                view.findNavController().navigate(action)
            }
        })


        binding.guessButton.setOnClickListener {
            //call makeGuess function to deal with the user's guess
            viewmodel.makeGuess(binding.guess.text.toString().uppercase())
            binding.guess.text = null //reset the edit text

        }



        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}