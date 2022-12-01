package com.example.kingofnara.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.kingofnara.model.dice.DiceValue
import com.example.kingofnara.service.GameService

class GameVM()
    : ViewModel()
{
    val gameService = GameService()
}