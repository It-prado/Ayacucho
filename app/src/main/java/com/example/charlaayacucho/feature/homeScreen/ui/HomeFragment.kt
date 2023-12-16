package com.example.charlaayacucho.feature.homeScreen.ui

import android.os.Bundle
import android.text.Layout.Directions
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.charlaayacucho.R
import com.example.charlaayacucho.databinding.FragmentHomeBinding
import com.example.charlaayacucho.feature.homeScreen.adapter.HomeAdapter
import com.example.charlaayacucho.feature.homeScreen.domain.MoviesDomain
import com.example.charlaayacucho.feature.homeScreen.viewmodel.HomeViewModel
import com.example.charlaayacucho.feature.homeScreen.viewmodel.HomeViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels { HomeViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
    }

    private fun setUpObservers() {
        val adapter = HomeAdapter(::onclick)
        binding.recyclerView.adapter = adapter
        viewModel.list.observe(this.viewLifecycleOwner) { data ->
            adapter.list = data
        }
    }

    private fun onclick(moviesDomain: MoviesDomain) {
        findNavController().navigate(
            R.id.action_homeFragment_to_composeActivity,
            bundleOf("id" to moviesDomain.id)
        )
    }

}