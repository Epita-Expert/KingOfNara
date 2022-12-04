package com.example.kingofnara.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.kingofnara.NB_MAX_PLAYER
import com.example.kingofnara.R
import com.example.kingofnara.ui.fragment.dice.DiceFragmentDirections
import com.example.kingofnara.ui.viewmodel.GameVM

class WelcomeFragment : Fragment()
{

    private lateinit var nbPlayersTxt: TextView
    private lateinit var nbBotsTxt: TextView

    private val gameVM : GameVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        view.findViewById<Button>(R.id.welcome_validate_btn).setOnClickListener {
            gameVM.validateInit()
                if (gameVM.isMoreMonsterToChoose())
                {
                    findNavController().
                    navigate(
                        WelcomeFragmentDirections.actionWelcomeFragmentToChooseMonsterFragment()
                    )
                }
                else
                {
                    findNavController().
                    navigate(
                    WelcomeFragmentDirections.actionWelcomeFragmentToPlateFragment()
                    )
                }
        }

        nbBotsTxt = view.findViewById<TextView>(R.id.nb_bots_txt)
        nbPlayersTxt = view.findViewById<TextView>(R.id.nb_players_txt)

        val playerSeekBar2 = view.findViewById<SeekBar>(R.id.seekBar2)
        playerSeekBar2.max = NB_MAX_PLAYER
        playerSeekBar2.progress = gameVM.totalPlayer


        val playerSeekBar = view.findViewById<SeekBar>(R.id.seekBar)
        playerSeekBar.max = gameVM.totalPlayer - 1
        playerSeekBar.progress = gameVM.nbPlayer - 1

        playerSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                gameVM.nbPlayer = progress + 1;
                updateNbTxt();
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

        playerSeekBar2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                gameVM.totalPlayer = progress + 1;
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                playerSeekBar.max = gameVM.totalPlayer - 1
                updateNbTxt()
            }

        })
    }

    private fun updateNbTxt()
    {
        nbPlayersTxt.text = gameVM.nbPlayer.toString()
        nbBotsTxt.text = gameVM.getNbBotsTxt().toString()
    }

}