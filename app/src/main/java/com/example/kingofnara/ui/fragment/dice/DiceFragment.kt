package com.example.kingofnara.ui.fragment.dice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.GridView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.kingofnara.R
import com.example.kingofnara.ui.viewmodel.DiceVM
import com.example.kingofnara.model.dice.Dice
import com.example.kingofnara.ui.fragment.plate.PlateFragmentDirections
import com.example.kingofnara.ui.viewmodel.GameVM

class DiceFragment : Fragment()
{
    private val viewModel : DiceVM by viewModels()
    private val gameVM : GameVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dice, container, false)
    }

    override fun onViewCreated(fragmentView: View, savedInstanceState: Bundle?)
    {
        viewModel.gameService = gameVM.gameService

        val diceGrid : GridView = fragmentView.findViewById(R.id.dice_layout);
        val diceItemsAdapter = DiceItemsAdapter(context, viewModel.getDices());
        diceGrid.adapter = diceItemsAdapter

        diceGrid.onItemClickListener =
            AdapterView.OnItemClickListener {adapterView, view, position, _ ->
                val dice : Dice = diceItemsAdapter.getItem(position) as Dice
                view.isActivated = viewModel.changeDiceValidatedState(dice)
            }

        viewModel.dices.observe(viewLifecycleOwner, Observer {
            diceItemsAdapter.setDices(it)
            diceItemsAdapter.notifyDataSetChanged()
        });


        val actionBtn : Button = fragmentView.findViewById(R.id.dice_action_button);
        actionBtn.setOnClickListener { viewModel.nextStep() }

        /*viewModel.endDices.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(
                DiceFragmentDirections.actionDiceFragmentToPlateFragment()
            );
        });
         */

    }
}