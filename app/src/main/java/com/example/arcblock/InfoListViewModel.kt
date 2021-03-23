package com.example.arcblock

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arcblock.entry.InfoData
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Rossi on 3/19/21.
 */
class InfoListViewModel : ViewModel() {

    private val TAG = "InfoListViewModel"

    val infoListLiveData: MutableLiveData<Result<List<InfoData?>>> = MutableLiveData()

    fun requestInfoList() {
        viewModelScope.launch {//使用协程进行网络请求
            val result = try {
                val retrofit = Retrofit.Builder().baseUrl(Api.BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val infoListService = retrofit.create(InfoListService::class.java)
                Result.success(infoListService.getInfoList())
            } catch (e: Exception) {
                Log.e(TAG, e.printStackTrace().toString())
                Result.failure(e)
            }
            setMixResult(result)
            infoListLiveData.value = result
        }
    }

    /**
     * 将列表分类，显示规则：1大图—3小图—1大图—2小图—1大图—3小图—1大图—2小图，无图片的顺延
     * */
    private fun setMixResult(result: Result<List<InfoData>>) {
        if (result.getOrNull().isNullOrEmpty()) return

        var index = 0
        var lastSmallPicSize = 2
        for (info in result.getOrNull()!!) {
            if (info.frontmatter?.banner?.childImageSharp?.fixed?.src.isNullOrBlank()) {
                info.itemType = InfoData.TEXT
                continue
            }
            if (index == 0)
                info.itemType = InfoData.BIG_PIC
            else
                info.itemType = InfoData.SMALL_PIC
            if (index == 3 && lastSmallPicSize == 2) {
                lastSmallPicSize = 3
                index = 0
            } else if (index == 2 && lastSmallPicSize == 3) {
                lastSmallPicSize = 2
                index = 0
            } else {
                index++
            }
        }
    }
}