import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int lowerRange = 1;
        int upperRange = 100;
        int maxAttempts = 10;
        int totalAttempts = 0;
        int rounds = 0;

        System.out.println("Welcome to the Number Guessing Game!");

        boolean playAgain = true;
        while (playAgain) {
            rounds++;
            int numberToGuess = random.nextInt(upperRange - lowerRange + 1) + lowerRange;
            int attempts = 0;

            System.out.printf("Round %d: Guess a number between %d and %d.%n", rounds, lowerRange, upperRange);

            while (attempts < maxAttempts) {
                System.out.print("Enter your guess: ");
                int userGuess = scanner.nextInt();
                attempts++;

                if (userGuess < numberToGuess) {
                    System.out.println("Too low!");
                } else if (userGuess > numberToGuess) {
                    System.out.println("Too high!");
                } else {
                    System.out.printf("Congratulations! You guessed the number %d in %d attempts.%n", numberToGuess, attempts);
                    totalAttempts += attempts;
                    break;
                }

                int attemptsLeft = maxAttempts - attempts;
                System.out.printf("You have %d %s left.%n", attemptsLeft, attemptsLeft != 1 ? "attempts" : "attempt");
            }

            if (attempts == maxAttempts) {
                System.out.printf("Sorry, you've used all %d attempts. The number was %d.%n", maxAttempts, numberToGuess);
            }

            System.out.print("Do you want to play another round? (yes/no): ");
            String playAgainInput = scanner.next();
            playAgain = playAgainInput.equalsIgnoreCase("yes");
        }

        System.out.printf("Thanks for playing! You played %d %s and your total score is %d attempts.%n",
                rounds, rounds != 1 ? "rounds" : "round", totalAttempts);

        scanner.close();
    }
}