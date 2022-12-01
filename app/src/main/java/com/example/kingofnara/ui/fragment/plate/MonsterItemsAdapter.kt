package com.example.kingofnara.ui.fragment.plate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.kingofnara.R
import com.example.kingofnara.model.Monster

class MonsterItemsAdapter(private val context: Context?
                          , private var monsters: List<Monster?> = ArrayList())
    : BaseAdapter()
{

    private var layoutInflater: LayoutInflater? = null

    override fun getCount(): Int {
        return monsters.size
    }

    override fun getItem(position: Int): Any {
        return monsters[position]?:0
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        // on blow line we are checking if layout inflater
        // is null, if it is null we are initializing it.
        if (layoutInflater == null) {
            layoutInflater =
            context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            }
        // on the below line we are checking if convert view is null.
        // If it is null we are initializing it.
        if (convertView == null)
        {
            // on below line we are passing the layout file
            // which we have to inflate for each item of grid view.
            convertView = layoutInflater!!.inflate(R.layout.item_monster, null)
        }

        monsters[position]?.let {
            // on below line we are initializing our course image view
            // and course text view with their ids.
            val monsterImgView : ImageView = convertView!!.findViewById(R.id.monsteritem_img)
            val monsterNameTxt : TextView = convertView.findViewById(R.id.monsteritem_name)
            // on below line we are setting image for our course image view.
            monsterImgView.setImageResource(it.resourceDraw)
            // on below line we are setting text in our course text view.
            monsterNameTxt.text = it.name
        }

        // at last we are returning our convert view.
        return convertView!!
    }

    fun setMonsters(it: List<Monster>?) {
        monsters = it?:ArrayList()
    }
}