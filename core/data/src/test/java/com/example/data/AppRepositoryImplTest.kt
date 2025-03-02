

import android.content.Context
import android.content.res.AssetManager
import com.example.common.Resource
import com.example.data.local.SatelliteDatabaseDao
import com.example.data.local.entity.SatelliteDetailEntity
import com.example.data.local.entity.mapper.SatelliteDetailEntityMapper
import com.example.data.repository.AppRepositoryImpl
import com.example.model.SatelliteDetailModel
import com.example.model.SatelliteModel
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class AppRepositoryImplTest {

    @Mock
    private lateinit var dao: SatelliteDatabaseDao
    @Mock
    private lateinit var context: Context
    @Mock
    private lateinit var assetManager: AssetManager
    private lateinit var repository: AppRepositoryImpl
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        repository = AppRepositoryImpl(dao)
        whenever(context.assets).thenReturn(assetManager)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchSatelliteList should return success with data`() = runTest {
        // Given
        val mockJson = """
        [
            {"id": 1, "active": true, "name": "Satellite 1"},
            {"id": 2, "active": false, "name": "Satellite 2"}
        ]
    """.trimIndent()
        val expectedList = listOf(
            SatelliteModel(1, true, "Satellite 1"),
            SatelliteModel(2, false, "Satellite 2")
        )
        whenever(assetManager.open("SATELLITE-LIST.json")).thenReturn(mockJson.byteInputStream())
        val result = repository.fetchSatelliteList(context).toList()

        Truth.assertThat(result.size).isEqualTo(2)
        Truth.assertThat(result[0]).isInstanceOf(Resource.Loading::class.java)
        Truth.assertThat(result[1]).isInstanceOf(Resource.Success::class.java)
        Truth.assertThat((result[1] as Resource.Success).data).isEqualTo(expectedList)
    }

    @Test
    fun `fetchSatelliteList should return error when exception occurs`() = runTest {
        whenever(assetManager.open("SATELLITE-LIST.json")).thenThrow(IOException("Test Exception"))
        val result = repository.fetchSatelliteList(context).toList()

        // Then
        Truth.assertThat(result.size).isEqualTo(2)
        Truth.assertThat(result[0]).isInstanceOf(Resource.Loading::class.java)
        Truth.assertThat(result[1]).isInstanceOf(Resource.Error::class.java)
        Truth.assertThat((result[1] as Resource.Error).message).isEqualTo("Asset dosyası okuma hatası: SATELLITE-LIST.json")
    }

    @Test
    fun `fetchSatelliteDetail should return success with data from local`() = runTest {

        val satelliteId = 1
        val mockEntity = SatelliteDetailEntity(satelliteId, 10,  "2023-11-20",20,10)
        val expectedModel = SatelliteDetailEntityMapper.asDomain(mockEntity)
        whenever(dao.getSatelliteDetailList()).thenReturn(listOf(mockEntity))

        val result = repository.fetchSatelliteDetail(context, satelliteId).toList()

        Truth.assertThat(result.size).isEqualTo(2)
        Truth.assertThat(result[0]).isInstanceOf(Resource.Loading::class.java)
        Truth.assertThat(result[1]).isInstanceOf(Resource.Success::class.java)
        Truth.assertThat((result[1] as Resource.Success).data).isEqualTo(expectedModel)
    }

    @Test
    fun `fetchSatelliteDetail should return success with data from json`() = runTest {
        // Given
        val satelliteId = 1
        val mockJson = """
        [
            {"id": 1, "cost_per_launch": 10, "first_flight": "2023-11-20", "height": 20 , "mass": 10},
            {"id": 2, "cost_per_launch": 30, "first_flight": "2023-11-21", "height": 40 , "mass": 10}
        ]
    """.trimIndent()
        val expectedModel = SatelliteDetailModel(satelliteId, 10,  "2023-11-20",20,10)
        whenever(dao.getSatelliteDetailList()).thenReturn(emptyList())
        whenever(assetManager.open("SATELLITE-DETAIL.json")).thenReturn(mockJson.byteInputStream())
        val result = repository.fetchSatelliteDetail(context, satelliteId).toList()

        Truth.assertThat(result.size).isEqualTo(2)
        Truth.assertThat(result[0]).isInstanceOf(Resource.Loading::class.java)
        Truth.assertThat(result[1]).isInstanceOf(Resource.Success::class.java)
        Truth.assertThat((result[1] as Resource.Success).data).isEqualTo(expectedModel)
    }

    @Test
    fun `fetchSatelliteDetail should return error when detail not found`() = runTest {
        // Given
        val satelliteId = 1
        val mockJson = """
        [
           {"id": 2, "cost_per_launch": 30, "first_flight": "2023-11-21", "height": 40 , "mass": 10}
        ]
    """.trimIndent()
        whenever(dao.getSatelliteDetailList()).thenReturn(emptyList())
        whenever(assetManager.open("SATELLITE-DETAIL.json")).thenReturn(mockJson.byteInputStream())
        val result = repository.fetchSatelliteDetail(context, satelliteId).toList()


        Truth.assertThat(result.size).isEqualTo(2)
        Truth.assertThat(result[0]).isInstanceOf(Resource.Loading::class.java)
        Truth.assertThat(result[1]).isInstanceOf(Resource.Error::class.java)
        Truth.assertThat((result[1] as Resource.Error).message).isEqualTo("Detay Bulunamadı")
    }

    @Test
    fun `fetchSatelliteDetail should return error when exception occurs`() = runTest {

        val satelliteId = 1
        whenever(dao.getSatelliteDetailList()).thenThrow(RuntimeException("Test Exception"))
        val result = repository.fetchSatelliteDetail(context, satelliteId).toList()

        Truth.assertThat(result.size).isEqualTo(2)
        Truth.assertThat(result[0]).isInstanceOf(Resource.Loading::class.java)
        Truth.assertThat(result[1]).isInstanceOf(Resource.Error::class.java)
        Truth.assertThat((result[1] as Resource.Error).message).isNotNull()
    }

}