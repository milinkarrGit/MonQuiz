package com.controller;

import com.model.Question;
import com.model.QuizSession;
import com.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quiz")
@CrossOrigin(origins = "*")
public class QuizController {

    @Autowired
    private QuizService quizService;

    // Récupérer les questions par catégorie
    @GetMapping("/questions/{category}")
    public ResponseEntity<List<Question>> getQuestions(
            @PathVariable String category,
            @RequestParam(defaultValue = "10") int limit) {
        try {
            Question.Category cat = Question.Category.valueOf(category.toUpperCase());
            List<Question> questions = quizService.getQuestionsByCategory(cat, limit);
            // Masquer la bonne réponse dans la réponse
            questions.forEach(q -> q.setCorrectAnswerIndex(-1));
            return ResponseEntity.ok(questions);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Vérifier une réponse
    @PostMapping("/check")
    public ResponseEntity<Map<String, Object>> checkAnswer(@RequestBody Map<String, Object> payload) {
        Long questionId = Long.valueOf(payload.get("questionId").toString());
        int answerIndex = Integer.parseInt(payload.get("answerIndex").toString());

        boolean correct = quizService.checkAnswer(questionId, answerIndex);
        Question question = quizService.getQuestionById(questionId).orElse(null);

        return ResponseEntity.ok(Map.of(
                "correct", correct,
                "correctIndex", question != null ? question.getCorrectAnswerIndex() : -1,
                "explanation", question != null && question.getExplanation() != null ? question.getExplanation() : ""
        ));
    }

    // Sauvegarder une session de quiz
    @PostMapping("/session")
    public ResponseEntity<QuizSession> saveSession(@RequestBody Map<String, Object> payload) {
        String playerName = payload.get("playerName").toString();
        String categoryStr = payload.get("category").toString();
        int score = Integer.parseInt(payload.get("score").toString());
        int total = Integer.parseInt(payload.get("total").toString());

        Question.Category category = Question.Category.valueOf(categoryStr.toUpperCase());
        QuizSession session = quizService.saveSession(playerName, category, score, total);
        return ResponseEntity.ok(session);
    }

    // Leaderboard global
    @GetMapping("/leaderboard")
    public ResponseEntity<List<QuizSession>> getLeaderboard() {
        return ResponseEntity.ok(quizService.getLeaderboard());
    }

    // Leaderboard par catégorie
    @GetMapping("/leaderboard/{category}")
    public ResponseEntity<List<QuizSession>> getLeaderboardByCategory(@PathVariable String category) {
        try {
            Question.Category cat = Question.Category.valueOf(category.toUpperCase());
            return ResponseEntity.ok(quizService.getLeaderboardByCategory(cat));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Statistiques
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getStats() {
        return ResponseEntity.ok(quizService.getStats());
    }

    // Ajouter une question (admin)
    @PostMapping("/questions")
    public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
        return ResponseEntity.ok(quizService.addQuestion(question));
    }

    // Supprimer une question (admin)
    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        quizService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }

    // Toutes les questions (admin)
    @GetMapping("/questions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return ResponseEntity.ok(quizService.getAllQuestions());
    }
}