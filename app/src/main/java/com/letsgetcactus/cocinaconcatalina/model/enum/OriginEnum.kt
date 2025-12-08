package com.letsgetcactus.cocinaconcatalina.model.enum

import com.letsgetcactus.cocinaconcatalina.R

/**
 * Land of origin of the different asian recipes on the DB
 * @param enumId name of the country
 * @param flag of the country
 */
enum class OriginEnum(override val enumId: Int, val flag: Int): TranslatableEnum {
    JAPON(
        R.string.japan,
        R.drawable.japon_flag),
    KOREA(R.string.korea,
        R.drawable.korea_flag),
    CHINA(R.string.china,
        R.drawable.china_flag),
    TAILANDIA(R.string.thai,
        R.drawable.tailandia_flag),
    VIETNAM(R.string.vietnam,
        R.drawable.vietnam_flag),
    CHEF_MADE(R.string.chef_made,
        R.drawable.chef_flag)
}