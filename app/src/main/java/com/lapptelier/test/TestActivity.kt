package com.lapptelier.test

import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.lapptelier.smartrecyclerview.*
import java.util.*

/**
 * @author L'Apptelier SARL
 * @date 14/09/2017
 */
internal class TestActivity : AppCompatActivity(), ViewHolderInteractionListener, OnMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private var mRecyclerView: SmartRecyclerView? = null

    private lateinit var adapter: MultiGenericAdapter

    private val elements = Arrays.asList("test 1", "test 2", "test 3", "test 4", "test 5", "test 6", "test 7", "test 8", "test 9", "test 10", "test 11", "test 12", "test 13", "test 14", "test 15", "test 16")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        mRecyclerView = findViewById(R.id.test_smart_recycler_view)

        //configuration de la liste
        mRecyclerView!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))

        // Configuration de l'adapter
        adapter = MultiGenericAdapter(String::class.java, TestViewHolder::class.java, R.layout.cell_test, this)
        mRecyclerView!!.setAdapter(adapter, true)
        mRecyclerView!!.addItemDecoration(DrawableDividerItemDecoration(ContextCompat.getDrawable(baseContext, R.drawable.divider), null, true))
        mRecyclerView!!.emptyLayout = R.layout.empty
        mRecyclerView!!.loadingLayout = R.layout.loading
        mRecyclerView!!.loadMoreLayout = R.layout.loadmore
        mRecyclerView!!.setOnMoreListener(this, 2)
        mRecyclerView!!.setRefreshListener(this)

        mRecyclerView!!.displayLoadingView()

        Handler().postDelayed({
            adapter.hasMore = true
        }, 1000)

        Handler().postDelayed({
            adapter.addAll(elements)
        }, 5000)


    }


    // OnMoreListener
    override fun onMoreAsked(overallItemsCount: Int, itemsBeforeMore: Int, maxLastVisiblePosition: Int) {
        Log.d("test", "MORE MORE MORE !")

        Handler().postDelayed({
            adapter.hasMore = true
        }, 500)

        Handler().postDelayed({
            adapter.appendAll(elements)
        }, 1000)

    }

    override fun onRefresh() {
        Log.d("test", "FRESH-FRESH-FRESH !")
        Handler().postDelayed({
            adapter.hasMore = true
        }, 1000)

        Handler().postDelayed({
            adapter.addAll(elements)
        }, 2000)

    }


    override fun onItemAction(item: Any, viewId: Int, viewHolderInteraction: ViewHolderInteraction) {
        adapter.removeAt(adapter.getObjectIndex(item))
    }
}
