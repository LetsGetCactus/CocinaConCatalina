package com.letsgetcactus.cocinaconcatalina.model

import com.letsgetcactus.cocinaconcatalina.R

enum class Allergen(val greyDrawable: Int, val colorDrawable: Int, val labelRes: Int) {
    MUSTARD(R.drawable.mustard_grey, R.drawable.mustard, R.string.mustard),
    PEANUT(R.drawable.peanut_grey, R.drawable.peanut, R.string.peanut),
    CRAB(R.drawable.crab_grey, R.drawable.crab, R.string.crab),
    CELERY(R.drawable.celery_grey, R.drawable.celery, R.string.celery),
    SULFITE(R.drawable.sulfite_grey, R.drawable.sulfite, R.string.sulfite),
    EGG(R.drawable.egg_grey, R.drawable.egg, R.string.egg),
    ALTRAMUZ(R.drawable.altramuz, R.drawable.altramuz, R.string.altramuz),
    DAIRY(R.drawable.dairy, R.drawable.dairy, R.string.dairy),
    FISH(R.drawable.fish_grey, R.drawable.fish, R.string.fish),
    MOLLUSK(R.drawable.mollusk_grey, R.drawable.mollusk, R.string.mollusk),
    NUTS(R.drawable.nuts_grey, R.drawable.nuts, R.string.nuts),
    GLUTEN(R.drawable.gluten_grey, R.drawable.gluten, R.string.gluten),
    SESAME(R.drawable.sesame_grey, R.drawable.sesame, R.string.sesame)
}