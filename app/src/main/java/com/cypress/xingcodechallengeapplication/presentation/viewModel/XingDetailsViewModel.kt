package com.cypress.xingcodechallengeapplication.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.cypress.xingcodechallengeapplication.data.local.XingDataBase
import com.cypress.xingcodechallengeapplication.data.local.XingEntity
import kotlinx.coroutines.flow.Flow

class XingDetailsViewModel(
    private val xingDataBase: XingDataBase
) : ViewModel() {

    fun getItem(id: Long): Flow<XingEntity> {
        return xingDataBase.xingDao.getById(id)
    }
}