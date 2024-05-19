package com.example.retrifiapi

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    private lateinit var view : ImageView
    private lateinit var recyclerView : RecyclerView
    lateinit var myAdapter: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerView = findViewById(R.id.recyclerView)
//        view =findViewById(R.id.ig)
        val retrofiBuilder = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
        val retrofitData = retrofiBuilder.getProductData()//aba data retrieve lageko
        retrofitData.enqueue(object : Callback<MyData?> {
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                //esko matlab if successfully data get vayo vahne yesko workflow hunxa
                //response body banaune jasle ajjha help gerxa jun kura laii dekhaune ho or display garne ho
                val responseBody = response.body()
                val productList = responseBody?.products!!
                myAdapter = MyAdapter(this@MainActivity,productList)
                recyclerView.adapter = myAdapter
                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)



            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                //in case of failure
                Log.d("failure","om Failure"+t.message)

            }
        })
    }
}