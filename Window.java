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

    private ArrayList<String> strings;
    private HandlingStrategy method;
    private final JTextArea inputArea;
    private final JTextArea outputArea;

    Window(String name) {
        super(name);
        strings = new ArrayList<>();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        inputArea = new JTextArea();
        add(inputArea);
        inputArea.setEditable(false);

        JButton read = new JButton("Read");
        add(read);

        JButton write = new JButton("Write");
        add(write);

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

        read.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser("F:\\BSU-FAMCs\\2-course\\IP\\sem-proj\\src\\main\\java\\com\\example\\semproj\\inputs");
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
            showInput();
            solveProblems();
            showOutput();
        });

        write.addActionListener(e -> {
            try {
                String dir = "F:\\BSU-FAMCs\\2-course\\IP\\sem-proj\\src\\main\\java\\com\\example\\semproj\\outputs\\";
                String fileName = JOptionPane.showInputDialog("Enter file name you want to save result to");
                if (fileName.isEmpty()) throw new IllegalArgumentException("File name is empty.");
                if (txtWrite.isSelected()){
                    writeToTXT(dir + fileName + ".txt");
                } else if (xmlWrite.isSelected()){
                    writeToXML(dir + fileName + ".xml");
                }
                JOptionPane.showMessageDialog(null, "File was written successfully!");
            } catch (IOException | ParserConfigurationException | TransformerException |
                     IllegalArgumentException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void writeToTXT(String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        for (String string : strings) {
            fileWriter.write(string);
            fileWriter.write("\n");
        }
        fileWriter.close();
    }

    private void writeToXML(String fileName) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        Document document = factory.newDocumentBuilder().newDocument();
        Element root = document.createElement("text");
        document.appendChild(root);
        for (String string : strings) {
            Element element = document.createElement("string");
            element.setTextContent(string);
            root.appendChild(element);
        }
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(document), new StreamResult(new File(fileName)));
    }

    private void solveProblems() {
        Pattern p = Pattern.compile("(\\s*-?\\s*(\\(*\\s*(-?\\s*\\d+(\\.\\d+)?)\\s*\\)*\\s*)*[-+*/^](\\s*\\(*\\s*(-?\\s*\\d+(\\.\\d+)?)\\s*\\)*\\s*)+)+");
        String oldSubstring;
        String newSubstring;
        for (int i = 0; i < strings.size(); i++) {
            Matcher matcher = p.matcher(strings.get(i));
            while (matcher.find()) {
                oldSubstring = strings.get(i).substring(matcher.start(), matcher.end());
                newSubstring = SmartCalculator.calculate(oldSubstring);
                strings.set(i, strings.get(i).replace(oldSubstring, newSubstring));
            }
        }

    }

    private void setStrategy(HandlingStrategy handlingStrategy) {
        method = handlingStrategy;
    }

    private void showInput() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : strings) {
            stringBuilder.append(string).append("\n");
        }
        inputArea.setText(stringBuilder.toString());
    }

    private void showOutput() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : strings) {
            stringBuilder.append(string).append("\n");
        }
        outputArea.setText(stringBuilder.toString());
    }
}