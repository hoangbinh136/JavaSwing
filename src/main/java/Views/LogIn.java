package Views;


import Controllers.LogInController;
import Models.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class LogIn {
    private JFrame frame;
    JLabel userLabel, passLabel;
    JTextField userText;
    JPasswordField passText;
    JButton logInButton, resetButton;
    User user = new User();
    LogInController logInController = new LogInController();

    public LogIn() {
        frame = new JFrame("Log In");

        userLabel = new JLabel("Username");
        userLabel.setBounds(50, 50, 150, 20);
        frame.add(userLabel);

        passLabel = new JLabel("Password");
        passLabel.setBounds(50, 100, 150, 20);
        frame.add(passLabel);

        userText = new JTextField();
        userText.setBounds(150, 50, 150, 20);
        frame.add(userText);

        passText = new JPasswordField();
        passText.setBounds(150, 100, 150, 20);
        frame.add(passText);

        ActionListener logInAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pass = String.valueOf(passText.getPassword());
                user.setName(userText.getText());
                user.setPassword(pass);
                boolean check =  logInController.checkAuth(user);
                if (check) {
                    JOptionPane.showMessageDialog(frame,"Success");
                    System.out.println("success");
                }else {
                    JOptionPane.showMessageDialog(frame,"Fail");
                    System.out.println("fail");
                }

            }
        };

        logInButton = new JButton("Log in");
        logInButton.setBounds(50, 150, 100, 20);
        logInButton.addActionListener(logInAction);
        frame.add(logInButton);

        ActionListener resetAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userText.setText("");
                passText.setText("");
            }
        };

        resetButton = new JButton("Reset");
        resetButton.setBounds(150, 150, 100, 20);
        resetButton.addActionListener(resetAction);
        frame.add(resetButton);

        frame.setLayout(null);
        frame.setSize(500, 400);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LogIn();
            }
        });
    }
}
