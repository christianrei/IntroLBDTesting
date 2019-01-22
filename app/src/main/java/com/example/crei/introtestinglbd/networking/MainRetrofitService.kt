package com.example.crei.introtestinglbd.networking

import com.example.crei.introtestinglbd.models.ResultModel
import io.reactivex.Single
import retrofit2.http.PUT

interface MainRetrofitService {

    @PUT("http://www.mocky.io/v2/5c4690b8310000550005f3a4")
    fun getTestResult(): Single<ResultModel>

}