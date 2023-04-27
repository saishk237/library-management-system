package com.pbl.librarian;

import com.pbl.models.Librarian;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import static com.pbl.Database.DBConnection;

public class IssueBook extends JPanel implements ActionListener {
    JTextField searchById, studentId;
    JLabel bookName, foundBookName, bookAuthor, foundBookAuthor;
    JButton issueBook, searchBook;
    Librarian librarian;
    IssueBook(Librarian librarian){
        this.librarian = librarian;
        JLabel label = new JLabel("Issue New Book");
        label.setSize(900, 100);
        searchBook = new JButton("Search Book");
        studentId = new JTextField("Enter Student Id");
        issueBook = new JButton("Issue Book");
        searchById = new JTextField("Enter Book Id");
        bookName = new JLabel("Book Name:- ");
        foundBookName = new JLabel();
        bookAuthor = new JLabel("Book Author:- ");
        foundBookAuthor = new JLabel();
        this.setBorder(new EmptyBorder(new Insets(50, 0, 50, 20)));
        searchById.setMaximumSize(new Dimension(200, 50));
        studentId.setMaximumSize(new Dimension(200, 50));
        label.setFont(new Font("Times New Roman", Font.BOLD, 20));
        this.setBackground(Color.BLACK);

        searchBook.addActionListener(this);
        issueBook.addActionListener(this);
        searchById.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchById.getText().equals("Enter Book Id")) {
                    searchById.setText("");
                    //searchById.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (searchById.getText().isEmpty()) {
                    //searchText.setForeground(Color.GRAY);
                    searchById.setText("Enter Book Id");
                }
            }
        });
        studentId.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (studentId.getText().equals("Enter Student Id")) {
                    studentId.setText("");
                    //searchById.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (studentId.getText().isEmpty()) {
                    //searchText.setForeground(Color.GRAY);
                    studentId.setText("Enter Student Id");
                }
            }
        });

        bookName.setBackground(Color.BLUE);
        bookName.setHorizontalAlignment(JLabel.CENTER);
        foundBookName.setHorizontalAlignment(JLabel.CENTER);
        bookAuthor.setHorizontalAlignment(JLabel.CENTER);
        foundBookAuthor.setHorizontalAlignment(JLabel.CENTER);
        JPanel bookDetails = new JPanel();
        bookDetails.setBackground(Color.BLACK);
        bookDetails.setLayout(new GridLayout(2,2));

        bookDetails.add(bookName);
        bookDetails.add(foundBookName);
        bookDetails.add(bookAuthor);
        bookDetails.add(foundBookAuthor);
        bookDetails.setMaximumSize(new Dimension(600, 70));

        JLabel bookIcon = new JLabel(new ImageIcon("./images/book_icon_2.png"));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setHorizontalAlignment(JLabel.CENTER);
        this.add(label);
        searchById.setHorizontalAlignment(JTextField.CENTER);
        searchById.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createRigidArea(new Dimension(900, 50)));
        this.add(searchById);
        searchBook.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(searchBook);
        bookDetails.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createRigidArea(new Dimension(900, 50)));
        this.add(bookDetails);
        this.add(Box.createRigidArea(new Dimension(900, 50)));
        studentId.setHorizontalAlignment(JTextField.CENTER);
        this.add(studentId);
        this.add(Box.createRigidArea(new Dimension(900, 50)));
        bookIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(bookIcon);
        this.add(Box.createRigidArea(new Dimension(900, 50)));
        issueBook.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(issueBook);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == searchBook){
            if(searchById.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Entered data is empty", "Invalid Data", JOptionPane.WARNING_MESSAGE);
            }

            try {
                Statement ps = DBConnection.createStatement();
                String sql = "SELECT * FROM books WHERE id=" + searchById.getText() + "";
                ResultSet rs=ps.executeQuery(sql);
                if(rs.next()){
                    foundBookName.setText(rs.getString(2));
                    foundBookAuthor.setText(rs.getString(3));
                    //foundBookName.
                    sql = "SELECT * FROM books_entries WHERE book_issued=" + searchById.getText() + " AND return_time is null";
                    rs = ps.executeQuery(sql);
                    if(rs.next()){
                        JOptionPane.showMessageDialog(null, "Book already issued by someone else", "Already issued", JOptionPane.WARNING_MESSAGE);
                        issueBook.setEnabled(false);
                        return;
                    }
                    issueBook.setEnabled(true);
                }
                else{
                    JOptionPane.showMessageDialog(null, "No book with entered Book ID", "Invalid Data", JOptionPane.WARNING_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Invalid Data Entered. No such Book Found", "Invalid Data", JOptionPane.WARNING_MESSAGE);
                ex.printStackTrace();
            }
        }

        if(e.getSource() == issueBook){
            String student = studentId.getText();
            String book = searchById.getText();
            if(student.isEmpty()){
                JOptionPane.showMessageDialog(null, "Entered data is empty", "Invalid Data", JOptionPane.WARNING_MESSAGE);
            }

            try {
                Statement ps = DBConnection.createStatement();
                String sql = "INSERT INTO books_entries values(" + student + ","+ librarian.id + "," + book + ",default,default)";
                ps.executeUpdate(sql);
                //System.out.println(rs);
                JOptionPane.showMessageDialog(null, "Book issued successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Invalid Data Entered. No such Student Found", "Invalid Data", JOptionPane.WARNING_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
}
