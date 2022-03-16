package com.han.owlmergerprototype.common

import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.data.ThemeEntity

enum class sortBy {
    LATEST, POPULARITY
}

class Constants {

    public fun getCategoryList(): MutableList<ThemeEntity> {
        val categoryList = listOf("꿀팁", "해외주식", "빡공", "해외스포츠", "야식", "게임")
        return mutableListOf(
            ThemeEntity(
                categoryList[0], R.drawable.ic_idea, R.color.style1_5_20,
                R.color.style2_5, R.color.style1_5, 0, false
            ),
            ThemeEntity(
                categoryList[1], R.drawable.ic_graph, R.color.style1_4_20,
                R.color.style2_4, R.color.style1_4, 1, false
            ),
            ThemeEntity(
                categoryList[2], R.drawable.ic_book, R.color.style1_6_20,
                R.color.style2_6, R.color.style1_6, 2, false
            ),
            ThemeEntity(
                categoryList[3], R.drawable.ic_sport, R.color.style1_3_20,
                R.color.style2_3, R.color.style1_3, 3, false
            ),
            ThemeEntity(
                categoryList[4], R.drawable.ic_chicken, R.color.style1_1_20,
                R.color.style2_1, R.color.style1_1, 4, false
            ),
            ThemeEntity(
                categoryList[5], R.drawable.ic_game, R.color.style1_7_20,
                R.color.style2_7, R.color.style1_7, 5, false
            )
        )
    }
}