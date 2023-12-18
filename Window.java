package com.example.semproj;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Window extends JFrame {

    private final JTextArea inputArea;
    private final JTextArea outputArea;

    Window(String name) {
        super(name);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        inputArea = new JTextArea();
        add(inputArea);
        inputArea.setEditable(false);
        inputArea.setColumns(25);
        inputArea.setBackground(Color.white);
        inputArea.setBorder(BorderFactory.createLineBorder(Color.gray, 1));


        outputArea = new JTextArea();
        add(outputArea);
        outputArea.setEditable(false);
        outputArea.setColumns(25);
        outputArea.setBackground(Color.white);
        outputArea.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
    }
}