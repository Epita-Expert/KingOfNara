package com.example.kingofnara.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.kingofnara.NB_MAX_PLAYER
import com.example.kingofnara.R
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

        val nbTotalPlayerTxt = view.findViewById<TextView>(R.id.total_player_txt)
        nbTotalPlayerTxt.text = gameVM.totalPlayer.toString()

        nbBotsTxt = view.findViewById<TextView>(R.id.nb_bots_txt)
        nbPlayersTxt = view.findViewById<TextView>(R.id.nb_players_txt)

        val totalPlayerSeekBar = view.findViewById<SeekBar>(R.id.total_player_seekBar)
        totalPlayerSeekBar.max = NB_MAX_PLAYER - 1
        totalPlayerSeekBar.progress = gameVM.totalPlayer - 1


        val playerBotSeekBar = view.findViewById<SeekBar>(R.id.player_bot_seekBar)
        playerBotSeekBar.max = gameVM.totalPlayer - 1
        playerBotSeekBar.progress = gameVM.nbPlayer - 1

        playerBotSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                gameVM.nbPlayer = progress + 1;
                updateNbTxt();
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

        totalPlayerSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                gameVM.totalPlayer = progress + 1;
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                playerBotSeekBar.max = gameVM.totalPlayer - 1
                nbTotalPlayerTxt.text = gameVM.totalPlayer.toString()
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