package com.han.owlmergerprototype.community

import android.app.Activity
import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.graphics.alpha
import androidx.core.view.marginEnd
import androidx.recyclerview.widget.RecyclerView
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.data.ThemeEntity

class ThemeSelectorRecyclerAdapter(
    private val themesList: MutableList<ThemeEntity>,
    private val owner: Activity
): RecyclerView.Adapter<ThemeSelectorRecyclerAdapter.ThemeHolder>() {
    private var selectedPos = -1
    private lateinit var bgColorList: MutableList<Int>
    private lateinit var iconsList: MutableList<Int>

    inner class ThemeHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val themeSelectorCv: CardView = itemView.findViewById(R.id.theme_selector_item_cv)
        val themeSelectorItemTv: TextView = itemView.findViewById(R.id.theme_selector_item_tv)
        val themeSelectorItemIcon: TextView = itemView.findViewById(R.id.theme_selector_item_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeHolder {
        val view = LayoutInflater.from(owner).inflate(R.layout.comm_theme_item, parent, false)
        return ThemeHolder(view)
    }

    override fun onBindViewHolder(holder: ThemeHolder, position: Int) {
        val themeEntity = themesList[position]
        with (holder) {
            themeSelectorItemTv.text = themeEntity.themeText
            themeSelectorItemIcon.background = getDrawable(owner, themeEntity.themeIcon)
            themeSelectorCv.setCardBackgroundColor(Color.parseColor("#FF0800"))
//            themeSelectorCv.setCardBackgroundColor(ContextCompat.getColor(owner, R.color.error2_1))//themeEntity.themeColor))
//            themeSelectorCv.setBackgroundColor(ContextCompat.getColor(owner, themeEntity.themeColor))
            themeSelectorCv.setOnClickListener {
                if (themeEntity.toggleClicked) {
                    themeEntity.toggleClicked = false
                    selectedPos = -1
                } else {
                    themeEntity.toggleClicked = true
                    selectedPos = position

                    // ((INEFFECTIVE!!)) if newly clicked, reset all other toggle values
                    for (i in 0 until themesList.size) {
                        if (i != position) {
                            themesList[i].toggleClicked = false
                        }
                    }
                }
                notifyDataSetChanged()
            }
            if (selectedPos == position) {
//                themeSelectorCv.setCardBackgroundColor(ContextCompat.getColor(owner, themeEntity.themeColorOnClick))
                themeSelectorCv.alpha = 0.6f
                themeSelectorCv.setCardBackgroundColor(ContextCompat.getColor(owner, themesList[position].themeColor))
//                Toast.makeText(owner, "original color: ${themeEntity.themeColor}", Toast.LENGTH_SHORT).show()
            } else {
                // reset color for each theme selector
                themeSelectorCv.alpha = 1f
                themeSelectorCv.setCardBackgroundColor(ContextCompat.getColor(owner, themesList[position].themeColor))
//                themeSelectorCv.setCardBackgroundColor(ContextCompat.getColor(owner, themesList[position % themesList.size].themeColor))
            }
        }
    }

    private fun initColorsAndIcons() {
        for (i in 0 until themesList.size) {
            bgColorList.add(themesList[i].themeColor)
            iconsList.add(themesList[i].themeIcon)
        }
    }

    override fun getItemCount() = themesList.size
}