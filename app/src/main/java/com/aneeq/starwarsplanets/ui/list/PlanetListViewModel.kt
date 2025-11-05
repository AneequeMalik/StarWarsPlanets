package com.aneeq.starwarsplanets.ui.list

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aneeq.starwarsplanets.domain.model.Planet
import com.aneeq.starwarsplanets.domain.repository.PlanetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanetListViewModel @Inject constructor(
    private val repository: PlanetRepository
) : ViewModel() {

    var uiState by mutableStateOf<PlanetUiState>(PlanetUiState.Loading)
        private set

    private var currentPage = 1
    private var isLoading = false
    private var hasNext = true
    private val allPlanets = mutableListOf<Planet>()

    init {
        fetchNextPage()
    }

    fun fetchNextPage() {
        if (isLoading || !hasNext) return
        isLoading = true

        viewModelScope.launch {
            repository.getPlanets(currentPage)
                .onSuccess { response ->
                    allPlanets.addAll(response.results)
                    hasNext = response.next != null
                    currentPage++
                    uiState = PlanetUiState.Success(allPlanets.toList())
                }
                .onFailure { e ->
                    uiState = PlanetUiState.Error(e.localizedMessage ?: "Failed to load data")
                }
            isLoading = false
        }
    }

    fun isLoadingMore(): Boolean = isLoading
    fun hasNextPage(): Boolean = hasNext
}


