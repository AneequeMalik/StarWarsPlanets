package com.aneeq.starwarsplanets.ui.detail

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.aneeq.starwarsplanets.utils.Constants.KEY_PLANET
import com.aneeq.starwarsplanets.domain.model.Planet
import com.aneeq.starwarsplanets.databinding.FragmentPlanetDetailBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment that displays detailed information about a planet.
 * Uses view binding and Hilt for dependency injection.
 */
@AndroidEntryPoint
class PlanetDetailFragment : Fragment() {

    private var _binding: FragmentPlanetDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlanetDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val planet = arguments?.getParcelable<Planet>(KEY_PLANET)
        // Bind planet data to UI
        binding.planetName.text = planet?.name
        binding.planetClimate.text = "Climate: ${planet?.climate}"
        binding.planetDiameter.text = "Diameter: ${planet?.diameter}"
        binding.planetGravity.text = "Gravity: ${planet?.gravity}"
        binding.planetPopulation.text = "Population: ${planet?.population}"
        binding.planetTerrain.text = "Terrain: ${planet?.terrain}"
        val width = Resources.getSystem().displayMetrics.widthPixels
        val height = Resources.getSystem().displayMetrics.heightPixels

        // Load random planet image based on name
        binding.planetImage.load("https://picsum.photos/seed/${planet?.name}/${width}/${height}") {
            crossfade(true)
            allowHardware(false)
        }

        // Handle back navigation
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        // Creates a new instance of PlanetDetailFragment with planet data
        fun newInstance(planet: Planet?): PlanetDetailFragment {
            val fragment = PlanetDetailFragment()
            val args = Bundle()
            args.putParcelable(KEY_PLANET, planet)
            fragment.arguments = args
            return fragment
        }
    }
}