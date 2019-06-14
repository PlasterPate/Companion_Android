package com.dalisyron.companion.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dalisyron.companion.R
import com.dalisyron.companion.ui.home.HomeFragment
import com.dalisyron.data.model.PlaceEntity
import com.dalisyron.data.repository.PlaceRepository
import com.dalisyron.data.repository.UserRepository
import com.dalisyron.remote.api.PlaceService
import com.dalisyron.remote.api.UserService
import com.dalisyron.remote.datasource.PlaceRemoteDataSourceImpl
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

class SearchFragment : DaggerFragment(), SearchContract.View, OnSearchPlaceItemClickListener {

    override fun onItemClicked(placeEntity: PlaceEntity) {
        Toast.makeText(requireContext(), placeEntity.toString(), Toast.LENGTH_LONG).show()
    }

    override fun clearPlaces() {
        adapter.items.clear()
    }

    override fun addPlace(placeEntity: PlaceEntity) {
        adapter.items.add(placeEntity)
        adapter.notifyDataSetChanged()
    }

    @Inject
    lateinit var presenter : SearchPresenter

    private val adapter by lazy {
        SearchAdapter(this)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView.queryHint = "کجا می‌روید؟"
        presenter.view = this
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = adapter
        recyclerView.apply {
            layoutManager = LinearLayoutManager(view.context)
            addItemDecoration(
                DividerItemDecoration(
                    view.context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        var placeRepository = PlaceRepository(PlaceRemoteDataSourceImpl(PlaceService.create()))

        println("Hello world")
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                presenter.onSearchQueryChanged(newText?:"")
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                presenter.onSearchSubmit(query?:"")
                return true
            }

        })
    }

    override fun navigateToNewTrip() {
        fragmentManager?.beginTransaction()
            ?.replace(R.id.content_frame, HomeFragment())
            ?.commit()
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroyView()
    }
}