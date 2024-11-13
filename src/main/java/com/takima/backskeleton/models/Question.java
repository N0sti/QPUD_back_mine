package com.takima.backskeleton.models;

import com.takima.backskeleton.DTO.AnswerDto;
import com.takima.backskeleton.DTO.QuestionDto;
import com.takima.backskeleton.repository.ThemeRepository;
import com.takima.backskeleton.repository.QuestionTypeRepository;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "questions")
@Getter
@Setter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Body cannot be empty")
    @Column(name = "body", nullable = false, unique = true)
    private String body;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "theme_id", nullable = false)
    private Theme theme;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "type_id", nullable = false)
    private QuestionType type;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers;

    // Associe une salle à la question
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public Question() {
    }

    // Constructeur avec paramètres pour initialiser la question avec des objets Answer
    public Question(String body, Theme theme, QuestionType type, List<Answer> answers) {
        this.body = body;
        this.theme = theme;
        this.type = type;
        this.answers = answers;
        for (Answer answer : answers) {
            answer.setQuestion(this); // Associer chaque réponse à la question
        }
    }

    // Constructeur avec paramètres pour initialiser une question avec des réponses en texte
    public Question(int themeId, int typeId, String body, ThemeRepository themeRepository, QuestionTypeRepository questionTypeRepository) {
        this.body = body;
        this.theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid theme ID"));
        this.type = questionTypeRepository.findById(typeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid type ID"));
    }

    // Retourne le DTO de la question
    public QuestionDto toQuestionDto() {
        List<AnswerDto> answerDtos = answers.stream()
                .map(answer -> new AnswerDto(answer.getBody(), answer.isCorrect()))
                .collect(Collectors.toList());
        return new QuestionDto(id, body, theme != null ? theme.getId() : null, type != null ? type.getId() : null, answerDtos);
    }

    // Vérifie si une réponse donnée est correcte
    public boolean isCorrect(String answer) {
        return answers.stream()
                .anyMatch(a -> a.isCorrect() && a.getBody().equalsIgnoreCase(answer));
    }

    // Retourne une chaîne descriptive de l'objet Question
    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", theme=" + (theme != null ? theme.getName() : "null") +
                ", type=" + (type != null ? type.getType() : "null") +
                ", answers=" + answers +
                ", room=" + (room != null ? room.getName() : "null") +
                '}';
    }

    // Définit le type de la question si l'objet questionType est valide
    public void setQuestionType(QuestionType questionType) {
        if (questionType != null) {
            this.type = questionType;
        } else {
            throw new IllegalArgumentException("QuestionType cannot be null");
        }
    }

    public QuestionType getQuestionType() {
        return this.type;
    }

    // Builder pattern pour créer un objet Question
    public static class Builder {
        private String body;
        private Theme theme;
        private QuestionType type;
        private List<Answer> answers;
        private Room room;

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder theme(Theme theme) {
            this.theme = theme;
            return this;
        }

        public Builder type(QuestionType type) {
            this.type = type;
            return this;
        }

        public Builder answers(List<Answer> answers) {
            this.answers = answers;
            return this;
        }

        public Builder room(Room room) {
            this.room = room;
            return this;
        }

        public Question build() {
            if (body == null || body.isEmpty()) {
                throw new IllegalArgumentException("Body cannot be empty");
            }
            Question question = new Question();
            question.setBody(this.body);
            question.setTheme(this.theme);
            question.setType(this.type);
            question.setAnswers(this.answers);
            question.setRoom(this.room);
            return question;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(id, question.id) &&
                Objects.equals(body, question.body) &&
                Objects.equals(theme, question.theme) &&
                Objects.equals(type, question.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, body, theme, type);
    }
}
