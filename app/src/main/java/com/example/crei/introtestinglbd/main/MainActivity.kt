package com.example.crei.introtestinglbd.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.crei.introtestinglbd.R
import com.example.crei.introtestinglbd.networking.MainRetrofitService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit

class MainActivity : AppCompatActivity(), MainActivityContract.View {

    private val service by lazy { Retrofit.Builder().baseUrl("").build().create<MainRetrofitService>(MainRetrofitService::class.java) }

    private val presenter: MainActivityContract.Presenter by lazy { MainActivityPresenter(this, service) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_calculate.setOnClickListener {
            presenter.calculate(et_input_field.text.toString())
        }

        presenter.getTestResult()
    }

    override fun showFailMessage() {
        Toast.makeText(this, "You failed :(", Toast.LENGTH_LONG).show()
    }

    override fun showPassMessage() {
        Toast.makeText(this, "You passed :)", Toast.LENGTH_LONG).show()
    }

    override fun showNetworkError() {
        Toast.makeText(this, "Sorry, there was a problem...", Toast.LENGTH_LONG).show()
    }

    override fun displayTestResult(result: String) {
        Toast.makeText(this, result, Toast.LENGTH_LONG).show()
    }
}
