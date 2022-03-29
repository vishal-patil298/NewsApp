package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.LinearLayoutManager as LLT

class MainActivity : AppCompatActivity(), NewsItemClicked {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager= LLT(this)
        val items=fetchData()
        val adapter= NewsListAdapter(items,this)
        recyclerView.adapter=adapter
    }
    private fun fetchData(): ArrayList<String>{
        val list=ArrayList<String>()
        for(i in 0 until 100)
        {
            list.add("Item $i")
        }
        return list
    }

    override fun onItemClicked(item: String) {
        Toast.makeText(this,"Item number $item is clicked",Toast.LENGTH_SHORT).show()
    }
}