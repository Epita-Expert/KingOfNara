package com.example.kingofnara.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.kingofnara.R
import com.example.kingofnara.ui.fragment.dice.DiceFragmentDirections
import com.example.kingofnara.ui.viewmodel.CardsVM
import com.example.kingofnara.ui.viewmodel.GameVM
import fr.epita.kingofnara.model.EnergyCard

class CardsFragment : Fragment() {

    private val viewModel : CardsVM by viewModels()
    private val gameVM : GameVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cards, container, false)
    }

    @SuppressLint("MissingInflatedId")
    override fun onViewCreated(fragmentView: View, savedInstanceState: Bundle?) {
        viewModel.gameService = gameVM.gameService

        val cardsList : LinearLayout = fragmentView.findViewById(R.id.card_list_layout)
        viewModel.getCards().forEach { card -> addCardInList(card, cardsList) }

        fragmentView.findViewById<Button>(R.id.card_redraw_btn).setOnClickListener{
            if (viewModel.isDrawAvailable())
            {
                viewModel.draw()
                cardsList.removeAllViews()
                viewModel.getCards().forEach { card -> addCardInList(card, cardsList) }
            }
            else
            {
                informNotEnoughEnergy()
            }
        }

        fragmentView.findViewById<Button>(R.id.card_validate_btn).setOnClickListener {
            viewModel.validate()
            findNavController().
            navigate(
                CardsFragmentDirections.actionCardsFragmentToPlateFragment()
            )
        }

    }

    @SuppressLint("MissingInflatedId")
    private fun addCardInList(card: EnergyCard, cardsList: LinearLayout)
    {
        val cardView : LinearLayout = layoutInflater.inflate(R.layout.item_card, null) as LinearLayout
        cardView.findViewById<TextView>(R.id.card_title_txt).text = card.name
        cardView.findViewById<TextView>(R.id.card_type_txt).text = card.type.name
        cardView.findViewById<ImageView>(R.id.card_img).setImageResource(card.imgResource)
        cardView.findViewById<TextView>(R.id.card_description).text = card.effect
        cardView.findViewById<TextView>(R.id.card_price).text = card.price.toString()

        val params = LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
        params.weight = 1F
        cardView.layoutParams = params
        cardView.weightSum = 13F

        cardView.setOnClickListener { onClickCard(card, cardView, cardsList) }

        cardsList.addView(cardView);
    }

    private fun onClickCard(card: EnergyCard, cardView: View, cardsList: LinearLayout)
    {
        if (viewModel.isCardAvailable(card))
        {
            cardsList.removeView(cardView)
            addCardInList(viewModel.selectCard(card), cardsList)
        }
        else
        {
            informNotEnoughEnergy()
        }
    }

    private fun informNotEnoughEnergy()
    {
        Toast.makeText(context, "Not enough energy :(", Toast.LENGTH_SHORT).show()
    }
}