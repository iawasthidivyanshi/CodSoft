import java.util.Arrays;
import java.util.*;

class Question {
    String questionText;
    List<String> options;
    int correctOptionIndex;

    public Question(String questionText, List<String> options, int correctOptionIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }
}

class QuizTimer {
    Timer timer;
    int secondsRemaining;

    public QuizTimer(int seconds) {
        timer = new Timer();
        secondsRemaining = seconds;
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                if (secondsRemaining > 0) {
                    System.out.println("Time remaining: " + secondsRemaining + " seconds");
                    secondsRemaining--;
                } else {
                    System.out.println("Time's up!");
                    timer.cancel();
                }
            }
        }, 0, 1000);
    }
}

public class QuizApplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Question> quizQuestions = new ArrayList<>();
        quizQuestions.add(new Question("What is the Capital of India?",
                new ArrayList<>(Arrays.asList("Chennai", "Mumbai", "Delhi", "Banglore")), 2));
        quizQuestions.add(new Question("The Chandrayaan 3 mission's rover is known as",
                new ArrayList<>(Arrays.asList("Vikram, Bheem, Pragyan, Dhruv")), 2));
        quizQuestions.add(new Question("What is the largest mammal?",
                new ArrayList<>(Arrays.asList("Elephant", "Whale", "Giraffe", "Rhinoceros")), 1));
        quizQuestions.add(new Question("How Old is the Solar System",
                new ArrayList<>(Arrays.asList("5000 years", "5 million years", "500 million years", "5 billion years")), 3));
        quizQuestions.add(new Question("Which planet is known as the 'Red Planet?",
                new ArrayList<>(Arrays.asList("Earth", "Mars", "Venus", "Jupiter")), 1));

        int userScore = 0;
        QuizTimer quizTimer = new QuizTimer(15); // 15 seconds for each question

        for (int i = 0; i < quizQuestions.size(); i++) {
            Question question = quizQuestions.get(i);

            System.out.println("Question " + (i + 1) + ": " + question.questionText);
            for (int j = 0; j < question.options.size(); j++) {
                System.out.println((j + 1) + ". " + question.options.get(j));
            }

            int selectedOption = sc.nextInt();
            if (selectedOption == question.correctOptionIndex + 1) {
                System.out.println("Correct!");
                userScore++;
            } else {
                System.out.println("Incorrect!");
            }

            quizTimer.secondsRemaining = 15; // Reseting timer for next question
        }

        quizTimer.timer.cancel();

        System.out.println("Quiz finished!");
        System.out.println("Your score: " + userScore + " out of " + quizQuestions.size());

        // Display a summary of correct/incorrect answers
        for (int i = 0; i < quizQuestions.size(); i++) {
            Question question = quizQuestions.get(i);
            String status = (userScore > i) ? "Correct" : "Incorrect";
            System.out.println("Q" + (i + 1) + ": " + status);
        }
    }
}