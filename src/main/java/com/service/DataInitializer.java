
package com.service;

import com.model.Question;
import com.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public void run(String... args) {
        if (questionRepository.count() > 0) return;

        // ANGLAIS
        save("What is the plural of 'child'?",
                List.of("childs","children","childrens","childer"),
                1, Question.Category.ANGLAIS, Question.Difficulty.FACILE,
                "'Children' est le pluriel irrégulier de 'child'.");

        save("Choose the correct sentence:",
                List.of("She don't like coffee.","She doesn't likes coffee.","She doesn't like coffee.","She not like coffee."),
                2, Question.Category.ANGLAIS, Question.Difficulty.FACILE,
                "Avec she/he/it on utilise 'doesn't' + verbe de base.");

        save("What does 'benevolent' mean?",
                List.of("Hostile","Kind and charitable","Strict","Indifferent"),
                1, Question.Category.ANGLAIS, Question.Difficulty.MEDIUM,
                "Benevolent = bienveillant, gentil.");

        save("Which tense is: 'I have been studying for 3 hours'?",
                List.of("Present Perfect","Present Continuous","Present Perfect Continuous","Past Perfect"),
                2, Question.Category.ANGLAIS, Question.Difficulty.MEDIUM,
                "Present Perfect Continuous = have/has been + verb-ing.");

        save("What is the synonym of 'ephemeral'?",
                List.of("Eternal","Fleeting","Solid","Frequent"),
                1, Question.Category.ANGLAIS, Question.Difficulty.DIFFICILE,
                "Ephemeral = de courte durée, comme 'fleeting'.");

        // MATHEMATIQUES
        save("Quel est le résultat de √144 ?",
                List.of("11","12","13","14"),
                1, Question.Category.MATHEMATIQUES, Question.Difficulty.FACILE,
                "√144 = 12 car 12 × 12 = 144.");

        save("Si 2x + 3 = 11, quelle est la valeur de x ?",
                List.of("3","4","5","6"),
                1, Question.Category.MATHEMATIQUES, Question.Difficulty.FACILE,
                "2x = 8, donc x = 4.");

        save("Quelle est la dérivée de f(x) = x³ - 2x² + 5 ?",
                List.of("3x² - 4x","3x² - 2x","x² - 4x","3x² + 4"),
                0, Question.Category.MATHEMATIQUES, Question.Difficulty.MEDIUM,
                "f'(x) = 3x² - 4x. Règle : d/dx(xⁿ) = n·xⁿ⁻¹.");

        save("Combien vaut sin(90°) ?",
                List.of("0","0.5","1","√2/2"),
                2, Question.Category.MATHEMATIQUES, Question.Difficulty.FACILE,
                "sin(90°) = 1, valeur fondamentale.");

        save("Quelle est la limite de (sin x)/x quand x → 0 ?",
                List.of("0","∞","1","Indéterminée"),
                2, Question.Category.MATHEMATIQUES, Question.Difficulty.DIFFICILE,
                "Limite fondamentale : lim(x→0) sin(x)/x = 1.");

        // CHIMIE
        save("Quel est le symbole chimique du fer ?",
                List.of("Fe","Fr","F","Ir"),
                0, Question.Category.CHIMIE, Question.Difficulty.FACILE,
                "Fe vient du latin 'Ferrum'.");

        save("Quelle est la formule de l'eau ?",
                List.of("HO","H2O","H2O2","OH"),
                1, Question.Category.CHIMIE, Question.Difficulty.FACILE,
                "L'eau = 2 atomes H + 1 atome O.");

        save("Quel est le pH d'une solution neutre à 25°C ?",
                List.of("0","5","7","14"),
                2, Question.Category.CHIMIE, Question.Difficulty.FACILE,
                "pH neutre = 7 à 25°C.");

        save("Quel est le numéro atomique du carbone ?",
                List.of("4","6","8","12"),
                1, Question.Category.CHIMIE, Question.Difficulty.FACILE,
                "Le carbone (C) a 6 protons.");

        save("Quelle est la configuration électronique du sodium (Z=11) ?",
                List.of("1s² 2s² 2p⁶ 3s²","1s² 2s² 2p⁵ 3s²","1s² 2s² 2p⁶ 3s¹","1s² 2s² 2p⁴ 3s²"),
                2, Question.Category.CHIMIE, Question.Difficulty.DIFFICILE,
                "Na (Z=11) : 1s² 2s² 2p⁶ 3s¹. 1 électron de valence.");

        // INFORMATIQUE
        save("Que signifie 'HTML' ?",
                List.of("Hyper Text Markup Language","High Tech Modern Language","Hyperlink Text Management Language","Home Tool Markup Language"),
                0, Question.Category.INFORMATIQUE, Question.Difficulty.FACILE,
                "HTML = HyperText Markup Language.");

        save("Quelle est la complexité d'une recherche binaire ?",
                List.of("O(n)","O(n²)","O(log n)","O(1)"),
                2, Question.Category.INFORMATIQUE, Question.Difficulty.MEDIUM,
                "La recherche binaire divise par 2 à chaque étape → O(log n).");

        save("Qu'est-ce que la récursivité ?",
                List.of("Une boucle infinie","Une fonction qui s'appelle elle-même","Un tableau multidimensionnel","Un algorithme de tri"),
                1, Question.Category.INFORMATIQUE, Question.Difficulty.FACILE,
                "Récursivité = fonction qui s'appelle elle-même avec un cas de base.");

        save("Quel protocole assure la transmission sécurisée sur le web ?",
                List.of("HTTP","FTP","HTTPS","SMTP"),
                2, Question.Category.INFORMATIQUE, Question.Difficulty.FACILE,
                "HTTPS utilise SSL/TLS pour chiffrer les communications.");

        save("Quel est le résultat de 5 XOR 3 ?",
                List.of("6","7","8","2"),
                0, Question.Category.INFORMATIQUE, Question.Difficulty.MEDIUM,
                "5=101, 3=011. XOR: 101 XOR 011 = 110 = 6.");

        System.out.println("✅ Base initialisée avec " + questionRepository.count() + " questions !");
    }

    private void save(String text, List<String> options, int correctIndex,
                      Question.Category category, Question.Difficulty difficulty, String explanation) {
        Question q = new Question();
        q.setQuestionText(text);
        q.setOptions(options);
        q.setCorrectAnswerIndex(correctIndex);
        q.setCategory(category);
        q.setDifficulty(difficulty);
        q.setExplanation(explanation);
        questionRepository.save(q);
    }
}