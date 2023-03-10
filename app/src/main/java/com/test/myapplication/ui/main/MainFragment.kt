package com.test.myapplication.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.myapplication.PaginationScrollListener
import com.test.myapplication.adapter.MainListAdapter
import com.test.myapplication.databinding.FragmentMainBinding
import com.test.myapplication.listener.ListClickListener
import com.test.myapplication.model.DataList


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }
    var isLastPage: Boolean = false
    var isLoading: Boolean = false

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding
    private var page:Int = 1
    private var listData = mutableListOf<DataList>()
    private val adapter = MainListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvList.layoutManager = layoutManager

        viewModel.getDataList(page)

        binding.rvList.adapter = adapter
        adapter.setOnClickListener(object : ListClickListener{
            override fun onListClick(data: DataList) {
                showDialog(data)
            }
        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            page = 1
            viewModel.getDataList(page)
        }

        addPagination(layoutManager)
        observeData()
    }

    private fun observeData(){
        viewModel.dataList.observe(viewLifecycleOwner, Observer {
            binding.swipeRefreshLayout.isRefreshing = false
            listData = it.toMutableList()
            if(page ==1){
                adapter.setList(listData)
            }else{
                adapter.addList(listData)
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            binding.swipeRefreshLayout.isRefreshing = false
        })
    }

    private fun showDialog(data: DataList){
        val builder = AlertDialog.Builder(requireActivity())
        //set title for alert dialog
        builder.setTitle(data.author)
        //set message for alert dialog
        builder.setMessage("Description")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Ok"){ _, _ ->
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }


    private fun addPagination(layoutManager:LinearLayoutManager ){
        binding.rvList.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                page++
                //you have to call loadmore items to get more data
                viewModel.getDataList(page)
            }
        })
    }
}