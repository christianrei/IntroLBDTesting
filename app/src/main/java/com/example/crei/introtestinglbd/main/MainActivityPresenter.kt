package com.example.crei.introtestinglbd.main

import com.example.crei.introtestinglbd.models.GradeModel
import com.example.crei.introtestinglbd.networking.MainRetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class MainActivityPresenter(val view: MainActivityContract.View, private val service: MainRetrofitService) : MainActivityContract.Presenter {

    var lastCalculatedGrade = 0

    override fun calculate(value: String) {
        val grade = value.toInt()
        lastCalculatedGrade = grade
        if (grade >= 50) {
            view.showPassMessage()
        } else {
            view.showFailMessage()
        }
    }

//    if (grade >= 50) {
//        view.showPassMessage()
//    } else if (grade < 50) {
//        view.showFailMessage()
//    } else {
//        view.sendValueBack(value)
//    }

    override fun getTestResult() {
        service.getTestResult().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    view.displayTestResult(formatGradeModel(it.gradeModel))
                }, {
                    view.showNetworkError()
                })
    }

    open fun formatGradeModel(gradeModel: GradeModel): String {
        return gradeModel.name + ": " + gradeModel.grade
    }
}