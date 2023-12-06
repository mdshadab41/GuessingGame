package com.example.guessinggame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.findNavController
import com.example.guessinggame.databinding.FragmentResultBinding


class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: ResultViewModel
    lateinit var viewModelFactory: ResultViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        val view = binding.root

        //Get the result string that's been passed to the ResultFragment
        val result = ResultFragmentArgs.fromBundle(requireArguments()).result
        //create the factory
       viewModelFactory = ResultViewModelFactory(result)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ResultViewModel::class.java)
        //display the result from viewmodel
        binding.resultViewModel = viewModel

        //navigate to Gamefragment when user click the buttons
        binding.newGameButton.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_resultFragment_to_gameFragment)
        }


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}