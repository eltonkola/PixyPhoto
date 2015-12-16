package com.eltonkola;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

public class WWArtDownloader {

    private final static String TOP_CHARTS = "http://charts.spotify.com/api/tracks/most_streamed/global/daily/latest";
    /*
    tracks: [
        {
        date: "2015-03-23",
        country: "global",
        track_url: "https://play.spotify.com/track/78TTtXnFQPzwqlbtbwqN0y",
        track_name: "FourFiveSeconds",
        artist_name: "Rihanna",
        artist_url: "https://play.spotify.com/artist/5pKCCKE2ajJHZ9KAiaK11H",
        album_name: "FourFiveSeconds",
        album_url: "https://play.spotify.com/album/7yBl4uFyJzH48Vy6tPieXL",
        artwork_url: "http://o.scdn.co/300/430d7afebf738c788ccadc1b2488ab956465236f",
        num_streams: 1872403,
        window_type: "daily",
        percent_male: 37,
        percent_age_group_0_17: 0,
        percent_age_group_18_24: 45,
        percent_age_group_25_29: 18,
        percent_age_group_30_34: 8,
        percent_age_group_35_44: 7,
        percent_age_group_45_54: 2,
        percent_age_group_55_plus: 20
        }
      ]
     */


    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public void loadPics(){
        try{
            JSONObject json = readJsonFromUrl(TOP_CHARTS);
            System.out.println(json.toString());
            JSONArray tracks = json.getJSONArray("tracks");
            System.out.println("tracks:" + tracks);
            for(int i = 0; i < tracks.length(); i++){
                JSONObject track = tracks.getJSONObject(i);
                System.out.println("track:" + track);

                String artwork_url = track.getString("artwork_url");
                String track_name = track.getString("track_name");
                String artist_name = track.getString("artist_name");

                System.out.println("art:" + artwork_url);
                download(artwork_url, "art_images/" + artist_name + "_" + track_name + ".jpg");
            }

            System.out.println("---------- done ---------");
        }catch (IOException e1){

        }catch (JSONException e2){

        }catch (Exception e3){

        }

    }

    private void download(String fileUrl, String fileName){
        try {
            URL url = new URL(fileUrl);
            InputStream in = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while (-1 != (n = in.read(buf))) {
                out.write(buf, 0, n);
            }
            out.close();
            in.close();
            byte[] response = out.toByteArray();


            FileOutputStream fos = new FileOutputStream(fileName);
            fos.write(response);
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
