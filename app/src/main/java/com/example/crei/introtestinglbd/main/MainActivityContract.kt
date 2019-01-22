package com.example.crei.introtestinglbd.main

interface MainActivityContract {

    interface View {
        fun showFailMessage()
        fun showPassMessage()
        fun showNetworkError()
        fun displayTestResult(result: String)
    }

    interface Presenter {
        fun calculate(value: String)
        fun getTestResult()
    }

}