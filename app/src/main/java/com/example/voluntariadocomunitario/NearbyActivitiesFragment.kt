package com.example.voluntariadocomunitario

import VoluntaryAct
import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.voluntariadocomunitario.databinding.FragmentNearbyActivitiesBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class NearbyActivitiesFragment : Fragment() {

    private var _binding: FragmentNearbyActivitiesBinding? = null
    private val binding get() = _binding!!
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var adapter: OpportunitiesAdapter
    private var events: List<VoluntaryAct> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNearbyActivitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        adapter = OpportunitiesAdapter(events, object : OpportunitiesAdapter.OnItemClickListener {
            override fun onItemClick(voluntaryAct: VoluntaryAct) {
                val dialog = MaterialAlertDialogBuilder(requireContext())
                    .setTitle(voluntaryAct.title)
                    .setMessage(voluntaryAct.description)
                    .setPositiveButton("OK", null)
                    .show()
            }
        })
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        fetchAndDisplayNearbyEvents()
    }

    private fun fetchAndDisplayNearbyEvents() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                lifecycleScope.launch {
                    fetchEventsBasedOnLocation(it)
                }
            }
        }
    }

    private suspend fun fetchEventsBasedOnLocation(location: Location) {
        val firebaseHelper = FirebaseHelper()
        events = withContext(Dispatchers.IO) {
            firebaseHelper.getVoluntaryActs()
        }
        val geocoder = Geocoder(requireContext(), Locale.getDefault())

        events.forEach { event ->
            val addresses = withContext(Dispatchers.IO) {
                geocoder.getFromLocationName(event.location, 1)
            }
            if (addresses != null && addresses.isNotEmpty()) {
                val address = addresses[0]
                event.latitude = address.latitude
                event.longitude = address.longitude
            } else {
                event.latitude = Double.NaN
                event.longitude = Double.NaN
            }
        }

        val nearbyEvents = events.filter { isNearby(it, location) }
        withContext(Dispatchers.Main) {
            adapter.updateData(nearbyEvents)
        }
    }

    private fun isNearby(voluntaryAct: VoluntaryAct, location: Location): Boolean {
        if (voluntaryAct.latitude.isNaN() || voluntaryAct.longitude.isNaN()) {
            return false
        }
        val eventLocation = Location("").apply {
            latitude = voluntaryAct.latitude
            longitude = voluntaryAct.longitude
        }
        val distance = location.distanceTo(eventLocation)
        return distance <= 50000000 // 50 km radius
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}