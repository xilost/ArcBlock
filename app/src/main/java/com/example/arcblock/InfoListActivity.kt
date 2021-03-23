package com.example.arcblock

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.arcblock.entry.InfoData
import kotlinx.android.synthetic.main.activity_info_list.*

/**
 * 信息列表页
 *
 * Created by Rossi on 3/19/21.
 */
class InfoListActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private val TAG = "InfoListActivity"

    var infoList: List<InfoData?>? = mutableListOf<InfoData>()

    var infoAdapter: InfoAdapter? = null

    private val viewModel: InfoListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_list)

        srl_info.isRefreshing = true

        ll_load_timeout.setOnClickListener {
            srl_info.isRefreshing = true
            ll_load_timeout.visibility = View.GONE
            viewModel.requestInfoList()
        }

        infoAdapter = InfoAdapter(infoList)
        infoAdapter?.onItemClickListener = itemClickListener

        rv_info.layoutManager = LinearLayoutManager(this)
        rv_info.adapter = infoAdapter
        srl_info.setOnRefreshListener(this)

        viewModel.infoListLiveData.observe(this) { result ->
            val infoListResult = result.getOrNull()
            if (infoListResult.isNullOrEmpty()) {
                ll_load_timeout.visibility = View.VISIBLE
                return@observe
            }

            infoList = infoListResult
            infoAdapter?.setNewData(infoList)

            srl_info.isRefreshing = false
        }
        viewModel.requestInfoList()
    }

    private var itemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position: Int ->
        startActivity(
            Intent(this, InfoDetailActivity::class.java)
                .putExtra("path", infoList?.get(position)?.frontmatter?.path)
        )
    }

    override fun onRefresh() {
        viewModel.requestInfoList()
    }
}