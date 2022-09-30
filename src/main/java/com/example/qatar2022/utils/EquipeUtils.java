/*package com.example.qatar2022.utils;

import com.example.qatar2022.entities.Equipe;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class EquipeUtils {

    private static List<Equipe> equipes = new ArrayList<Equipe>();

    private static final int NUM_EQUIPE = 30;

    private static final int MIN_EQUIPE_NUM= 1000;


    public static List<Equipe> buildEquipes()
    {
        if(equipes.isEmpty())
        {
            IntStream.range(0, NUM_EQUIPE).forEach(n -> {
                equipes.add(new Equipe(MIN_EQUIPE_NUM + n + 1, "Spring in action"));
            });
        }
        return equipes;
    }
}

 */
