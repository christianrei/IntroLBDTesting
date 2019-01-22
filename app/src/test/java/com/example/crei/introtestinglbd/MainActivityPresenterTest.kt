package com.example.crei.introtestinglbd

import com.example.crei.introtestinglbd.main.MainActivityContract
import com.example.crei.introtestinglbd.main.MainActivityPresenter
import com.example.crei.introtestinglbd.models.GradeModel
import com.example.crei.introtestinglbd.models.ResultModel
import com.example.crei.introtestinglbd.networking.MainRetrofitService
import com.example.crei.introtestinglbd.utils.BaseRxTest
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.capture
import com.nhaarman.mockitokotlin2.spy
import io.reactivex.Single
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MainActivityPresenterTest : BaseRxTest() {

    @Mock
    private lateinit var mockView: MainActivityContract.View

    @Mock
    private lateinit var mockService: MainRetrofitService

    private lateinit var presenter: MainActivityPresenter

    private lateinit var spyPresenter: MainActivityPresenter

    @Captor
    private lateinit var captor: ArgumentCaptor<String>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MainActivityPresenter(mockView, mockService)
        spyPresenter = spy(presenter)
    }

    @After
    fun tearDown() {
        //StopKoin
    }

    @Test
    fun `when calculate is called with a grade 50 or over then show a pass message`() {
        presenter.calculate("75")
        verify(mockView).showPassMessage()
    }

    @Test
    fun `when calculate is called with a grade below 50 then show a fail message`() {
        presenter.calculate("25")
        verify(mockView).showFailMessage()
    }

    @Test
    fun `when calculate is called then lastCalculatedGrade must be updated`() {
        presenter.calculate("50")
        assertEquals(50, presenter.lastCalculatedGrade)
    }

    @Test
    fun `when get test result is called and is successful then show result`() {
        val result = ResultModel(GradeModel(0, "Test name"))
        `when`(mockService.getTestResult()).thenReturn(Single.just(result))
        presenter.getTestResult()
        verify(mockView).displayTestResult(any())
        //verify(mockView).displayTestResult("Test name: 0")
    }

    @Test
    fun `when get test result is called and fails then show network error`() {
        `when`(mockService.getTestResult()).thenReturn(Single.error(Exception()))
        presenter.getTestResult()
        verify(mockView).showNetworkError()
    }

    @Test
    fun `when get test result is called then the result is formatted`() {
        val gradeModel = GradeModel(0, "Test name")
        val result = ResultModel(gradeModel)
        `when`(mockService.getTestResult()).thenReturn(Single.just(result))
        spyPresenter.getTestResult()
        verify(spyPresenter).formatGradeModel(gradeModel)
    }

    @Test
    fun `when get test result is called and is successful then result string should match grade returned`() {
        val gradeModel = GradeModel(0, "Test name")
        val result = ResultModel(gradeModel)
        `when`(mockService.getTestResult()).thenReturn(Single.just(result))
        presenter.getTestResult()
        verify(mockView).displayTestResult(capture(captor))
        assertEquals("Test name: 0", captor.value)
    }
}