package com.han.owlmergerprototype.data

data class ThemeEntity (
    var themeText: String = "",
    var themeIcon: Int = -1,
    var themeColor: Int = 1,
    var themeTextColorOnClick: Int = 1,
    var themeColorOnClick: Int = -1,
    var presentOrder: Int = -1,
    var toggleClicked: Boolean = false)