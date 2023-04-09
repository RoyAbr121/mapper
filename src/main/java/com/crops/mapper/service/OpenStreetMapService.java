package com.crops.mapper.service;

import com.crops.mapper.model.Field;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OpenStreetMapService {

    public static final Pattern WHEAT_PATTERN = Pattern.compile("<li class=\"list-group-item search_results_entry\">Farmland.*data-lat=\"(.*)\" data-lon=\"(.*)\" data-min-lat=.*data-prefix=\"Farmland\"");

    // TODO: should the function return ArrayList<Field> or ArrayList<FieldIn>?
    public ArrayList<Field> searchFields() throws IOException {
        return parseFieldHtml(getFieldHtml());
    }

    public ArrayList<Field> parseFieldHtml(String html) {
        ArrayList<Field> result = new ArrayList<Field>();
        Matcher matcher = WHEAT_PATTERN.matcher(html);

        while (matcher.find()) {
            Double latitude = Double.valueOf(matcher.group(1));
            Double longitude = Double.valueOf(matcher.group(2));
            Field field = new Field(latitude, longitude);
            result.add(field);
        }

        return result;
    }

    public String getFieldHtml() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");
        RequestBody body = RequestBody.create(mediaType, "zoom=19&minlon=79.63113248348236&minlat=18.0182907365402&maxlon=79.63418751955034&maxlat=18.021448434767805&authenticity_token=90CmVft1wPhIOlF1DaAspMNAlSUdYUNn_dQ7EPWIrYN0RH2sU7eggdfTIS7PCTQaHa_kTe3qlpPxDlz5e8G5kw");
        Request request = new Request.Builder()
                .url("https://www.openstreetmap.org/geocoder/search_osm_nominatim?query=wheat")
                .method("POST", body)
                .addHeader("authority", "www.openstreetmap.org")
                .addHeader("accept", "*/*")
                .addHeader("accept-language", "en-US,en;q=0.5")
                .addHeader("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("cookie", "_osm_session=e870db1b34cb154278446fd8fb27b49e; _pk_id.1.cf09=7ebff3d5aa0aec89.1680438232.; _osm_totp_token=851020; _pk_ses.1.cf09=1; _osm_location=79.63266|18.01987|19|M")
                .addHeader("origin", "https://www.openstreetmap.org")
                .addHeader("referer", "https://www.openstreetmap.org/search?query=wheat")
                .addHeader("sec-ch-ua", "\"Brave\";v=\"111\", \"Not(A:Brand\";v=\"8\", \"Chromium\";v=\"111\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("sec-fetch-dest", "empty")
                .addHeader("sec-fetch-mode", "cors")
                .addHeader("sec-fetch-site", "same-origin")
                .addHeader("sec-gpc", "1")
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36")
                .addHeader("x-csrf-token", "90CmVft1wPhIOlF1DaAspMNAlSUdYUNn_dQ7EPWIrYN0RH2sU7eggdfTIS7PCTQaHa_kTe3qlpPxDlz5e8G5kw")
                .addHeader("x-requested-with", "XMLHttpRequest")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
