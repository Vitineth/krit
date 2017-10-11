package com.github.vitineth.game.krit.storage;

import com.github.vitineth.game.krit.Krit;
import com.github.vitineth.game.krit.utils.RandomUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class Description
 * <p/>
 * File created by Ryan (vitineth).<br>
 * Created on 07/10/2017.
 *
 * @author Ryan (vitineth)
 * @since 07/10/2017
 */
public class NameStorage {

    private static List<String> possibilities = new ArrayList<>();
    private static List<String> used = new ArrayList<>();
    private static HashMap<Krit, String> map = new HashMap<>();

    public static void initialise(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("C:\\cygwin64\\home\\Ryan\\names.txt"));
            br.lines().forEach(l -> possibilities.add(l.substring(0, 1) + l.substring(1).toLowerCase()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void map(Krit krit){
        String selection = RandomUtils.choice(possibilities);
        while (used.contains(selection)) selection = RandomUtils.choice(possibilities);
        map.put(krit, selection);
        used.add(selection);
    }

    public static String get(Krit krit){
        if (map.containsKey(krit)) return map.get(krit);
        else return null;
    }

}
