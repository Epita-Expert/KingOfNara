package com.example.kingofnara.ui.fragment.dice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.kingofnara.R
import com.example.kingofnara.model.dice.Dice

class DiceItemsAdapter(
    private val context: Context?
    , private var dices: List<Dice>) : BaseAdapter()
{
    private var layoutInflater: LayoutInflater? = null;

    override fun getCount(): Int {
        return dices.size;
    }

    override fun getItem(position: Int): Any {
        return dices[position];
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
    {
        var convertView = convertView
        // on blow line we are checking if layout inflater
        // is null, if it is null we are initializing it.
        if (layoutInflater == null)
        {
            layoutInflater =
                context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        // on the below line we are checking if convert view is null.
        // If it is null we are initializing it.
        if (convertView == null)
        {
            // on below line we are passing the layout file
            // which we have to inflate for each item of grid view.
            convertView = layoutInflater!!.inflate(R.layout.item_dice, null)
        }
        // on below line we are initializing our course image view
        // and course text view with their ids.
        val diceValueImg : ImageView = convertView!!.findViewById(R.id.item_dice_image)
        if(null != dices[position].value)
        {
            diceValueImg.setImageResource(dices[position].value!!.resourceImg)
        }

        //if (dices[position].validated)
        //{
        //    convertView.elevation = 10F;
        //}
        convertView.isActivated = dices[position].validated;

        // at last we are returning our convert view.
        return convertView
    }

    fun setDices(dices: List<Dice>?) {
        this.dices = dices?: emptyList()
    }
}