package com.pratyush.riddlemaster.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.pratyush.riddlemaster.model.LeaderboardEntry;
import org.springframework.stereotype.Service;

import java.io.Writer;
import java.io.Reader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class LeaderboardService {
    private final Path file = Path.of("leaderboard-web.json");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public LeaderboardService() {
        try {
            if (!Files.exists(file)) {
                Files.writeString(file, "[]", StandardOpenOption.CREATE);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void add(String name, int score) {
        try {
            List<LeaderboardEntry> list = readAll();
            boolean found = false;
            for (LeaderboardEntry e : list) {
                if (e.getName().equalsIgnoreCase(name)) {
                    e.setScore(e.getScore() + score);
                    found = true;
                    break;
                }
            }
            if (!found) {
                list.add(new LeaderboardEntry(name, score, Instant.now().toString()));
            }
            list.sort(Comparator.comparingInt(LeaderboardEntry::getScore).reversed());
            try (Writer w = Files.newBufferedWriter(file, StandardOpenOption.TRUNCATE_EXISTING)) {
                gson.toJson(list, w);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<LeaderboardEntry> topN(int n) {
        try {
            List<LeaderboardEntry> list = readAll();
            list.sort(Comparator.comparingInt(LeaderboardEntry::getScore).reversed());
            return list.subList(0, Math.min(n, list.size()));
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private List<LeaderboardEntry> readAll() {
        try (Reader r = Files.newBufferedReader(file)) {
            Type t = new TypeToken<List<LeaderboardEntry>>(){}.getType();
            List<LeaderboardEntry> list = gson.fromJson(r, t);
            return list == null ? new ArrayList<>() : list;
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
