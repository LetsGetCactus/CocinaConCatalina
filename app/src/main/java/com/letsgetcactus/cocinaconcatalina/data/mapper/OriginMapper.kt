package com.letsgetcactus.cocinaconcatalina.data.mapper

import com.letsgetcactus.cocinaconcatalina.model.enum.OriginEnum


/**
 * Maps the String we get from the field origin in the DB to our OriginEnum
 */
object OriginMapper {
    fun mapOriginToEnum(country: String?): OriginEnum {
        return when (country?.trim()?.uppercase()) {
            "JAPON", "JAPÓN" -> OriginEnum.JAPON
            "KOREA", "COREA" -> OriginEnum.KOREA
            "CHINA" -> OriginEnum.CHINA
            "TAILANDIA" -> OriginEnum.TAILANDIA
            "VIETNAM" -> OriginEnum.VIETNAM
            else -> OriginEnum.CHEF_MADE
        }
    }
}
