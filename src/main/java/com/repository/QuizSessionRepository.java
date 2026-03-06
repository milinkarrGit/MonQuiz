
package com.repository;

import com.model.QuizSession;
import com.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuizSessionRepository extends JpaRepository<QuizSession, Long> {

    List<QuizSession> findTop10ByOrderByScoreDesc();

    List<QuizSession> findTop10ByCategoryOrderByScoreDesc(Question.Category category);
}
