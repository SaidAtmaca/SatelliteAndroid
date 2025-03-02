package com.example.presentation

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.foundation.layout.size
import com.example.common.Resource
import com.example.domain.repository.AppRepository
import com.example.domain.use_cases.SatelliteListUseCase
import com.example.model.SatelliteModel
import com.example.presentation.ui.list.ListUIState
import com.example.presentation.ui.list.ListViewModel
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class ListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: ListViewModel
    private lateinit var useCase: SatelliteListUseCase
    private lateinit var repository: AppRepository
    private lateinit var context: Context

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock()
        context = mock()
        useCase = SatelliteListUseCase(repository)
        viewModel = ListViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchSatelliteList should return success with data`() = runTest {
        val satelliteList = listOf(
            SatelliteModel(1, true, "Satellite 1"),
            SatelliteModel(2, false, "Satellite 2")
        )
        whenever(repository.fetchSatelliteList(context)).thenReturn(
            flowOf(
                Resource.Success(
                    satelliteList
                )
            )
        )
        viewModel.fetchSatelliteList(context)
        testDispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.uiState.value
        Truth.assertThat(result).isInstanceOf(ListUIState.Success::class.java)
        Truth.assertThat((result as ListUIState.Success).satelliteList).isEqualTo(satelliteList)
    }

    @Test
    fun `fetchSatelliteList should return error when exception occurs`() = runTest {
        val errorMessage = "Test Exception"
        whenever(repository.fetchSatelliteList(context)).thenReturn(
            flowOf(
                Resource.Error(
                    errorMessage
                )
            )
        )
        viewModel.fetchSatelliteList(context)
        testDispatcher.scheduler.advanceUntilIdle()

        val result = viewModel.uiState.value
        Truth.assertThat(result).isInstanceOf(ListUIState.Error::class.java)
        Truth.assertThat((result as ListUIState.Error).message).isEqualTo(errorMessage)
    }

    @Test
    fun `updateSearchQuery should filter list correctly`() = runTest {

        val satelliteList = listOf(
            SatelliteModel(1, true, "Satellite 1"),
            SatelliteModel(2, false, "Satellite 2"),
            SatelliteModel(3, true, "Satellite 3")
        )
        whenever(repository.fetchSatelliteList(context)).thenReturn(
            flowOf(
                Resource.Success(
                    satelliteList
                )
            )
        )
        viewModel.fetchSatelliteList(context)
        testDispatcher.scheduler.advanceUntilIdle()
        viewModel.updateSearchQuery("Satellite 2")
        testDispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.uiState.value
        Truth.assertThat(result).isInstanceOf(ListUIState.Success::class.java)
        Truth.assertThat((result as ListUIState.Success).filteredList.size).isEqualTo(1)
        Truth.assertThat(result.filteredList[0].name).isEqualTo("Satellite 2")
    }

    @Test
    fun `updateSearchQuery should not filter list when query length is less than 3`() = runTest {

        val satelliteList = listOf(
            SatelliteModel(1, true, "Satellite 1"),
            SatelliteModel(2, false, "Satellite 2"),
            SatelliteModel(3, true, "Satellite 3")
        )
        whenever(repository.fetchSatelliteList(context)).thenReturn(
            flowOf(
                Resource.Success(
                    satelliteList
                )
            )
        )
        viewModel.fetchSatelliteList(context)
        testDispatcher.scheduler.advanceUntilIdle()
        viewModel.updateSearchQuery("Sa")
        testDispatcher.scheduler.advanceUntilIdle()

        val result = viewModel.uiState.value
        Truth.assertThat(result).isInstanceOf(ListUIState.Success::class.java)
        Truth.assertThat((result as ListUIState.Success).filteredList.size).isEqualTo(3)
    }

    @Test
    fun `fetchSatelliteList should return loading`() = runTest {

        whenever(repository.fetchSatelliteList(context)).thenReturn(flowOf(Resource.Loading()))
        viewModel.fetchSatelliteList(context)
        testDispatcher.scheduler.advanceUntilIdle()

        val result = viewModel.uiState.value
        Truth.assertThat(result).isEqualTo(ListUIState.Loading)
    }
}