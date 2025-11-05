package com.letsgetcactus.cocinaconcatalina.model.enum

import com.letsgetcactus.cocinaconcatalina.R

enum class OriginEnum(override val enumId: Int): TranslatableEnum {
    JAPAN(R.string.japan),
    KOREA(R.string.korea),
    CHINA(R.string.china),
    THAILAND(R.string.thai),
    VIETNAM(R.string.vietnam),
    CHEF_MADE(R.string.chef_made)
}