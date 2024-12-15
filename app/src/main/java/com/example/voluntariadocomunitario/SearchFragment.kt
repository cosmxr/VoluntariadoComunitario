package com.example.voluntariadocomunitario

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.voluntariadocomunitario.databinding.FragmentSearchBinding
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: OpportunitiesAdapter
    private var events: List<VoluntaryAct> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configure the RecyclerView
        adapter = OpportunitiesAdapter(events)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        // Fetch and display the list of events
        fetchAndDisplayEvents()

        // Configure the search button
        binding.searchButton.setOnClickListener {
            val query = binding.searchEditText.text.toString()
            filterOpportunities(query)
        }
    }

    private fun fetchAndDisplayEvents() {
        lifecycleScope.launch {
            val firebaseHelper = FirebaseHelper()
            events = firebaseHelper.getVoluntaryActs()
            adapter.updateData(events)
        }
    }

    private fun filterOpportunities(query: String) {
        val filteredEvents = events.filter { it.title.contains(query, ignoreCase = true) }
        adapter.updateData(filteredEvents)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}