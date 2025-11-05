package com.aneeq.starwarsplanets.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aneeq.starwarsplanets.R
import com.aneeq.starwarsplanets.domain.model.Planet
import com.aneeq.starwarsplanets.utils.Constants
import dagger.hilt.android.AndroidEntryPoint


/**
 * Activity that displays detailed information about a selected planet.
 * Injected with Hilt and loads {@link PlanetDetailFragment} on creation.
 */
@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val planet = intent.getParcelableExtra<Planet>(Constants.KEY_PLANET)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.detailContainer, PlanetDetailFragment.newInstance(planet))
                .commit()
        }
    }
}
