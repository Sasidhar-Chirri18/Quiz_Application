package javaApplication;

import java.util.*;

public class QuizApp {

    // Simple Question holder
    static class Question {
        private String prompt;
        private String[] options;
        private int correctIndex; 

        public Question(String prompt, String[] options, int correctIndex) {
            this.prompt = prompt;
            this.options = options;
            this.correctIndex = correctIndex;
            if (correctIndex < 0 || correctIndex >= options.length)
                throw new IllegalArgumentException("correctIndex out of range");
        }

        public String getPrompt() { return prompt; }
        public String[] getOptions() { return options; }
        public int getCorrectIndex() { return correctIndex; }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Question> questions = loadQuestions(); // add/modify questions 

        System.out.println("=== Welcome to the Java Console Quiz ===");
        System.out.println("You will be shown " + questions.size() + " questions. Enter the option number for your answer.");
        System.out.println();

        List<Integer> userAnswers = new ArrayList<>();
        int score = 0;
        int qNo = 1;
        for (Question q : questions) {
            System.out.println("Question " + qNo + " of " + questions.size());
            int selected = askQuestion(q, sc);      
            userAnswers.add(selected);
            if (selected == q.getCorrectIndex()) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Wrong. Correct answer: " + q.getOptions()[q.getCorrectIndex()]);
            }
            System.out.println();
            qNo++;
        }

        // Results
        System.out.println("=== Quiz Finished ===");
        System.out.println("Your score: " + score + " / " + questions.size());
        double percent = 100.0 * score / questions.size();
        System.out.printf("Percentage: %.2f%%\n", percent);

        if (score < questions.size()) {
            System.out.println("\nReview of incorrect answers:");
            for (int i = 0; i < questions.size(); i++) {
                if (userAnswers.get(i) != questions.get(i).getCorrectIndex()) {
                    Question q = questions.get(i);
                    System.out.println("\nQ" + (i+1) + ": " + q.getPrompt());
                    System.out.println("Your answer: " + q.getOptions()[userAnswers.get(i)]);
                    System.out.println("Correct : " + q.getOptions()[q.getCorrectIndex()]);
                }
            }
        }

        sc.close();
    }

    // Ask question and validate input
    private static int askQuestion(Question q, Scanner sc) {
        System.out.println(q.getPrompt());
        String[] opts = q.getOptions();
        for (int i = 0; i < opts.length; i++) {
            System.out.println((i+1) + ". " + opts[i]);
        }
        int max = opts.length;
        int choice = -1;
        while (true) {
            System.out.print("Your answer (1-" + max + "): ");
            String line = sc.nextLine().trim();
            try {
                choice = Integer.parseInt(line);
                if (choice >= 1 && choice <= max) {
                    return choice - 1; 
                } else {
                    System.out.println("Please enter a number between 1 and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter the option number (e.g. 1).");
            }
        }
    }

    // Put all questions here
    private static List<Question> loadQuestions() {
        List<Question> list = new ArrayList<>();
        list.add(new Question("What is the output of 2 + 3 * 4?", new String[] {"20", "14", "24", "10"}, 1));
        list.add(new Question("Which keyword is used to inherit a class in Java?", new String[] {"implements", "extends", "inherits", "super"}, 1));
        list.add(new Question("Which data type is used to create a variable that should store text?", new String[] {"String", "int", "char", "boolean"}, 0));
        list.add(new Question("Which company developed the Java programming language?", new String[] {"Microsoft", "Sun Microsystems", "Apple", "Google"}, 1));
        list.add(new Question("Which method is the entry point of a Java program?", new String[] {"start()", "init()", "main()", "run()"}, 2));
        return list;
    }
}
