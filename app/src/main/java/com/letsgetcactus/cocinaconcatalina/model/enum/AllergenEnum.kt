package com.letsgetcactus.cocinaconcatalina.model.enum

import com.letsgetcactus.cocinaconcatalina.R

//Enum for each allergens picture on grey and color
enum class AllergenEnum(
    val greyDrawable: Int,
    val colorDrawable: Int,
) {
    MUSTARD(
        R.drawable.mustard_grey,
        R.drawable.mustard,
    ),


    PEANUT(
        R.drawable.peanut_grey,
        R.drawable.peanut,
    ),

    CRAB(
        R.drawable.crab_grey,
        R.drawable.crab,
    ),

    CELERY(
        R.drawable.celery_grey,
        R.drawable.celery,
    ),

    SULFITE(
        R.drawable.sulfite_grey,
        R.drawable.sulfite,
    ),

    EGG(
        R.drawable.egg_grey,
        R.drawable.egg,
    ),

    ALTRAMUZ(
        R.drawable.altramuz,
        R.drawable.altramuz_color,
    ),

    DAIRY(
        R.drawable.dairy,
        R.drawable.dairy_color,
    ),

    FISH(
        R.drawable.fish_grey,
        R.drawable.fish,
    ),

    MOLLUSK(
        R.drawable.mollusk_grey,
        R.drawable.mollusk,
    ),

    NUTS(
        R.drawable.nuts_grey,
        R.drawable.nuts,
    ),

    GLUTEN(
        R.drawable.gluten_grey,
        R.drawable.gluten,
    ),

    SESAME(
        R.drawable.sesame_grey,
        R.drawable.sesame,
    ),
    SOY(
    R.drawable.soja_gris,
    R.drawable.soja,
    )
}