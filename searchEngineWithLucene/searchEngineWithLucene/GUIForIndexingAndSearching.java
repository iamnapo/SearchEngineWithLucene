package searchEngineWithLucene;//Napoleon Oikonomou

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class GUIForIndexingAndSearching {
    private JPanel rootPanel;
    static ArrayList<String> consoleMessages = new ArrayList<>();

    public static void main(String[] args) {
        JFrame frame = new JFrame("GUIForIndexingAndSearching");
        frame.setContentPane(new GUIForIndexingAndSearching().rootPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JButton IndexButton;
    private JTextField TokenInput;
    private JTextArea OutputArea;
    private JButton DataFolderPath;
    private String[] path = new String[2];


    private GUIForIndexingAndSearching() {
        IndexButton.addActionListener(e -> {
            try {
                FileIndexer.main(path);
            } catch (Exception ignored) {
            }
            OutputArea.setText(null);
            for (String consoleMessage : consoleMessages) {
                OutputArea.append(consoleMessage);
                OutputArea.append("\n");
            }
            consoleMessages.clear();
        });
        TokenInput.addActionListener(e -> {
            File indexDir = new File(path[0] + "/IndexDir");
            TokenSearcher tokenSearcher = new TokenSearcher();
            try {
                tokenSearcher.searchIndex(indexDir, TokenInput.getText(), 100);
            } catch (Exception ignored) {
            }
            OutputArea.setText(null);
            for (String consoleMessage : consoleMessages) {
                OutputArea.append(consoleMessage);
                OutputArea.append("\n");
            }
            consoleMessages.clear();
        });

        DataFolderPath.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));
            chooser.setDialogTitle("choosertitle");
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            chooser.setAcceptAllFileFilterUsed(false);
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
            }
            File folder = chooser.getCurrentDirectory();
            path[0] = folder.getAbsolutePath();
            OutputArea.append("Path Specified!\n");
        });
    }
}
