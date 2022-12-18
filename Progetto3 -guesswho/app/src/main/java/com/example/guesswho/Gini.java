package com.example.guesswho;

import android.annotation.TargetApi;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Gini {

    private static HashMap<String, Float> characteristics = null;

    private static List<String> features = new ArrayList<>(Arrays.asList("surname", "category", "age_category", "gender",
            "skin_color", "citizenship", "hair_color", "hair_shape", "hair_length", "eyes_color", "eyes_shape", "profession",
            "lips_shape", "lips_color", "smile", "microphone", "civil_status", "children", "glasses", "glasses_type", "hat",
            "beard", "beard_type", "height", "body", "shirt", "shirt_color", "sweater", "sweater_color", "elegant_clothes_color", "elegant_clothes_type",
            "elegant_clothes", "tie", "tie_type", "jacket", "scarf", "earing", "necklace", "interpretation"));

    private static List<List> featuresLists = new ArrayList<>();

    Gini() {
        for (int i = 0; i < features.size() - 1; i++) featuresLists.add(new ArrayList());
    }

    @TargetApi(24)
    static HashMap<String, Float> characters(JSONArray data) throws JSONException {
        characteristics = new HashMap<>();

        for(List l : featuresLists) l.clear();

        for (int j = 0; j < features.size() -1; j++) {
            for(int i=0; i < data.length() -1; i++){
                featuresLists.get(j).add(data.getJSONObject(i).optString(features.get(j)));
            }
        }

        for (int i = 0; i < featuresLists.size() -1; i++) countOccurences(featuresLists.get(i));

        return characteristics;
    }


    @TargetApi(24)
    private static void countOccurences(List<String> featureList) {
        for (String featureValue : featureList) {
            if (characteristics.containsKey(featureValue)) {
                characteristics.put(featureValue, (characteristics.get(featureValue) + (float) 1));
            } else {
                characteristics.put(featureValue, (float) 1);
            }
        }
    }

    static HashMap<String, Float> compute_gini(HashMap<String, Float> results, int data_length) {
        for (Map.Entry<String, Float> entry : results.entrySet()) {
            Float newValue = (2 * data_length) * (entry.getValue() / data_length) * (1 - (entry.getValue() / data_length));
            results.put(entry.getKey(), newValue);
        }
        return results;
    }

    static String maximun_of_gini(HashMap<String, Float> characteristicGini) {
        String giniKey = "";
        Float maxGini = (float) 0;
        for (Map.Entry<String, Float> entry : characteristicGini.entrySet()) {
            if (entry.getValue() >= maxGini) {
                giniKey = entry.getKey();
                maxGini = entry.getValue();
            }
        }
        return giniKey;
    }
}
