package com.example.arcblock.entry

import android.os.Parcelable
import com.chad.library.adapter.base.entity.MultiItemEntity
import kotlinx.android.parcel.Parcelize

/**
 * Created by Rossi on 3/19/21.
 */
@Parcelize
data class InfoData(var frontmatter: FrontMatter?) : Parcelable, MultiItemEntity {

    companion object {
        val BIG_PIC: Int = 1 //大图
        val SMALL_PIC: Int = 2 //小图
        var TEXT: Int = 3 //纯文字
    }

    var itemType: Int? = 3

    @Parcelize
    data class FrontMatter(
        var title: String?,
        var banner: Banner?,
        var date: String,
        var path: String
    ) : Parcelable {
        @Parcelize
        data class Banner(var childImageSharp: ChildImageSharp?) :
            Parcelable {
            @Parcelize
            data class ChildImageSharp(var fixed: Fixed?) :
                Parcelable {
                @Parcelize
                data class Fixed(var src: String?) : Parcelable
            }
        }
    }

    override fun getItemType(): Int {
        return itemType ?: 3
    }
}