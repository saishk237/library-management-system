package com.pbl;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.pbl.librarian.LibrarianDashboard;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        //new SplashScreen();
        Database db = new Database();
        db.connect();
        try {
            UIManager.setLookAndFeel( new FlatDarculaLaf());
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        new Login();
    }
}
