package com.pbl.librarian;

import com.pbl.Main;
import com.pbl.MainPage;
import com.pbl.models.Librarian;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibrarianDashboard extends JFrame implements ActionListener {
    int posX=0,posY=0;
    JButton changePasswordBtn, issuedBooksBtn, issueBooksBtn, returnBookBtn;
    JLabel logo;
    JPanel sideBar, renderer, issueBooks, issuedBooks, returnBooks, changePassword;
    CardLayout cardLayout;
    int currentState;
    JLayeredPane layeredPane;
    JTabbedPane tabbedPane;
    JPanel MainPagePanel;
    Librarian librarian;
    public LibrarianDashboard(Librarian librarian){
        TitleBar titleBar = new TitleBar(this);
        this.librarian = librarian;
        currentState = 1;
        ImageIcon favicon = new ImageIcon("./images/favicon.png");
        logo = new JLabel(new ImageIcon("./images/lib_logo2.png"));
        logo.setText(librarian.name);
        this.setIconImage(favicon.getImage());
        this.setTitle("Library Management System");
        this.setUndecorated(true);
        this.setBackground(new Color(0f, 0f, 0f, 0.0f));
        this.setSize(1000, 700);
        this.setLocationRelativeTo(null);

        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.add(titleBar);

        MainPagePanel = new JPanel();
        MainPagePanel.setSize(1000, 680);
        MainPagePanel.setBackground(new Color(0f, 0f, 0f, 0.0f));

        sideBar = new JPanel();
        renderer = new JPanel();

        sideBar.setBackground(new Color(0f, 0f, 0f, 0.6f));
        sideBar.setBounds(0, 0, 200, 680);
        renderer.setBackground(new Color(0f, 0f, 0f, 0.0f));
        renderer.setBounds(200, 0, 900, 680);

        changePasswordBtn = new JButton("Change Password");
        issueBooksBtn = new JButton("Issue Book");
        issuedBooksBtn = new JButton("Issued Books");
        returnBookBtn = new JButton("Return Books");

        changePasswordBtn.addActionListener(this);
        issuedBooksBtn.addActionListener(this);
        issueBooksBtn.addActionListener(this);
        returnBookBtn.addActionListener(this);

        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.PAGE_AXIS));
        issueBooksBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnBookBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        issuedBooksBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        changePasswordBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        logo.setHorizontalAlignment(JLabel.CENTER);
        logo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        logo.setVerticalTextPosition(JLabel.BOTTOM);
        logo.setHorizontalTextPosition(JLabel.CENTER);

        JSeparator sp = new JSeparator();
        sp.setSize(100, 20);
        sideBar.setBorder(new EmptyBorder(new Insets(0, 10, 0, 10)));
        sideBar.add(logo);
        sideBar.add(Box.createRigidArea(new Dimension(100, 100)));
        sideBar.add(issueBooksBtn);
        sideBar.add(Box.createRigidArea(new Dimension(100, 70)));
        sideBar.add(returnBookBtn);
        sideBar.add(Box.createRigidArea(new Dimension(100, 70)));
        sideBar.add(issuedBooksBtn);
        sideBar.add(Box.createRigidArea(new Dimension(100, 70)));
        sideBar.add(changePasswordBtn);

        cardLayout = new CardLayout();
        issueBooks = new IssueBook(librarian);
        returnBooks = new ReturnBook(librarian);
        changePassword = new ChangePassword(librarian);
        issuedBooks = new IssuedBooks();

        renderer.setLayout(cardLayout);

        renderer.add("Issue_Books", issueBooks);
        renderer.add("Return_Books", returnBooks);
        renderer.add("Issued_Books", issuedBooks);
        renderer.add("Change_Password", changePassword);

        MainPagePanel.setLayout(null);
        MainPagePanel.add(sideBar);
        MainPagePanel.add(renderer);

        this.add(MainPagePanel);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == issueBooksBtn){
            cardLayout.show(renderer, "Issue_Books");
        }
        if(e.getSource() == returnBookBtn){
            cardLayout.show(renderer, "Return_Books");
        }
        if(e.getSource() == issuedBooksBtn){
            cardLayout.show(renderer, "Issued_Books");
        }
        if(e.getSource() == changePasswordBtn){
            cardLayout.show(renderer, "Change_Password");
        }
    }
}
