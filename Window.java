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

        JRadioButton txtWrite = new JRadioButton(".txt");
        add(txtWrite);
        txtWrite.setSelected(true);
        JRadioButton xmlWrite = new JRadioButton(".xml");
        add(xmlWrite);
        ButtonGroup writeGroup = new ButtonGroup();
        writeGroup.add(txtWrite);
        writeGroup.add(xmlWrite);

        outputArea = new JTextArea();
        add(outputArea);
        outputArea.setEditable(false);
        outputArea.setColumns(25);
        outputArea.setBackground(Color.white);
        outputArea.setBorder(BorderFactory.createLineBorder(Color.gray, 1));


        read.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(".");
            try {
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    if (fileChooser.getSelectedFile().getName().endsWith("txt")) {
                        setStrategy(new TextHandlingStrategy());
                    } else if (fileChooser.getSelectedFile().getName().endsWith("xml")) {
                        setStrategy(new XMLHandlingStrategy());
                    } else throw new IllegalArgumentException();
                    strings = method.getStringArray(fileChooser.getSelectedFile());
                }
            } catch (IllegalArgumentException exception) {
                JOptionPane.showMessageDialog(null, "Choose correct file.", "Illegal file format!", JOptionPane.ERROR_MESSAGE);
            } catch (IOException | ParserConfigurationException | SAXException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
            }

        });
    }

    private void setStrategy(HandlingStrategy handlingStrategy) {
        method = handlingStrategy;
    }
}
