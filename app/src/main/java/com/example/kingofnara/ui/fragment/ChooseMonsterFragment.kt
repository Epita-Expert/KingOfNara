package com.example.kingofnara.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.kingofnara.R
import com.example.kingofnara.ui.viewmodel.GameVM

class ChooseMonsterFragment : Fragment() {

    private val gameVM : GameVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_monster, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        view.findViewById<TextView>(R.id.choose_monster_player_name).text =
            gameVM.getChooseMonsterPlayerName()

        gameVM.getAvailableMonsterToChoose().forEach { monster ->

            val monsterView = layoutInflater.inflate(R.layout.item_monster, null)
            val monsterImgView : ImageView = monsterView!!.findViewById(R.id.monsteritem_img)
            val monsterNameTxt : TextView = monsterView.findViewById(R.id.monsteritem_name)
            // on below line we are setting image for our course image view.
            monsterImgView.setImageResource(monster.resourceDraw)
            // on below line we are setting text in our course text view.
            monsterNameTxt.text = monster.name

            monsterView.setOnClickListener {

                gameVM.chooseMonsters(monster)

                if (gameVM.isMoreMonsterToChoose())
                {
                    findNavController().
                    navigate(
                       ChooseMonsterFragmentDirections.actionChooseMonsterFragmentSelf()
                    )
                }
                else
                {
                    findNavController().
                    navigate(
                        ChooseMonsterFragmentDirections.actionChooseMonsterFragmentToPlateFragment()
                    )
                }
            }

            val params = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.layoutParams = params

            view.findViewById<LinearLayout>(R.id.choose_monster_layout).addView(monsterView)
        }


    }
}