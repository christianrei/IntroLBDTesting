package com.example.crei.introtestinglbd.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GradeModel(@SerializedName("grade") val grade: Int,
                      @SerializedName("name") val name: String) : Serializable