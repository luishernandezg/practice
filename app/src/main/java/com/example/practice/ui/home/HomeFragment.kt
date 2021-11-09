package com.example.practice.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practice.R
import com.example.practice.databinding.FragmentHomeBinding
import com.example.practice.model.PokemonItem
import com.example.practice.ui.Adapters.PokemonAdapter
import com.example.practice.ui.Adapters.PokemonAdapterClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment(), PokemonAdapterClickListener {

//    private lateinit var homeViewModel: HomeViewModel
    private val homeViewModel by viewModels<HomeViewModel>()
    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: PokemonAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)*/

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        setHasOptionsMenu(true)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setupObservers()
//        homeViewModel.getPokemonList()

        viewManager = LinearLayoutManager(requireContext())
        binding.rvPokemon.layoutManager = viewManager
        adapter = PokemonAdapter(PokemonAdapter.PokemonComparator)
        binding.rvPokemon.adapter = adapter

        setupObservers()


        /*val pagingAdapter = UserAdapter(UserComparator)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = pagingAdapter*/

// Activities can use lifecycleScope directly, but Fragments should instead use
// viewLifecycleOwner.lifecycleScope.

    }


    private fun setupObservers() {

        adapter.addLoadStateListener { combinedLoadStates ->
            when (combinedLoadStates.refresh) {
                is LoadState.Loading -> {
                   binding.progressBar.visibility = View.VISIBLE
                }
                is LoadState.NotLoading -> {
                    binding.progressBar.visibility = View.GONE
                }
                is LoadState.Error -> {
                    Toast.makeText(requireContext(),"Error",Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                }
            }
        }

        lifecycleScope.launch {
            homeViewModel.getPokemonPagingListFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }

        /*homeViewModel.run {
            pokemonList.observe(viewLifecycleOwner) {
                Timber.d(it.toString())
                it?.let { list ->
                    (binding.rvPokemon.adapter as PokemonAdapter).setItems(list.results.mapPokemonItems())
                }

            }
        }*/
    }

    private fun List<PokemonItem?>?.mapPokemonItems() =
        this?.mapNotNull {
            it
        } ?: listOf()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.home_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
         if (id == R.id.action_settings) {
            Log.d("Settings","press")
             findNavController().navigate(R.id.action_navigation_home_to_settingsActivity)
             return true
        }

        if (id == R.id.action_profile) {
            Log.d("Profile","press")
            findNavController().navigate(R.id.action_navigation_home_to_profileFragment)
            return true
        }

       return  super.onOptionsItemSelected(item)

    }

    override fun onItemClick(item: PokemonItem) {
        Timber.d("ItemClick: $item")
    }
}