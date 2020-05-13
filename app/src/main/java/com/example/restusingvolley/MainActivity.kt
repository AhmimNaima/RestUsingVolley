package com.example.restusingvolley

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private val l=ArrayList<ToDO>()
    private var queue: RequestQueue? = null
    private val url = "https://jsonplaceholder.typicode.com/todos"
    private var recyclerView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerview)
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        queue = Volley.newRequestQueue(this)
        GetData()
    }

    private fun GetData() {
        val request = JsonObjectRequest(Request.Method.GET,url,null,
            Response.Listener
           {
                    response ->
                  try {
                    Toast.makeText(this, response["succss"].toString(), Toast.LENGTH_SHORT).show()
                     val jsonArray = response.getJSONArray("item")
                     for (i in 0 until jsonArray.length()) {
                      val item = jsonArray.getJSONObject(i)
                      val title = item.getString("title")
                      val comp = item.getString("completed")
                         val todo=ToDO(title,comp)
                         l.add(todo) }
                      val adapter = ToDoAdapter(this@MainActivity,l)
                      recyclerView!!.adapter = adapter

                  } catch (e: JSONException) {
                    e.printStackTrace()
                            }
            },
            Response.ErrorListener {
                Toast.makeText(this, "That didn't work!", Toast.LENGTH_SHORT).show()
            })
        queue?.add(request)
    }

}