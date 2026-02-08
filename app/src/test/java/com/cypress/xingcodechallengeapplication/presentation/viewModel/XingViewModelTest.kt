package com.cypress.xingcodechallengeapplication.presentation.viewModel

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.cypress.xingcodechallengeapplication.data.local.XingEntity
import com.cypress.xingcodechallengeapplication.data.repository.XingRepository
import com.cypress.xingcodechallengeapplication.domain.XingRepoModel
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test


class XingViewModelTest {

    private val repository = mockk<XingRepository>()
    private lateinit var viewModel: XingViewModel

    @Before
    fun setup() {
        viewModel = XingViewModel(repository)
    }

    @Test
    fun xingFlow_emitsPagingDataWithFakeItem() = runTest {

        val fakeEntity = XingEntity(
            id = 1,
            name = "Test Repo",
            description = "Test Description",
            isFork = false,
            stars = 10,
            forks = 5,
            language = "Kotlin",
            ownerLogin = "user",
            ownerAvatarUrl = null
        )

        val fakePagingData: PagingData<XingEntity> = PagingData.from(listOf(fakeEntity))

        val fakePager = mockk<Pager<Int, XingEntity>>()
        every { fakePager.flow } returns flowOf(fakePagingData)
        every { repository.getXingPager() } returns fakePager

        val pagingData = viewModel.xingFlow.first()

        val differ = AsyncPagingDataDiffer(
            diffCallback = object : DiffUtil.ItemCallback<XingRepoModel>() {
                override fun areItemsTheSame(oldItem: XingRepoModel, newItem: XingRepoModel) =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: XingRepoModel, newItem: XingRepoModel) =
                    oldItem == newItem
            },
            updateCallback = NoopListCallback(),
            mainDispatcher = Dispatchers.Unconfined,
            workerDispatcher = Dispatchers.Unconfined
        )

        differ.submitData(pagingData)

        val items = differ.snapshot().items
        assertEquals(1, items.size)
        assertEquals(1, items[0].id)
        assertEquals("Test Repo", items[0].name)
    }
}

class NoopListCallback : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}