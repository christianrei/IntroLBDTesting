package com.example.crei.introtestinglbd.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResultModel(@SerializedName("result") val gradeModel: GradeModel) : Serializable