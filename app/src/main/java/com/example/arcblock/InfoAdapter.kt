package com.example.arcblock

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.arcblock.entry.InfoData

/**
 * Created by Rossi on 2020/3/21.
 */
class InfoAdapter constructor(data: List<InfoData?>?) :
    BaseMultiItemQuickAdapter<InfoData, BaseViewHolder>(data) {

    init {
        addItemType(InfoData.BIG_PIC, R.layout.item_info_big_pic)
        addItemType(InfoData.SMALL_PIC, R.layout.item_info_small_pic)
        addItemType(InfoData.TEXT, R.layout.item_info_text)
    }

    override fun convert(helper: BaseViewHolder, item: InfoData?) {
        if (helper.itemViewType == InfoData.BIG_PIC || helper.itemViewType == InfoData.SMALL_PIC)
            Glide.with(mContext)
                .load(Api.BaseUrl + item?.frontmatter?.banner?.childImageSharp?.fixed?.src)
                .centerCrop()
                .transform(RoundedCorners(CommonUtils.dp2px(mContext, 8f)))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(helper.getView(R.id.iv_banner))

        helper.setText(R.id.tv_title, item?.frontmatter?.title)
            .setText(R.id.tv_time, item?.frontmatter?.date)
    }
}