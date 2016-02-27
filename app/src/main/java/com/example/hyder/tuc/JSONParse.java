package com.example.hyder.tuc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import TYCAPI.ApiException;
import TYCAPI.ApiInvoker;


/**
 * Created by hv on 5/10/15 DriverApp3.
 */
class JSONParse {

    //private static int count = 0;

    /*public static Object parseJSON(String s, Class cls) throws ApiException, JSONException {

        jsonObject = new JSONObject(s);
        jsonArray = jsonObject.getJSONArray("record");
        jsonObject = jsonArray.getJSONObject(0);
        return ApiInvoker.deserialize(jsonObject.toString(), "", cls);

    }*/

    public static ArrayList<Object> parseJSON(String s, Class cls)
            throws ApiException, JSONException {
        JSONObject jsonObject = new JSONObject(s);

        JSONArray jsonArray = jsonObject.getJSONArray("resource");
        ArrayList<Object> objectArrayList = new ArrayList<>(jsonArray.length());

        for(int i=0;i< jsonArray.length();i++)
            objectArrayList.add(
                    ApiInvoker.deserialize(jsonArray.getJSONObject(i).toString(), "", cls));
        return objectArrayList;
    }

    private static JSONObject parseString(String s) throws JSONException {
        return new JSONObject(s);
    }
/*

    public static JSONFilter getFilter(String s) throws JSONException {
        JSONFilter jsonFilter = new JSONFilter();
        jsonFilter.setFilter(parseString(s).toString());
        return jsonFilter;

        //return jsonFilter;
    }
*/

    /*public static JSONRecord getRecord(Object obj) {
        JSONRecord jsonRecord = new JSONRecord();
        jsonRecord.setRecord(obj);
        return jsonRecord;
    }*/

}
