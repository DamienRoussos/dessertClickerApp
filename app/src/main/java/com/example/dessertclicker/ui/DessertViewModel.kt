package com.example.dessertclicker.ui

import androidx.lifecycle.ViewModel
import com.example.dessertclicker.data.Datasource.dessertList
import com.example.dessertclicker.data.DessertUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DessertViewModel : ViewModel() {
    private val _dessertUiState = MutableStateFlow(DessertUiState())
    val dessertUiState: StateFlow<DessertUiState> = _dessertUiState.asStateFlow()

    fun determineDessertToShow(dessertsSold: Int): Int {
        var dessertToShow = 0
        dessertList.mapIndexed { index, dessert ->
            if (dessertsSold >= dessert.startProductionAmount) {
                dessertToShow = index
            }
        }
        return dessertToShow
    }

    fun onDessertClicked() {
        _dessertUiState.update { dessertUiState ->
            val dessertsSold = dessertUiState.dessertsSold.inc()
            val nextDessertIndex = determineDessertToShow(dessertsSold)
            dessertUiState.copy(
                dessertsSold = dessertsSold,
                revenue = dessertUiState.revenue + dessertUiState.currentDessertPrice,
                currentDessertIndex = nextDessertIndex,
                currentDessertPrice = dessertList[nextDessertIndex].price,
                currentDessertImageId = dessertList[nextDessertIndex].imageId
            )
        }
    }
}