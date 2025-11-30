package com.letsgetcactus.cocinaconcatalina.model.enum

import com.letsgetcactus.cocinaconcatalina.R

enum class OriginEnum(override val enumId: Int, val flag: Int): TranslatableEnum {
    JAPAN(
        R.string.japan,
        R.drawable.japan_flag),
    KOREA(R.string.korea,
        R.drawable.korea_flag),
    CHINA(R.string.china,
        R.drawable.china_flag),
    THAILAND(R.string.thai,
        R.drawable.thailand_flag),
    VIETNAM(R.string.vietnam,
        R.drawable.vietnam_flag),
    CHEF_MADE(R.string.chef_made,
        R.drawable.chef_flag)
}