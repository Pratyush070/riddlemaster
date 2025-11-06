package com.pratyush.riddlemaster.service;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import com.pratyush.riddlemaster.model.Riddle;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class RiddleService {
    private final List<Riddle> riddles = new ArrayList<>();
    private final Random random = new Random();

    public RiddleService() {
        loadFromResource();
    }

    private void loadFromResource() {
        try {
            ClassPathResource res = new ClassPathResource("riddles.json");
            try (Reader reader = new InputStreamReader(res.getInputStream(), StandardCharsets.UTF_8)) {
                Type t = new TypeToken<List<Riddle>>(){}.getType();
                List<Riddle> list = new Gson().fromJson(reader, t);
                if (list != null) riddles.addAll(list);
            }
        } catch (Exception e) {
            System.err.println("Failed to load riddles: " + e.getMessage());
        }
    }

    public Riddle randomRiddle() {
        if (riddles.isEmpty()) return null;
        return riddles.get(random.nextInt(riddles.size()));
    }

    public Riddle findById(String id) {
        for (Riddle r : riddles) if (r.getId().equals(id)) return r;
        return null;
    }

    public boolean checkAnswer(Riddle r, String attempt) {
        if (r==null || attempt==null) return false;
        String a = r.getAnswer().trim().toLowerCase();
        String x = attempt.trim().toLowerCase();
        return x.equals(a) || a.contains(x) || x.contains(a);
    }
}
