package com.my.fragmenttest

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.my.kotlinrecyclerview.Movie
import com.my.kotlinrecyclerview.RecyclerAdapter
import kotlinx.android.synthetic.main.fragment_blank2.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class BlankFragment2 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: RecyclerAdapter
    lateinit var itemsswipetorefresh: SwipeRefreshLayout
    var movieList: List<Movie> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_blank2, container, false)


        recyclerView = view.findViewById(R.id.recyclerview)
        itemsswipetorefresh = view.findViewById(R.id.itemsswipetorefresh)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )

        itemsswipetorefresh.setColorSchemeColors(Color.WHITE)
        itemsswipetorefresh.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                requireActivity(),
                R.color.colorPrimary
            )
        )
        itemsswipetorefresh.isRefreshing = true
        setItemData()
        itemsswipetorefresh.setOnRefreshListener {
            movieList = emptyList()
            setItemData()

        }




        return view
    }

    private fun setItemData() {
        val apiInterface = ApiInterface.create().getMovies()
        apiInterface.enqueue(object : Callback<List<Movie>> {
            override fun onResponse(call: Call<List<Movie>>?, response: Response<List<Movie>>?) {
                if (response?.body() != null) {
                    movieList = response.body()!!

                    recyclerAdapter = RecyclerAdapter(
                        //calling the listener method in the init section of the Adapter class
                        clickListener = {
                                itemPos->Log.e("Current Pos ",""+itemPos)
                        },
                        // click listener ends
                        movieListItems = movieList
                    )
                    recyclerView.adapter = recyclerAdapter
                    itemsswipetorefresh.isRefreshing = false
                }
            }

            override fun onFailure(call: Call<List<Movie>>?, t: Throwable?) {
                Log.e("Failed ", t?.localizedMessage.toString())

            }
        })

    }


    companion object {

        fun newInstance(param1: String, param2: String) =
            BlankFragment2().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}