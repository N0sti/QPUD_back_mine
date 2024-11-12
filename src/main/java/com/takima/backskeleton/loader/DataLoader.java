
import com.takima.backskeleton.models.*;
import com.takima.backskeleton.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final ThemeRepository themeRepository;
    private final QuestionTypeRepository questionTypeRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final RoomQuestionRepository roomQuestionRepository;
    private final RoomSettingsRepository roomSettingsRepository;
    private final UserInRoomRepository userInRoomRepository;
    private final RoomSettingsThemesRepository roomSettingsThemesRepository;
    private final RoomSettingsTypesRepository roomSettingsTypesRepository;

    // Constructor injection
    public DataLoader(RoomRepository roomRepository, UserRepository userRepository, ThemeRepository themeRepository,
                      QuestionTypeRepository questionTypeRepository, QuestionRepository questionRepository,
                      AnswerRepository answerRepository, RoomQuestionRepository roomQuestionRepository,
                      RoomSettingsRepository roomSettingsRepository, UserInRoomRepository userInRoomRepository,
                      RoomSettingsThemesRepository roomSettingsThemesRepository, RoomSettingsTypesRepository roomSettingsTypesRepository) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.themeRepository = themeRepository;
        this.questionTypeRepository = questionTypeRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.roomQuestionRepository = roomQuestionRepository;
        this.roomSettingsRepository = roomSettingsRepository;
        this.userInRoomRepository = userInRoomRepository;
        this.roomSettingsThemesRepository = roomSettingsThemesRepository;
        this.roomSettingsTypesRepository = roomSettingsTypesRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        loadRooms();
        loadUsers();
        loadThemes();
        loadQuestionTypes();
        loadQuestionsAndAnswers();
        loadRoomSettings();
        loadUserInRooms();
        loadRoomQuestions();
        loadRoomSettingsThemes();
        loadRoomSettingsTypes();
    }

    private void loadRooms() {
        roomRepository.saveAll(Arrays.asList(
                new Room("General Quiz Room", 15),
                new Room("Science Room", 10),
                new Room("History Room", 20)
        ));
    }

    private void loadUsers() {
        userRepository.saveAll(Arrays.asList(
                new User("alice", false),
                new User("bob", true),
                new User("charlie", false),
                new User("dave", false),
                new User("eve", true)
        ));
    }

    private void loadThemes() {
        themeRepository.saveAll(Arrays.asList(
                new Theme("General Knowledge"),
                new Theme("Science"),
                new Theme("History")
        ));
    }

    private void loadQuestionTypes() {
        questionTypeRepository.saveAll(Arrays.asList(
                new QuestionType("Multiple Choice"),
                new QuestionType("True or False")
        ));
    }

    private void loadQuestionsAndAnswers() {
        Question q1 = questionRepository.save(new Question(1, 1, "What is the capital of France?", themeRepository, questionTypeRepository));
        Question q2 = questionRepository.save(new Question(2, 2, "Water boils at 100 degrees Celsius.", themeRepository, questionTypeRepository));
        Question q3 = questionRepository.save(new Question(3, 1, "Who was the first president of the United States?", themeRepository, questionTypeRepository));

        answerRepository.saveAll(Arrays.asList(
                new Answer(q1, true, "Paris"),
                new Answer(q1, false, "London"),
                new Answer(q1, false, "Berlin"),
                new Answer(q1, false, "Madrid"),
                new Answer(q2, true, "True"),
                new Answer(q2, false, "False"),
                new Answer(q3, true, "George Washington"),
                new Answer(q3, false, "Abraham Lincoln"),
                new Answer(q3, false, "Thomas Jefferson"),
                new Answer(q3, false, "John Adams")
        ));
    }


    private void loadRoomSettings() {
        Room room1 = roomRepository.findById(1).orElseThrow();  // Assurez-vous d'utiliser Long pour l'ID
        Room room2 = roomRepository.findById(2).orElseThrow();
        Room room3 = roomRepository.findById(3).orElseThrow();

        roomSettingsRepository.saveAll(Arrays.asList(
                new RoomSettings(room1, 5, 300),   // Passer l'objet Room directement
                new RoomSettings(room2, 10, 600),
                new RoomSettings(room3, 15, 900)
        ));
    }



    private void loadUserInRooms() {
        userInRoomRepository.saveAll(Arrays.asList(
                new UserInRoom(1L, 1L), // Alice in General Quiz Room
                new UserInRoom(2L, 2L), // Bob in Science Room
                new UserInRoom(3L, 3L)  // Charlie in History Room
        ));
    }


    private void loadRoomQuestions() {
        roomQuestionRepository.saveAll(Arrays.asList(
                new RoomQuestion(1L, 1L), // General Quiz Room gets question about France
                new RoomQuestion(2L, 2L), // Science Room gets question about boiling water
                new RoomQuestion(3L, 3L)  // History Room gets question about George Washington
        ));
    }


    private void loadRoomSettingsThemes() {
        roomSettingsThemesRepository.saveAll(Arrays.asList(
                new RoomSettingsThemes(1L, 1L), // General Quiz Room uses General Knowledge theme
                new RoomSettingsThemes(2L, 2L), // Science Room uses Science theme
                new RoomSettingsThemes(3L, 3L)  // History Room uses History theme
        ));
    }

    private void loadRoomSettingsTypes() {
        roomSettingsTypesRepository.saveAll(Arrays.asList(
                new RoomSettingsTypes(1L, 1L), // General Quiz Room uses Multiple Choice
                new RoomSettingsTypes(2L, 2L), // Science Room uses True or False
                new RoomSettingsTypes(3L, 1L)  // History Room uses Multiple Choice
        ));
    }
}
