package com.example.presentation

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.common.Resource
import com.example.domain.repository.AppRepository
import com.example.domain.use_cases.SatelliteDetailUseCase
import com.example.domain.use_cases.SatellitePositionUseCase
import com.example.model.PositionModel
import com.example.model.SatelliteDetailModel
import com.example.presentation.ui.detail.DetailUIState
import com.example.presentation.ui.detail.DetailViewModel
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
class DetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: DetailViewModel
    private lateinit var detailUseCase: SatelliteDetailUseCase
    private lateinit var positionUseCase: SatellitePositionUseCase
    private lateinit var repository: AppRepository
    private lateinit var context: Context

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock()
        context = mock()
        detailUseCase = SatelliteDetailUseCase(repository)
        positionUseCase = SatellitePositionUseCase(repository)
        viewModel = DetailViewModel(detailUseCase, positionUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchSatelliteDetail should return success with data`() = runTest {

        val satelliteDetail = SatelliteDetailModel(1, 1000, "2023-01-01", 10, 100)
        whenever(repository.fetchSatelliteDetail(context, 1)).thenReturn(
            flowOf(
                Resource.Success(
                    satelliteDetail
                )
            )
        )
        viewModel.fetchSatelliteDetail(context, 1)
        testDispatcher.scheduler.advanceUntilIdle()

        val result = viewModel.uiState.value
        Truth.assertThat(result).isInstanceOf(DetailUIState.Success::class.java)
        Truth.assertThat((result as DetailUIState.Success).detail).isEqualTo(satelliteDetail)
    }

    @Test
    fun `fetchSatelliteDetail should return error when exception occurs`() = runTest {

        val errorMessage = "Test Exception"
        whenever(repository.fetchSatelliteDetail(context, 1)).thenReturn(
            flowOf(
                Resource.Error(
                    errorMessage
                )
            )
        )
        viewModel.fetchSatelliteDetail(context, 1)
        testDispatcher.scheduler.advanceUntilIdle()

        val result = viewModel.uiState.value
        Truth.assertThat(result).isInstanceOf(DetailUIState.Error::class.java)
        Truth.assertThat((result as DetailUIState.Error).message).isEqualTo(errorMessage)
    }

    @Test
    fun `fetchSatelliteDetail should return loading`() = runTest {

        whenever(repository.fetchSatelliteDetail(context, 1)).thenReturn(flowOf(Resource.Loading()))
        viewModel.fetchSatelliteDetail(context, 1)
        testDispatcher.scheduler.advanceUntilIdle()

        val result = viewModel.uiState.value
        Truth.assertThat(result).isEqualTo(DetailUIState.Loading)
    }

    @Test
    fun `setCurrentPosition should update currentPositionState`() = runTest {

        val position = PositionModel(10.0, 20.0)
        viewModel.setCurrentPosition(position)

        val result = viewModel.currentPositionState.value
        Truth.assertThat(result).isEqualTo(position)
    }
}