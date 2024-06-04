import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Number_Game extends JFrame {
    private final int MAX_ATTEMPTS = 10;
    private int targetNumber;
    private int attempts;
    private Random random;

    private JLabel instructionLabel;
    private JTextField guessField;
    private JButton guessButton;
    private JLabel feedbackLabel;
    private JLabel attemptsLabel;
    private JButton playAgainButton;

    public Number_Game() {
        setTitle("Number Guessing Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        random = new Random();
        setupUI();
        resetGame();
    }

    private void setupUI() {
        instructionLabel = new JLabel("Guess the number between 1 and 100:");
        guessField = new JTextField(10);
        guessButton = createStyledButton("Guess");
        feedbackLabel = new JLabel("");
        attemptsLabel = new JLabel("");
        playAgainButton = createStyledButton("Play Again");
        playAgainButton.setVisible(false);

        guessButton.addActionListener(new GuessButtonListener());
        playAgainButton.addActionListener(e -> resetGame());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));
        panel.add(instructionLabel);
        panel.add(guessField);
        panel.add(guessButton);
        panel.add(feedbackLabel);
        panel.add(attemptsLabel);
        panel.add(playAgainButton);

        add(panel);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(0, 123, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 153, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 123, 255));
            }
        });

        return button;
    }

    private void resetGame() {
        targetNumber = random.nextInt(100) + 1;
        attempts = 0;
        guessField.setText("");
        feedbackLabel.setText("");
        attemptsLabel.setText("Attempts left: " + MAX_ATTEMPTS);
        playAgainButton.setVisible(false);
        guessButton.setEnabled(true);
    }

    private class GuessButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int guess = Integer.parseInt(guessField.getText());
                attempts++;

                if (guess == targetNumber) {
                    feedbackLabel.setText("Congratulations! You guessed the number!");
                    guessButton.setEnabled(false);
                    playAgainButton.setVisible(true);
                } else if (guess < targetNumber) {
                    feedbackLabel.setText("Your guess is too low.");
                } else {
                    feedbackLabel.setText("Your guess is too high.");
                }

                attemptsLabel.setText("Attempts left: " + (MAX_ATTEMPTS - attempts));

                if (attempts >= MAX_ATTEMPTS && guess != targetNumber) {
                    feedbackLabel.setText("Game over! The number was " + targetNumber);
                    guessButton.setEnabled(false);
                    playAgainButton.setVisible(true);
                }
            } catch (NumberFormatException ex) {
                feedbackLabel.setText("Please enter a valid number.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Number_Game game = new Number_Game();
            game.setVisible(true);
        });
    }
}
