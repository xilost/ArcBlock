package com.example.arcblock

import com.example.arcblock.entry.InfoData
import retrofit2.http.GET


/**
 * Created by Rossi on 3/19/21.
 */
interface InfoListService {
    @GET("blog/posts.json")
    suspend fun getInfoList(): List<InfoData>
}