package com.example.kingofnara.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.kingofnara.R
import com.example.kingofnara.model.Monster
import com.example.kingofnara.ui.viewmodel.GameVM
import com.example.kingofnara.ui.viewmodel.PlateVM
import com.example.kingofnara.ui.viewmodel.PlayerVM

class PlayerFragment : Fragment()
{

    private val args : PlayerFragmentArgs by navArgs()
    private val gameMV : GameVM by activityViewModels()
    private val viewModel : PlayerVM by viewModels()

    private lateinit var playerImg : ImageView;
    private lateinit var playerNameTxt : TextView;
    private lateinit var playerEnergyPointTxt: TextView
    private lateinit var playerLifePointTxt: TextView
    private lateinit var playerVPTxt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onViewCreated(fragmentView: View, savedInstanceState: Bundle?)
    {
        viewModel.gameService = gameMV.gameService

        playerNameTxt = fragmentView.findViewById(R.id.player_name_txt)
        playerImg = fragmentView.findViewById(R.id.player_img)
        playerVPTxt = fragmentView.findViewById(R.id.player_vp_txt)
        playerLifePointTxt = fragmentView.findViewById(R.id.player_life_txt)
        playerEnergyPointTxt = fragmentView.findViewById(R.id.player_energy_txt)

        viewModel.player.observe(viewLifecycleOwner, Observer {
            if (null != it)
            {
                playerImg.setImageResource(viewModel.getPlayerImgResource())
                playerNameTxt.text = viewModel.getMonsterNameString()
                playerVPTxt.text = viewModel.getVPString()
                playerLifePointTxt.text = viewModel.getLPString()
                playerEnergyPointTxt.text = viewModel.getEPString()
            }
        })
    }

    override fun onResume()
    {
        super.onResume()
        viewModel.setPlayer(args.monster)
    }
}