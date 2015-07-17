package com.eleks.rnd.nearables;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bogdan.melnychuk on 16.07.2015.
 */
public class Stickers {
    private static final Map<String, String> uuids = new HashMap<String, String>();

    static {
        uuids.put("b4aaa62e03d1dcef", "Dog");
        uuids.put("e59b0d69306fab9f", "Chair");
        uuids.put("f65bb59e07eb23ab", "Door");
        uuids.put("0832bc783a80b289", "Shoes");
    }

    public static final String getStickerName(String stikerId) {
        if (stikerId == null) {
            return null;
        }
        return uuids.get(stikerId);
    }

}
