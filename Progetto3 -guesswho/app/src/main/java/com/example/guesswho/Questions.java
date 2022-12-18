package com.example.guesswho;

import org.json.JSONArray;
import org.json.JSONObject;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;


public class Questions {

    private String type_question;

    public Questions() {
        this.type_question = "";
    }

    public String do_question(String maxGiniKey) {

        try {
            APIService service = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);

            Call<ResponseBody> callSync = service.getQuestions();
            Response<ResponseBody> response = callSync.execute();
            String apiResponse = null;
            if (response.body() != null) {
                apiResponse = response.body().string();
            }
            JSONObject obj = new JSONObject(apiResponse);
            JSONArray questions = obj.getJSONArray("questions");
            int i;
            for (i = 0; i < questions.length(); i++) {
                JSONObject jsonObject1 = questions.getJSONObject(i);
                if (!jsonObject1.optString(maxGiniKey).equals("")) {
                    type_question = jsonObject1.optString(maxGiniKey);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return type_question;

    }

    static void remove_question(String key) {
        try {
            APIService service = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);
            Call<ResponseBody> callSync = service.removeQuestions(key);
            Response<ResponseBody> response = callSync.execute();
            String apiResponse = null;
            if (response.body() != null) {
                apiResponse = response.body().string();
            }
            JSONObject obj = new JSONObject(apiResponse);
            JSONArray questions = obj.getJSONArray("filtered_questions");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static void clear_questions(){
        try {
            APIService service = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);
            Call<ResponseBody> callSync = service.clearQuestions();
            callSync.execute();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
