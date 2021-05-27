package com.han.owlmergerprototype.community

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import android.text.Layout
import android.util.Log
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
import androidx.core.view.isVisible
import androidx.core.view.marginEnd
import androidx.recyclerview.widget.RecyclerView
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.data.ThemeEntity

class ThemeSelectorRecyclerAdapter(
    private val themesList: MutableList<ThemeEntity>,
    private val owner: Activity,
    private val which : Boolean //true면 메인, false 글작성
): RecyclerView.Adapter<ThemeSelectorRecyclerAdapter.ThemeHolder>() {
    public var pos = -1
    private var selectedPos = -1
    private lateinit var bgColorList: MutableList<Int>
    private lateinit var iconsList: MutableList<Int>

    inner class ThemeHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val themeSelectorCv: CardView = itemView.findViewById(R.id.theme_selector_item_cv)
        val themeSelectorItemTv: TextView = itemView.findViewById(R.id.theme_selector_item_tv)
        val themeSelectorItemIcon: TextView = itemView.findViewById(R.id.theme_selector_item_icon)
        val layout: RelativeLayout = itemView.findViewById(R.id.clicked_card_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeHolder {
        lateinit var view:View
        if(which){
            view = LayoutInflater.from(owner).inflate(R.layout.comm_theme_item, parent, false)
        }else{
            view = LayoutInflater.from(owner).inflate(R.layout.select_theme_item, parent, false)
        }

        return ThemeHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ThemeHolder, position: Int) {
        val themeEntity = themesList[position]
        with (holder) {
            themeSelectorItemTv.text = themeEntity.themeText
            themeSelectorItemTv.setTextColor(owner.resources.getColor(themeEntity.themeColorOnClick))
            themeSelectorCv.setCardBackgroundColor(owner.resources.getColor(themeEntity.themeColor))
            themeSelectorItemIcon.background = getDrawable(owner, themeEntity.themeIcon)

//            themeSelectorCv.setCardBackgroundColor(ContextCompat.getColor(owner, R.color.error2_1))//themeEntity.themeColor))
//            themeSelectorCv.setBackgroundColor(ContextCompat.getColor(owner, themeEntity.themeColor))
            holder.themeSelectorCv.setOnClickListener {
                if (themeEntity.toggleClicked) {
                    themeEntity.toggleClicked = false
                    selectedPos = -1
                    pos = -1
                } else {
                    themeEntity.toggleClicked = true
                    selectedPos = position
                    pos = position
                    Log.d("TAG", "onBindViewHolder: ${position}")

                        itemClickListner.onClick(it, position)


                    // ((INEFFECTIVE!!)) if newly clicked, reset all other toggle values
                    for (i in 0 until themesList.size) {
                        if (i != position) {
                            themesList[i].toggleClicked = false
                        }
                    }
                }
                notifyDataSetChanged()
            }
            if (/*selectedPos == position*/pos == position) {
//                themeSelectorCv.setCardBackgroundColor(ContextCompat.getColor(owner, themeEntity.themeColorOnClick))
//                themeSelectorCv.alpha = 0.3f
//                themeSelectorCv.setCardBackgroundColor(ContextCompat.getColor(owner, themesList[position].themeColor))
//                Toast.makeText(owner, "original color: ${themeEntity.themeColor}", Toast.LENGTH_SHORT).show()
                themeSelectorCv.setCardBackgroundColor(owner.resources.getColor(themeEntity.themeColorOnClick))
                layout.setBackgroundColor(owner.resources.getColor(themeEntity.themeColor))
                themeSelectorItemTv.setTextColor(owner.resources.getColor(themeEntity.themeTextColorOnClick))
                layout.isVisible = true
            } else {
                // reset color for each theme selector
//                themeSelectorCv.alpha = 1f
//                themeSelectorCv.setCardBackgroundColor(ContextCompat.getColor(owner, themesList[position].themeColor))
//                themeSelectorCv.setCardBackgroundColor(ContextCompat.getColor(owner, themesList[position % themesList.size].themeColor))
                themeSelectorCv.setCardBackgroundColor(owner.resources.getColor(themeEntity.themeColor))
                themeSelectorItemTv.setTextColor(owner.resources.getColor(themeEntity.themeColorOnClick))
                layout.isVisible = false
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

    private fun getSelectedIndex() = selectedPos

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    //클릭리스너 선언
    private lateinit var itemClickListner: ItemClickListener

    //클릭리스너 등록 매소드
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    }


}