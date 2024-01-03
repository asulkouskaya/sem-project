package com.example.semproj;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int interface_;
        Scanner in = new Scanner(System.in);
        do {
            System.out.println("Введите 1, если хотите использовать GUI; введите 2, если хотите спользовать Web-interface: ");
            interface_ = in.nextInt();
        } while (interface_ != 1 && interface_ != 2);

        if (interface_ == 1) {
            Window chooser = new Window("Semester project: calculations");
            chooser.pack();
            chooser.setSize(800, 400);
            chooser.setLocation(250, 120);
            chooser.setVisible(true);
        }

    }
}
