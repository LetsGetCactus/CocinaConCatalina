package com.letsgetcactus.cocinaconcatalina.data.mapper

import com.letsgetcactus.cocinaconcatalina.model.enum.OriginEnum


/**
 * Maps the String we get from the field origin in the DB to our OriginEnum
 */
object OriginMapper {

    fun mapOriginToEnum(origin: String?): OriginEnum {
        if (origin.isNullOrBlank()) return OriginEnum.CHEF_MADE

        return when (origin.trim().uppercase()) {
            "JAPAN", "JAPÓN", "JAPON", "XAPÓN", "XAPON" -> OriginEnum.JAPAN
            "KOREA", "COREA" -> OriginEnum.KOREA
            "CHINA" -> OriginEnum.CHINA
            "THAILAND", "TAILANDIA" -> OriginEnum.THAILAND
            "VIETNAM", "VIET-NAM" -> OriginEnum.VIETNAM
            else -> OriginEnum.CHEF_MADE
        }
    }
}