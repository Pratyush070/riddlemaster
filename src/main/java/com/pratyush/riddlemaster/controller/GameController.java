package com.pratyush.riddlemaster.controller;

import com.pratyush.riddlemaster.model.Riddle;
import com.pratyush.riddlemaster.model.LeaderboardEntry;
import com.pratyush.riddlemaster.service.LeaderboardService;
import com.pratyush.riddlemaster.service.RiddleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GameController {
    private final RiddleService riddleService;
    private final LeaderboardService leaderboardService;

    public GameController(RiddleService riddleService, LeaderboardService leaderboardService) {
        this.riddleService = riddleService;
        this.leaderboardService = leaderboardService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("riddle", riddleService.randomRiddle());
        model.addAttribute("top", leaderboardService.topN(10));
        return "index";
    }

    @PostMapping("/check")
    public String check(@RequestParam String id, @RequestParam String attempt, Model model) {
        Riddle r = riddleService.findById(id);
        boolean ok = riddleService.checkAnswer(r, attempt);
        model.addAttribute("result", ok);
        model.addAttribute("attempt", attempt);
        model.addAttribute("riddle", r);
        return "play";
    }

    @PostMapping("/save")
    public String save(@RequestParam String name, @RequestParam int score) {
        leaderboardService.add(name, score);
        return "redirect:/";
    }

    @GetMapping("/leaderboard")
    public String leaderboard(Model model) {
        List<LeaderboardEntry> top = leaderboardService.topN(50);
        model.addAttribute("top", top);
        return "leaderboard";
    }
}
