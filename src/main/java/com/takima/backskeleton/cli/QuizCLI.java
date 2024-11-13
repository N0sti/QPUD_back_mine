package com.takima.backskeleton.cli;

import com.takima.backskeleton.models.*;
import com.takima.backskeleton.repository.AnswerRepository;
import com.takima.backskeleton.repository.QuestionRepository;
import com.takima.backskeleton.repository.RoomRepository;
import com.takima.backskeleton.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@ComponentScan(basePackages = "com.takima.backskeleton")
@SpringBootApplication
public class QuizCLI implements CommandLineRunner {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnswerRepository answerRepository; // Injecter le repository des réponses

    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        SpringApplication.run(QuizCLI.class, args);
    }

    @Override
    public void run(String... args) {
        start();
    }

    public void start() {
        System.out.println("Bienvenue sur mon quizz!");
        boolean running = true;
        while (running) {
            System.out.println("Que voulez-vous faire ?");
            System.out.println("1. Créer une room");
            System.out.println("2. Créer des questions");
            System.out.println("3. Lancer un quizz");
            System.out.println("4. Quitter");
            System.out.print("Choisissez une option : ");
            int choice = getIntInput(1, 4);  // Validates choice between 1 and 4

            switch (choice) {
                case 1 -> createRoom();
                case 2 -> createQuestions();
                case 3 -> startQuiz();
                case 4 -> running = false;
                default -> System.out.println("Option invalide.");
            }
        }
        System.out.println("Merci d'avoir utilisé le quizz!");
    }

    private void createRoom() {
        System.out.print("Entrez le nom de la room : ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Le nom de la room ne peut pas être vide.");
            return;
        }

        System.out.print("Entrez le nombre de questions souhaitées : ");
        int numberOfQuestions = getIntInput(1, Integer.MAX_VALUE);

        // Sélection du thème pour la room
        String[] themes = {"Mathématiques", "Histoire", "Science", "Géographie", "Informatique", "Littérature", "Autre"};
        System.out.println("Choisissez un thème parmi les options suivantes :");
        for (int i = 0; i < themes.length; i++) {
            System.out.println((i + 1) + ". " + themes[i]);
        }
        System.out.print("Entrez le numéro du thème choisi : ");
        int themeChoice = getIntInput(1, themes.length);  // Ensure valid theme choice
        String selectedTheme = themes[themeChoice - 1];

        // Récupérer toutes les questions qui correspondent au thème sélectionné
        List<Question> allQuestions = questionRepository.findByTheme(selectedTheme);

        // Vérifier s'il y a assez de questions sur le thème souhaité
        if (allQuestions.size() < numberOfQuestions) {
            System.out.println("Il n'y a pas assez de questions sur le thème souhaité pour créer une room.");
            return;
        }

        // Création de la room en base de données
        Room room = new Room(name);
        room = roomRepository.save(room);

        System.out.println("Room créée et sauvegardée en base : " + room);

        // Sélectionner des questions aléatoires
        List<Question> selectedQuestions = getRandomQuestions(allQuestions, numberOfQuestions);

        // Associer les questions à la room
        for (Question question : selectedQuestions) {
            room.addQuestion(question);
            questionRepository.save(question);
        }

        roomRepository.save(room);
        System.out.println("Les questions ont été associées à la room.");
    }


    private List<Question> getRandomQuestions(List<Question> allQuestions, int numberOfQuestions) {
        List<Question> randomQuestions = new ArrayList<>(allQuestions);
        java.util.Collections.shuffle(randomQuestions);
        return randomQuestions.subList(0, Math.min(numberOfQuestions, randomQuestions.size()));
    }

    private void createQuestions() {
        String[] themes = {"Mathématiques", "Histoire", "Science", "Géographie", "Informatique", "Littérature", "Autre"};

        while (true) {
            System.out.print("Entrez le corps de la question (ou 'stop' pour arrêter) : ");
            String body = scanner.nextLine().trim();

            if (body.equalsIgnoreCase("stop")) {
                System.out.println("Arrêt de la saisie des questions.");
                break;
            }

            if (body.isEmpty()) {
                System.out.println("Le corps de la question ne peut pas être vide.");
                continue;
            }

            // Sélection du thème et du type de question
            System.out.println("Choisissez un thème parmi les options suivantes :");
            for (int i = 0; i < themes.length; i++) {
                System.out.println((i + 1) + ". " + themes[i]);
            }
            System.out.print("Entrez le numéro du thème choisi : ");
            int themeChoice = getIntInput(1, themes.length);
            Theme selectedTheme = new Theme(themes[themeChoice - 1]);

            System.out.print("Entrez le type de question (qcm ou vrai/faux) : ");
            String typeChoice = scanner.nextLine().trim().toLowerCase();

            if (!typeChoice.equals("qcm") && !typeChoice.equals("vrai/faux")) {
                System.out.println("Type de question invalide. Veuillez entrer 'qcm' ou 'vrai/faux'.");
                continue;
            }

            QuestionType selectedType = new QuestionType(typeChoice);

            // Gestion des réponses
            List<Answer> answers = new ArrayList<>();

            if ("qcm".equals(typeChoice)) {
                System.out.print("Entrez le nombre de réponses possibles : ");
                int nbReponses = getIntInput(1, Integer.MAX_VALUE);

                for (int i = 0; i < nbReponses; i++) {
                    System.out.print("Entrez la réponse " + (i + 1) + " : ");
                    String answerText = scanner.nextLine().trim();

                    if (answerText.isEmpty()) {
                        System.out.println("Le texte de la réponse ne peut pas être vide.");
                        i--; // Décrémente i pour réessayer cette itération
                        continue;
                    }

                    System.out.print("Cette réponse est-elle correcte ? (true/false) : ");
                    boolean isCorrect = getBooleanInput();

                    // Créer une nouvelle réponse et l'ajouter à la liste des réponses
                    Answer answer = new Answer(null, isCorrect, answerText);
                    answers.add(answer);
                    System.out.println("Réponse créée : " + answer);
                }

            } else if ("vrai/faux".equals(typeChoice)) {
                Answer trueAnswer = new Answer(null, true, "Vrai"); // Réponse "Vrai"
                Answer falseAnswer = new Answer(null, false, "Faux"); // Réponse "Faux"

                answers.add(trueAnswer);
                answers.add(falseAnswer);
                System.out.println("Réponses créées : " + answers);

                System.out.print("La réponse attendue est-elle true ou false ? : ");
                boolean isCorrect = getBooleanInput();
            }

            // Créer la question
            Question question = new Question(body, selectedTheme, selectedType, answers);
            System.out.println("Question créée : " + question);

            // Sauvegarder la question
            question = questionRepository.save(question);
            System.out.println("Réponses avant sauvegarde : " + answers);

            // Sauvegarder les réponses associées à la question
            for (Answer answer : answers) {
                answer.setQuestion(question); // Associer chaque réponse à la question
                System.out.println("Réponse avant sauvegarde : " + answer);
                answerRepository.save(answer); // Sauvegarder la réponse
                System.out.println("Réponse sauvegardée : " + answer);
            }

            System.out.println("Question et réponses ajoutées et sauvegardées en base : " + question);
        }
    }

    private void startQuiz() {
        List<Room> rooms = roomRepository.findAll();
        if (rooms.isEmpty()) {
            System.out.println("Aucune room disponible. Veuillez en créer une.");
            return;
        }

        System.out.println("Sélectionnez une room pour commencer le quizz :");
        for (int i = 0; i < rooms.size(); i++) {
            System.out.println((i + 1) + ". " + rooms.get(i).getName());
        }
        int roomChoice = getIntInput(1, rooms.size()) - 1;  // Ensure valid room choice
        Room room = rooms.get(roomChoice);

        System.out.println("Combien de joueurs ?");
        int playerCount = getIntInput(1, Integer.MAX_VALUE);

        List<String> players = new ArrayList<>();
        for (int i = 0; i < playerCount; i++) {
            System.out.print("Entrez le nom du joueur " + (i + 1) + " : ");
            String playerName = scanner.nextLine().trim();
            if (playerName.isEmpty()) {
                System.out.println("Le nom du joueur ne peut pas être vide.");
                i--; // Décrémente i pour réessayer cette itération
                continue;
            }
            players.add(playerName);
        }

        List<Integer> scores = new ArrayList<>(playerCount);
        for (int i = 0; i < playerCount; i++) scores.add(0);

        for (int qIndex = 0; qIndex < room.getQuestions().size(); qIndex++) {
            Question question = room.getQuestions().get(qIndex);
            System.out.println("Question " + (qIndex + 1) + ": " + question.getBody());
            System.out.println("Thème: " + question.getTheme().getName() + " | Type: " + question.getQuestionType().getType());

            // Afficher les réponses
            if ("qcm".equals(question.getQuestionType().getType())) {
                System.out.println("Choix possibles :");
                List<Answer> answers = question.getAnswers();
                for (int i = 0; i < answers.size(); i++) {
                    System.out.println((i + 1) + ". " + answers.get(i).getBody());
                }
            }

            for (int pIndex = 0; pIndex < playerCount; pIndex++) {
                String answer = "";
                if ("qcm".equals(question.getQuestionType().getType())) {
                    while (true) {
                        System.out.print("Réponse du joueur " + players.get(pIndex) + " : ");
                        answer = scanner.nextLine().trim();
                        boolean validAnswer = false;
                        for (Answer a : question.getAnswers()) {
                            if (a.getBody().equalsIgnoreCase(answer)) {
                                validAnswer = true;
                                break;
                            }
                        }
                        if (validAnswer) {
                            break;
                        } else {
                            System.out.println("Réponse invalide. Veuillez entrer une des réponses proposées.");
                        }
                    }
                } else if ("vrai/faux".equals(question.getQuestionType().getType())) {
                    while (true) {
                        System.out.print("Réponse du joueur " + players.get(pIndex) + " : ");
                        answer = scanner.nextLine().trim().toLowerCase();
                        if (answer.equals("vrai") || answer.equals("faux")) {
                            break;
                        } else {
                            System.out.println("Réponse invalide. Veuillez entrer 'vrai' ou 'faux'.");
                        }
                    }
                }

                if (question.isCorrect(answer)) {
                    scores.set(pIndex, scores.get(pIndex) + 1);
                    System.out.println("Bonne réponse !");
                } else {
                    System.out.println("Mauvaise réponse.");
                }
            }
        }

        System.out.println("Scores finaux :");
        for (int i = 0; i < playerCount; i++) {
            System.out.println(players.get(i) + ": " + scores.get(i) + " points");
        }
    }


    // Helper method to safely get a valid integer input within a specified range
    private int getIntInput(int min, int max) {
        int choice = -1;
        while (choice < min || choice > max) {
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice < min || choice > max) {
                    System.out.println("Veuillez entrer un nombre entre " + min + " et " + max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide. Veuillez entrer un nombre.");
            }
        }
        return choice;
    }

    // Helper method to safely get a valid boolean input
    private boolean getBooleanInput() {
        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("true") || input.equals("false")) {
                return Boolean.parseBoolean(input);
            } else {
                System.out.println("Entrée invalide. Veuillez entrer 'true' ou 'false'.");
            }
        }
    }
}
