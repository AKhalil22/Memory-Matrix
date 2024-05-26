import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class testFinal extends JFrame {
    private JPanel panelFinal;
    public ArrayList<ButtonData> ButtonDataStore = new ArrayList<>();
    public int matrixSize;
    ArrayList<JButton> trueButtonsList = new ArrayList<>();
    int selectedCount = 0;
    int tries = 0;

    public testFinal(int size) {
        this.matrixSize = size;

        // Initialize the panel and set layout
        panelFinal = new JPanel(new GridLayout(matrixSize, matrixSize, 2, 2));


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(matrixSize*100, matrixSize*100);
        this.setTitle("Memory Matrix");
        this.setContentPane(panelFinal);
        this.setVisible(true);

        createButtons();
        setTrueButtons();
        showTrueButtons();
    }

    static class ButtonData {
        private JButton button;
        private boolean state;
        ButtonData(JButton button) {
            this.button = button;
            this.state = false; // default state
        }
        public JButton getButton() {
            return button;
        }
        public boolean getState() {
            return state;
        }
        public void setState(boolean state) {
            this.state = state;
        }
    }

    public void createButtons() {
        for (int i = 0; i < (matrixSize*matrixSize); i++) {
            String buttonName = "Button" + i;
            JButton button = new JButton(buttonName); // Creates a new button of name Button + int i
            button.addActionListener(e -> this.ButtonClicked(button));
            button.setText("");
            ButtonDataStore.add(new ButtonData(button)); // Add JButton to object arrayList that keeps track of true / false state
            panelFinal.add(button); // Add JButton to JFrame
        }
        setVisible(true); // Sets JFrame to visible
    }

    public void ButtonClicked(JButton button) {
        ButtonData buttonData = findButtonData(button);
        if (buttonData != null && buttonData.getState()) {
            button.setBackground(Color.green);
            button.setOpaque(true);
            trueButtonsList.add(button);
            selectedCount++;
            button.setEnabled(false);
        } else {
            button.setBackground(Color.red);
            button.setOpaque(true);
            tries += 1;
            button.setEnabled(false);
        }
        if (selectedCount == matrixSize) {
            JOptionPane.showMessageDialog(null, "Winner");
            playAgain(true);
        } else if (tries == 2){
            JOptionPane.showMessageDialog(null, "Loser");
            playAgain(false);
        }
    }

    private ButtonData findButtonData(JButton button) {
        for (ButtonData data : ButtonDataStore) {
            if (data.getButton() == button) {
                return data;
            }
        }
        return null;
    }

    public void setTrueButtons(){
        int count = 0;
        Random random = new Random();
        while(count < matrixSize) {
            int randomIndex = random.nextInt(matrixSize*matrixSize);
            ButtonData buttonData = ButtonDataStore.get(randomIndex); // Get the ButtonData at the random index
            if (!buttonData.getState()) { // Check if the button state is false
                buttonData.setState(true); // Set the button state to true
                count++; // Increment the count of true buttons
            }
        }
    }

    public void showTrueButtons(){
        Timer timer = new Timer();
        for (ButtonData buttonData : ButtonDataStore) {
            JButton button = buttonData.getButton();

            if (buttonData.getState()) {
                button.setBackground(Color.green);
            } else {
                button.setBackground(Color.red);
            }
            button.setOpaque(true);
            button.setEnabled(false);
        }

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (ButtonData buttonData : ButtonDataStore) {
                    JButton button = buttonData.getButton();
                    button.setEnabled(true);
                    button.setOpaque(false);
                    button.setBackground(Color.white);
                }
            }
        }, 850);
    }

    public void playAgain(boolean b) {
        Object[] options = {"Yes", "No"};
        int selected = JOptionPane.showOptionDialog(
                panelFinal,
                "Play Again?",
                "Select option",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null,
                options,
                options[0]);

            if (b & selected == JOptionPane.YES_OPTION) {
                this.setVisible(false);
                this.dispose();
                matrixSize++;
                new testFinal(matrixSize); // Increase current button grid by one after each try
            } else if (!b & selected == JOptionPane.YES_OPTION) {
                this.setVisible(false);
                this.dispose();
                new testFinal(2);
            } else if (selected == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
    }
}