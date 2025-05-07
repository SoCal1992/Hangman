import java.util.ArrayList;
import java.util.Scanner;
import java.nio.file.Paths;
import java.util.Random;

public class UserInterface {
    private Person person;
    private ArrayList<String> hangmanWords;
    private ArrayList<String>hiddenWord;
    private Scanner keyboard;
    private int counter;
    private Random random;
    private String userInput;
    private String completeWord;
    private boolean winner;


    public UserInterface() {
        this.person = new Person();
        this.hangmanWords = new ArrayList<>();
        this.hiddenWord = new ArrayList<>();
        this.keyboard = new Scanner(System.in);
        this.counter = 0;
        this.random = new Random();
        this.userInput = userInput;
        this.completeWord = completeWord;
        this.winner = false;
    }

    public void start() {
        getFile();

        String word = this.hangmanWords.get(random.nextInt(this.hangmanWords.size()));
        String dashWord = word.replaceAll("[a-zA-Z]", "_ ");
        int turns = 6;

        //Starts the game
        while (!this.winner) {
            displayHangman();

            System.out.println("Turns left: " + (turns - this.counter));
            System.out.println("\nGuess a letter or word: ");
            convertWord(word);
            System.out.println();

            this.userInput = keyboard.nextLine();
            checker(this.userInput, word);

            if (this.counter == 6) {
                displayHangman();
                System.out.println("Sorry, you lose! The word was: " + word);
                break;
            }
        }
    }

    public void getFile() {
        try (Scanner scanner = new Scanner(Paths.get("HangmanList.txt"))) {
            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                hangmanWords.add(row);
            }
        } catch (Exception e) {
            System.out.print("Sorry, an error occurred with the word file: " + e.getLocalizedMessage());
        }
    }

    public void convertWord(String word) {
        for (int i = 0; i < word.length(); i++) {
            this.hiddenWord.add("_ ");
            System.out.print(this.hiddenWord.get(i));
        }
    }

    public void checker(String userInput, String word) {

        int localCounter = 0;

        if (userInput.length() > 1 && userInput.equals(word)) {
            this.winner = true;
            System.out.println("You win!");

        } else if (userInput.length() == 1) {
            for (int i = 0; i < userInput.length(); i++) {
                for (int j = 0; j < word.length(); j++) {
                    if (userInput.charAt(i) == word.charAt(j)) {
                        this.hiddenWord.set(j, userInput + " ");
                        localCounter++;
                        combineArray();
                    }
                }
                if (!(localCounter > 0)) {
                    this.counter++;
                } else if (this.completeWord.equals(word)) {
                    this.winner = true;
                    System.out.println("You Win!");
                }
            }
        } else { // May need to place this portion in the start() method
            this.counter++;
            System.out.println("Sorry, try again!");
        }
    }
    public void displayHangman () {

        switch(this.counter) {
            case 0:
                person.startingPoint();
                break;
            case 1:
                person.firstFail();
                break;
            case 2:
                person.secondFail();
                break;
            case 3:
                person.thirdFail();
                break;
            case 4:
                person.fourthFail();
                break;
            case 5:
                person.fifthFail();
                break;
            case 6:
                person.sixthFail();
                break;
        }
    }
    public void combineArray () {
        String joinedElements = String.join("", hiddenWord);
        this.completeWord = joinedElements.replaceAll("[()_\\s]", "");
    }
}
