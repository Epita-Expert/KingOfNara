package fr.epita.kingofnara.game

import fr.epita.kingofnara.model.Monster
import fr.epita.kingofnara.model.Plate

class PlateService(selectedMonsters:  ArrayList<Monster>) {

    private val plate: Plate;

    init
    {
        val tokySize = if (selectedMonsters.size>4)  1 else 2;
        plate = Plate(selectedMonsters, tokySize);
    }

    fun isInTokyo(monster : Monster): Boolean {
        return plate.tokyoMonsters.contains(monster);
    }

    fun isTokyoFull(): Boolean {
        return plate.tokyoMonsters.size < plate.tokyoSize;
    }

    fun isTokyoEmpty(): Boolean {
        return 0 != plate.tokyoMonsters.size;
    }

    fun removeMonster(monster:Monster): Boolean {
        return plate.outSideTokyo.remove(monster) || plate.tokyoMonsters.remove(monster);
    }

    fun addInTokyo(monster:Monster): Boolean {
        var added:Boolean = false;

        if (!isTokyoFull()) {
            plate.tokyoMonsters.add(monster);
            added = true;
        }
        return added;
    }

    fun addOutsideTokyo(monster: Monster): Boolean {
        return plate.outSideTokyo.add(monster);
    }

    fun moveMonsterToTokyo(monster:Monster): Boolean {

        var moved = false;
        if (plate.outSideTokyo.contains(monster))
        {
            removeMonster(monster);
            addInTokyo(monster);

            moved =true;
        }

        return moved;
    }

    fun moveMonsterOutsideTokyo(monster: Monster): Boolean {

        var moved = false;
        if (plate.tokyoMonsters.contains(monster))
        {
            removeMonster(monster);
            addInTokyo(monster);

            moved =true;
        }

        return moved;
    }

}