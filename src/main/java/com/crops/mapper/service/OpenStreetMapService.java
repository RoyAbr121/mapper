package com.crops.mapper.service;

import com.crops.mapper.model.Field;
import okhttp3.*;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OpenStreetMapService {
    public static final Pattern PATTERN_FOR_SEARCH_PAGE = Pattern.compile("<li class=\\\"list-group-item search_results_entry\\\">Farmland.*data-prefix=\\\"Farmland\\\".*href=\\\"(\\/way\\/[0-9]*)\\\">");
    public static final Pattern PATTERN_FOR_WAY_PAGE = Pattern.compile("<a class=\\\"node\\\" title=\\\"\\\" rel=\\\"nofollow\\\" href=\\\"(/node/[0-9]*)\\\"");
    public static final Pattern PATTERN_FOR_NODE_PAGE = Pattern.compile("<span class=\\\"latitude\\\">(.*)</span>, <span class=\\\"longitude\\\">(.*)</span></a>");

    public ArrayList<Field> scrapeWheatFields() throws IOException {
        ArrayList<Field> fields = new ArrayList<Field>();
        ArrayList<String> wayPageUrls = parseSearchResultsHtml(getSearchResultsHtml());

        for (String wayUrl : wayPageUrls) {
            ArrayList<String> nodePageUrls = parseWayHtml(getWayHtml(wayUrl));
            ArrayList<Point> points = new ArrayList<Point>();

            for (String nodeUrl : nodePageUrls) {
                Point point = parseNodeHtml(getNodeHtml(nodeUrl));
                points.add(point);
            }

            fields.add(new Field(new Polygon(points)));
        }
        return fields;
    }

    private static Point parseNodeHtml(String nodeHtml) {
        Point point = null;
        Matcher matcher = PATTERN_FOR_NODE_PAGE.matcher(nodeHtml);

        while (matcher.find()) {
            Double latitude = Double.parseDouble(matcher.group(1));
            Double longitude = Double.parseDouble(matcher.group(2));
            point = new Point(longitude, latitude);
        }
        return point;
    }

    private static String getNodeHtml(String nodeUrl) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("text/plain");
        Request request = new Request.Builder()
                .url(nodeUrl)
                .method("GET", null)
                .addHeader("authority", "www.openstreetmap.org")
                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8")
                .addHeader("accept-language", "en-US,en;q=0.6")
                .addHeader("cache-control", "max-age=0")
                .addHeader("cookie", "_pk_id.1.cf09=7ebff3d5aa0aec89.1680438232.; _osm_session=867c6e30ef91877f81b0c9007730d61b; _osm_totp_token=796322; _pk_ses.1.cf09=1; _osm_location=81.34080|28.20524|19|M")
                .addHeader("if-none-match", "W/\"db638860dd9af2d171559a7efd7d573d-br\"")
                .addHeader("referer", "https://www.openstreetmap.org/way/481784403")
                .addHeader("sec-ch-ua", "\"Chromium\";v=\"112\", \"Brave\";v=\"112\", \"Not:A-Brand\";v=\"99\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("sec-fetch-dest", "document")
                .addHeader("sec-fetch-mode", "navigate")
                .addHeader("sec-fetch-site", "same-origin")
                .addHeader("sec-fetch-user", "?1")
                .addHeader("sec-gpc", "1")
                .addHeader("upgrade-insecure-requests", "1")
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private static ArrayList<String> parseWayHtml(String wayHtml) {
        ArrayList<String> results = new ArrayList<>();
        Matcher matcher = PATTERN_FOR_WAY_PAGE.matcher(wayHtml);

        while (matcher.find()) {
            results.add("https://www.openstreetmap.org" + matcher.group(1));
        }
        return results;
    }

    private static String getWayHtml(String wayUrl) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("text/plain");
        Request request = new Request.Builder()
                .url(wayUrl)
                .method("GET", null)
                .addHeader("authority", "www.openstreetmap.org")
                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8")
                .addHeader("accept-language", "en-US,en;q=0.6")
                .addHeader("cache-control", "max-age=0")
                .addHeader("cookie", "_pk_id.1.cf09=7ebff3d5aa0aec89.1680438232.; _osm_session=867c6e30ef91877f81b0c9007730d61b; _osm_totp_token=796322; _pk_ses.1.cf09=1; _osm_location=81.34099|28.20517|19|M")
                .addHeader("if-none-match", "W/\"91d0dd47610c79e74d43a30eca28c950-br\"")
                .addHeader("referer", "https://www.openstreetmap.org/search?query=wheat")
                .addHeader("sec-ch-ua", "\"Chromium\";v=\"112\", \"Brave\";v=\"112\", \"Not:A-Brand\";v=\"99\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("sec-fetch-dest", "document")
                .addHeader("sec-fetch-mode", "navigate")
                .addHeader("sec-fetch-site", "same-origin")
                .addHeader("sec-fetch-user", "?1")
                .addHeader("sec-gpc", "1")
                .addHeader("upgrade-insecure-requests", "1")
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private static ArrayList<String> parseSearchResultsHtml(String html) {
        ArrayList<String> results = new ArrayList<>();
        Matcher matcher = PATTERN_FOR_SEARCH_PAGE.matcher(html);

        while (matcher.find()) {
            results.add("https://www.openstreetmap.org" + matcher.group(1));
        }
        return results;
    }

    private static String getSearchResultsHtml() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");
        RequestBody body = RequestBody.create(mediaType, "zoom=14&minlon=-80.5774211883545&minlat=39.6263832270382&maxlon=-80.52223205566406&maxlat=39.65275548355981&authenticity_token=HMj9FqLKIhpBkAhIp_dJaGfMGjDIGvgU3ocwjEutxKOe_ULUzbAUeNN1yDJgVGtFFoVI5P0wu-eP6B2hlfSZrg");
        Request request = new Request.Builder()
                .url("https://www.openstreetmap.org/geocoder/search_osm_nominatim?query=wheat")
                .method("POST", body)
                .addHeader("authority", "www.openstreetmap.org")
                .addHeader("accept", "*/*")
                .addHeader("accept-language", "en-US,en;q=0.7")
                .addHeader("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("cookie", "_pk_id.1.cf09=198a17343da5fde5.1682864204.; _osm_session=77a5fa70c62a2956d46ec812cfe64bf1; _osm_totp_token=511138; _pk_ses.1.cf09=1; _osm_location=-80.5498|39.6396|14|M")
                .addHeader("origin", "https://www.openstreetmap.org")
                .addHeader("referer", "https://www.openstreetmap.org/search?query=wheat")
                .addHeader("sec-ch-ua", "\"Chromium\";v=\"112\", \"Brave\";v=\"112\", \"Not:A-Brand\";v=\"99\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("sec-fetch-dest", "empty")
                .addHeader("sec-fetch-mode", "cors")
                .addHeader("sec-fetch-site", "same-origin")
                .addHeader("sec-gpc", "1")
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36")
                .addHeader("x-csrf-token", "HMj9FqLKIhpBkAhIp_dJaGfMGjDIGvgU3ocwjEutxKOe_ULUzbAUeNN1yDJgVGtFFoVI5P0wu-eP6B2hlfSZrg")
                .addHeader("x-requested-with", "XMLHttpRequest")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
