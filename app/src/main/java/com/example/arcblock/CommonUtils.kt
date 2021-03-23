package com.example.arcblock

import android.content.Context

/**
 * Created by Rossi on 3/21/21.
 */
class CommonUtils {

    companion object {

        /**
         * dp 转 px
         */
        fun dp2px(context: Context, dpValue: Float): Int {
            val scale: Float = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }
    }
}