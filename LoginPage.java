import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class LoginPage extends JFrame {
    private JPasswordField passwordField1;
    private JCheckBox rememberCredentialsCheckBox;
    private JButton signInButton;
    private JPanel panel;
    private JButton hintButton;

    public LoginPage() {
        setUpSignInButtonAction();
        readCredentialsFile();
        hintButtonAction();
    }

    private void hintButtonAction() {
        hintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Hint: Matrix Release Year");
            }
        });
    }

    private void setUpSignInButtonAction() {
        int correctPin = 1999; // Release date Matrix

        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredPinString = new String(passwordField1.getPassword());
                try {
                    // Parse the String to an integer
                    int enteredPin = Integer.parseInt(enteredPinString);

                    if (enteredPin == correctPin) {
                        JOptionPane.showMessageDialog(null, "Correct credentials! Welcome!");
                        goToMatrixPage();
                        if (rememberCredentialsCheckBox.isSelected()) {
                            createCredentialsFile();
                        }
                    }
                } catch (NumberFormatException lol) {
                    JOptionPane.showMessageDialog(null, lol);
                }
            }
        });
    }

    private void setUpCheckBox() {
        rememberCredentialsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(rememberCredentialsCheckBox.isSelected()) {
                    System.out.println("Check box was checked!");

                } else {
                    System.out.println("Check box was unchecked!");
                    deleteCredentialsFile();
                }
            }
        });
    }

    public void deleteCredentialsFile() {
        String homeDirectory = System.getProperty("user.home");
        String dirNane = homeDirectory + "/Documents/SignInCredentials.txt";
        File fileToDelete = new File(dirNane);
        boolean successfullyDeleted = fileToDelete.delete();
        if (successfullyDeleted) {
            System.out.println(dirNane + "\nFile Deleted Successfully!");
        } else {
            System.out.println(dirNane + "\nThe file failed to be deleted! Maybe it doesn't exist.");
        }
    }

    public void goToMatrixPage() {
        // close current window
        this.setVisible(false);
        this.dispose();
        // Create an object of 'DataPage' and show its window
        new testFinal(2);
    }

    public void createCredentialsFile() {
        try {
            String homeDirectory = System.getProperty("user.home");
            String fullDirectory = homeDirectory + "/Documents/SignInCredentials.txt";
            OutputStream os = new FileOutputStream(fullDirectory);
            String enteredPinString = new String (passwordField1.getPassword());
            byte[] b = enteredPinString.getBytes();
            os.write(b);
            os.close();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void readCredentialsFile() {
        try {
            String homeDirectory = System.getProperty("user.home");
            String fullDirectory = homeDirectory + "/Documents/SignInCredentials.txt";
            InputStream is = new FileInputStream(fullDirectory);
            int size = is.available();
            String textFromFile = "";
            for (int i = 0; i < size; i++) {
                char nextByte = (char) is.read();
                String nextCharacter = Character.toString(nextByte);
                textFromFile += nextCharacter;
            }
            String password = textFromFile;
            passwordField1.setText(password);
            rememberCredentialsCheckBox.setSelected(true);
            is.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        LoginPage SignInPage = new LoginPage();
        SignInPage.setContentPane(SignInPage.panel);
        SignInPage.setTitle("Sign In Page");
        SignInPage.setBounds(600, 200, 300, 300);
        SignInPage.setVisible(true);
        SignInPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
