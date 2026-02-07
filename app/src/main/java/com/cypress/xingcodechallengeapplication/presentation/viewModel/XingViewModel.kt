package com.cypress.xingcodechallengeapplication.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.cypress.xingcodechallengeapplication.data.repository.XingRepository
import com.cypress.xingcodechallengeapplication.domain.mappers.toXingDetailsModel
import com.cypress.xingcodechallengeapplication.domain.mappers.toXingModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalCoroutinesApi::class)
class XingViewModel(
    private val xingRepository: XingRepository
) : ViewModel() {

    private var triggerRefresh = MutableStateFlow(Unit)

    val xingFlow = triggerRefresh.flatMapLatest {
        xingRepository
            .getXingPager()
            .flow
            .map { pagingData ->
                pagingData.map { it.toXingModel() }
            }
    }.cachedIn(viewModelScope)

    fun getXingItems(){
        triggerRefresh.value = Unit
    }

}