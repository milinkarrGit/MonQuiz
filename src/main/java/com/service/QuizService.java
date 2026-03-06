package com.service;

import com.model.Question;
import com.model.QuizSession;
import com.repository.QuestionRepository;
import com.repository.QuizSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizSessionRepository quizSessionRepository;

    public List<Question> getQuestionsByCategory(Question.Category category, int limit) {
        List<Question> questions = questionRepository.findByCategory(category);
        Collections.shuffle(questions);
        return questions.stream().limit(limit).collect(Collectors.toList());
    }

    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    public boolean checkAnswer(Long questionId, int answerIndex) {
        return questionRepository.findById(questionId)
                .map(q -> q.getCorrectAnswerIndex() == answerIndex)
                .orElse(false);
    }

    public QuizSession saveSession(String playerName, Question.Category category, int score, int total) {
        QuizSession session = new QuizSession();
        session.setPlayerName(playerName);
        session.setCategory(category);
        session.setScore(score);
        session.setTotalQuestions(total);
        session.setCompletedAt(LocalDateTime.now());
        return quizSessionRepository.save(session);
    }

    public List<QuizSession> getLeaderboard() {
        return quizSessionRepository.findTop10ByOrderByScoreDesc();
    }

    public List<QuizSession> getLeaderboardByCategory(Question.Category category) {
        return quizSessionRepository.findTop10ByCategoryOrderByScoreDesc(category);
    }

    public Map<String, Long> getStats() {
        Map<String, Long> stats = new HashMap<>();
        for (Question.Category cat : Question.Category.values()) {
            stats.put(cat.name(), questionRepository.countByCategory(cat));
        }
        return stats;
    }

    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }
}