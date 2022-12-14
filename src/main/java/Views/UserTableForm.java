package Views;

import Controllers.RoomController;
import Models.User;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

import static Controllers.RoomController.getConnection;

public class UserTableForm {
    ArrayList<User> listStudents = new ArrayList<>();
    RoomController roomController = new RoomController();
    User GetUser;

    JFrame frame;
    JTable userTable;
    JButton addButton;
    JButton modifyButton;

    // Add function
    JLabel nameLabel;
    JLabel phoneNumberLabel;
    JTextField nameText;
    JTextField phoneNumberText;
    JButton addToTableButton;

    // Modify function
    JButton updateButton;

    // Delete function
    JButton deleteButton;

    DefaultTableModel tableModel = new DefaultTableModel();

    UserTableForm() throws SQLException {
        // Initiate form
        frame = new JFrame();
        frame.setSize(500, 500);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2 - frame.getSize().width/2, dim.height/2 - frame.getSize().height/2);
        frame.setVisible(true);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setVisible(false);

        // JMenu
        JMenuBar menuBar = new JMenuBar();
        JMenu manageAcc = new JMenu("Quan ly tai khoan");
        JMenu manageStaff = new JMenu("Quan ly nhan vien");
        JMenu stat = new JMenu("Thong ke");

        menuBar.add(manageAcc);
        menuBar.add(manageStaff);
        menuBar.add(stat);

        MenuListener manageStaffListener = new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                panel.setVisible(true);
            }

            @Override
            public void menuDeselected(MenuEvent e) {
                panel.setVisible(false);
            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        };
        manageStaff.addMenuListener(manageStaffListener);

        frame.setJMenuBar(menuBar);


        // Initiate table
        listStudents = roomController.getTeacher(); // Get data
        userTable = new JTable(tableModel);
        tableModel.addColumn("id");
        tableModel.addColumn("name");
        tableModel.addColumn("phone_number");

        for (User user: listStudents) {
            tableModel.addRow( new Object[] {user.getId(), user.getName(), user.getPhone_number()});
        }

        JScrollPane sp = new JScrollPane(userTable);
        sp.setBounds(0, 0, 500, 300);
        panel.add(sp);

        // Add button
        addButton = new JButton("Add");
        addButton.setBounds(25, 350, 150, 50);
        panel.add(addButton);

        ActionListener addActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame2 = new JFrame();
                frame2.add(nameLabel);
                frame2.add(nameText);
                frame2.add(phoneNumberLabel);
                frame2.add(phoneNumberText);
                frame2.add(addToTableButton);


                frame2.setLayout(null);
                frame2.setSize(400, 400);
                frame2.setVisible(true);
            }
        };
        addButton.addActionListener(addActionListener);

        // Modify button
        modifyButton = new JButton("Modify");
        modifyButton.setBounds(175, 350, 150, 50);
        panel.add(modifyButton);

        ActionListener modifyActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame modifyFrame = new JFrame();
                GetUser = listStudents.get(userTable.getSelectedRow());

                modifyFrame.add(nameLabel);
                modifyFrame.add(nameText);
                modifyFrame.add(phoneNumberLabel);
                modifyFrame.add(phoneNumberText);
                modifyFrame.add(updateButton);

                nameText.setText(GetUser.getName());
                phoneNumberText.setText(GetUser.getPhone_number());


                modifyFrame.setLayout(null);
                modifyFrame.setSize(400, 400);
                modifyFrame.setVisible(true);
            }
        };
        modifyButton.addActionListener(modifyActionListener);

        // Delete button
        deleteButton = new JButton("Delete");
        deleteButton.setBounds(325, 350, 150, 50);
        panel.add(deleteButton);

        ActionListener deleteActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    GetUser = listStudents.get(userTable.getSelectedRow());
                    roomController.deleteUser(GetUser);
                    tableModel.setRowCount(0);
                    listStudents = roomController.getTeacher();
                    for (User user: listStudents) {
                        tableModel.addRow( new Object[] {user.getId(), user.getName(), user.getPhone_number()});
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        };
        deleteButton.addActionListener(deleteActionListener);

        frame.add(panel);

        // Add function
        nameLabel = new JLabel("Name");
        nameLabel.setBounds(0, 50, 150, 50);

        nameText = new JTextField();
        nameText.setBounds(150, 50, 150, 50);

        phoneNumberLabel = new JLabel("Phone number");
        phoneNumberLabel.setBounds(0, 100, 150, 50);

        phoneNumberText = new JTextField();
        phoneNumberText.setBounds(150, 100, 150, 50);

        addToTableButton = new JButton("Add");
        addToTableButton.setBounds(100, 150, 100, 50);

        ActionListener addToTableActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String getName = nameText.getText();
                String getPhone = phoneNumberText.getText();
                try {
                    Connection con = getConnection();
                    PreparedStatement preparedStatement = con.prepareStatement("Insert into student(name, phone_number)" + "Values (?, ?)");
                    preparedStatement.setString(1, getName);
                    preparedStatement.setString(2, getPhone);
                    preparedStatement.execute();
                    con.close();

                    tableModel.setRowCount(0);
                    listStudents = roomController.getTeacher();
                    for (User user: listStudents) {
                        tableModel.addRow( new Object[] {user.getId(), user.getName(), user.getPhone_number()});
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        };
        addToTableButton.addActionListener(addToTableActionListener);

        // Modify function
        updateButton = new JButton("Update");
        updateButton.setBounds(100, 150, 100, 50);

        ActionListener updateActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    GetUser.setName(nameText.getText());
                    GetUser.setPhone_number(phoneNumberText.getText());

                    roomController.updateUser(GetUser);

                    tableModel.setRowCount(0);
                    listStudents = roomController.getTeacher();
                    for (User user: listStudents) {
                        tableModel.addRow( new Object[] {user.getId(), user.getName(), user.getPhone_number()});
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        };
        updateButton.addActionListener(updateActionListener);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new UserTableForm();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}