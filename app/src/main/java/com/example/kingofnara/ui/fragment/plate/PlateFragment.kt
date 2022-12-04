package com.example.kingofnara.ui.fragment.plate

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.GridView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.kingofnara.R
import com.example.kingofnara.model.GameStep
import com.example.kingofnara.model.Monster
import com.example.kingofnara.ui.viewmodel.GameVM
import com.example.kingofnara.ui.viewmodel.PlateVM

class PlateFragment : Fragment() {

    private val viewModel : PlateVM by viewModels()
    private val gameVM : GameVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plate, container, false)
    }

    override fun onViewCreated(fragmentView: View, savedInstanceState: Bundle?) {

        if (!gameVM.ready)
        {
            findNavController().
            navigate(
                PlateFragmentDirections.actionPlateFragmentToWelcomeFragment()
            );
       }
        else
        {

            viewModel.gameService = gameVM.gameService
            viewModel.setMonstersList()

            viewModel.newLeaveAsking.observe(viewLifecycleOwner, Observer { name ->

                if (null != name) {
                    viewModel.newLeaveAsking.value = null;
                    val builder = getBuilder()
                    if (null != builder) {
                        builder.setMessage("$name, Do you want to leave tokyo?")
                        builder.show()
                    }
                }
            })

//        viewModel.nextStep.observe(viewLifecycleOwner, Observer { nextStep ->
//            doNext(nextStep)
//        })

            val scoreLayout: View = fragmentView.findViewById(R.id.score_layout)
            val currentPlayerNameTV: TextView = fragmentView.findViewById(R.id.bottom_layout_name)
            viewModel.currentPlayerName.observe(viewLifecycleOwner, Observer {
                if (View.INVISIBLE == scoreLayout.visibility || View.GONE == scoreLayout.visibility) {
                    scoreLayout.visibility = View.VISIBLE
                }
                currentPlayerNameTV.text = it
            })
            val currentPlayerVPTV: TextView = fragmentView.findViewById(R.id.nb_vp_txt)
            val currentPlayerEPTV: TextView = fragmentView.findViewById(R.id.nb_flash_txt)
            val currentPlayerHPTV: TextView = fragmentView.findViewById(R.id.nb_slap_txt)
            val currentPlayerLPTV: TextView = fragmentView.findViewById(R.id.nb_heart_txt)
            viewModel.currentScore.observe(viewLifecycleOwner, Observer {
                currentPlayerVPTV.text = it.victoryPoint.toString()
                currentPlayerEPTV.text = it.energyPoint.toString()
                currentPlayerHPTV.text = it.hitPoint.toString()
                currentPlayerLPTV.text = it.healPoint.toString()
            })

            val nextBtn: Button = fragmentView.findViewById(R.id.action_next_btn)
            nextBtn.setOnClickListener { doNext(viewModel.getNextStep()) }

            val adapterItemClickListener =
                AdapterView.OnItemClickListener { viewAdapter, _, position, _ ->
                    val adapter = viewAdapter.adapter as MonsterItemsAdapter
                    if (adapter.getItem(position) is Monster) {
                        onClickMonsterItem(adapter.getItem(position) as Monster)
                    }
                }
            val outsideTokyoLayout: GridView = fragmentView.findViewById(R.id.outsidetokyo_layout);
            outsideTokyoLayout.adapter =
                MonsterItemsAdapter(context, viewModel.outsideTokyoMonsters.value ?: ArrayList());
            outsideTokyoLayout.onItemClickListener = adapterItemClickListener
            viewModel.outsideTokyoMonsters.observe(viewLifecycleOwner, Observer {
                val monsterItemsAdapter = outsideTokyoLayout.adapter as MonsterItemsAdapter
                monsterItemsAdapter.setMonsters(it);
                monsterItemsAdapter.notifyDataSetChanged();
            })

            val insideTokyoLayout: GridView = fragmentView.findViewById(R.id.insidetokyo_layout);
            insideTokyoLayout.adapter =
                MonsterItemsAdapter(context, viewModel.insideTokyoMonsters.value ?: ArrayList());
            insideTokyoLayout.onItemClickListener = adapterItemClickListener
            viewModel.insideTokyoMonsters.observe(viewLifecycleOwner, Observer {
                val monsterItemsAdapter = insideTokyoLayout.adapter as MonsterItemsAdapter
                monsterItemsAdapter.setMonsters(it);
                monsterItemsAdapter.notifyDataSetChanged();
            })


            if (GameStep.INIT_ROUND == viewModel.getNextStep()) {
                viewModel.getNewRound()
            }
        }
    }

    private fun getBuilder(): AlertDialog? {
        val builder = AlertDialog.Builder(activity)
        builder.apply {
            setTitle("A Monster is attacking Tokyo !")
            setPositiveButton("YES",
                DialogInterface.OnClickListener { dialog, id ->
                    viewModel.setTokyoLeaver(true)
                })
            setNegativeButton("NO",
                DialogInterface.OnClickListener { dialog, id ->
                    // User cancelled the dialog
                    viewModel.setTokyoLeaver(false)
                })
        }
        return builder.create()
    }

    private fun onClickCardBtn() {
        findNavController().
        navigate(
            PlateFragmentDirections.actionPlateFragmentToCardsFragment()
        );
    }

    private fun onClickMonsterItem(monster: Monster)
    {
        findNavController().
        navigate(
            PlateFragmentDirections.actionPlateFragmentToPlayerFragment(monster)
        );

    }

    private fun onClickDiceBtn()
    {
        findNavController().navigate(
            PlateFragmentDirections.actionPlateFragmentToDiceFragment()
        );
    }

    private fun doNext(nextStep : GameStep)
    {
        Log.i(javaClass.name, "doNext() : " + nextStep.name)
        when (nextStep)
        {
            GameStep.INIT_ROUND -> viewModel.getNewRound()
            GameStep.THROW_DICE -> onClickDiceBtn()
            GameStep.RESOLVE_SCORE -> viewModel.updateScore()
            GameStep.ASK_FOR_TOKYO_LEAVER -> viewModel.askForTokyoLeaver()
            GameStep.MOVE_MONSTER -> viewModel.moveMonster()
            GameStep.CHOOSE_CARDS -> onClickCardBtn()
            GameStep.STOP_GAME -> {
                findNavController().navigate(
                    PlateFragmentDirections.actionPlateFragmentToVictoryFragment(gameVM.gameService.isWinner(), gameVM.gameService.getWinner().monster)
                )
            }
            else -> {}
        }
    }

}