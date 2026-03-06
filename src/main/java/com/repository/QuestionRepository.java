
package com.repository;

import com.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByCategory(Question.Category category);

    List<Question> findByCategoryAndDifficulty(Question.Category category, Question.Difficulty difficulty);

    @Query("SELECT q FROM Question q WHERE q.category = :category ORDER BY RANDOM()")
    List<Question> findRandomByCategory(Question.Category category);

    long countByCategory(Question.Category category);
}