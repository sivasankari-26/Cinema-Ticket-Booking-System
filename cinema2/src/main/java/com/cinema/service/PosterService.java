package com.cinema.service;

import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Service
public class PosterService {

    // Free OMDB API key - works for basic usage
    private static final String API_KEY = "aa9290e";

    public String fetchPoster(String movieTitle) {
        try {
            String encoded = URLEncoder.encode(movieTitle, "UTF-8");
            String urlStr = "http://www.omdbapi.com/?t=" + encoded + "&apikey=" + API_KEY;

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream())
            );
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);
            reader.close();

            String json = sb.toString();

            // Parse "Poster" field from JSON manually
            if (json.contains("\"Poster\":")) {
                int start = json.indexOf("\"Poster\":\"") + 10;
                int end = json.indexOf("\"", start);
                String poster = json.substring(start, end);
                if (!poster.equals("N/A") && poster.startsWith("http")) {
                    return poster;
                }
            }
        } catch (Exception e) {
            System.out.println("Poster fetch failed for: " + movieTitle);
        }
        // Return a fallback placeholder if not found
        return "https://via.placeholder.com/200x300/1a1a2e/ffffff?text=" +
               movieTitle.replace(" ", "+");
    }
}
