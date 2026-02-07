package com.cypress.xingcodechallengeapplication.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.cypress.xingcodechallengeapplication.data.local.XingDataBase
import com.cypress.xingcodechallengeapplication.data.local.XingEntity
import com.cypress.xingcodechallengeapplication.domain.XingDetailsModel
import com.cypress.xingcodechallengeapplication.domain.mappers.toXingDetailsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class XingDetailsViewModel(
    private val xingDataBase: XingDataBase
) : ViewModel() {

    fun getItem(id: Long): Flow<XingDetailsModel> {
        return xingDataBase.xingDao.getById(id).map{  it.toXingDetailsModel() }
    }
}