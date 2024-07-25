import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.TextAction;
import javax.swing.text.Utilities;
import javax.swing.undo.UndoManager;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.awt.print.PrinterException;

public class TextEditor1 extends JFrame implements ActionListener {

    JTextArea textArea;
    JScrollPane scrollPane;
    JButton fontColorButton, closeButton;
    JComboBox<String> fontBox;
    JSpinner fontSizeSpinner;
    UndoManager undoManager; 

    JMenuBar menuBar;
    JMenu fileMenu, editMenu, formatMenu, viewMenu, toolsMenu, helpMenu;
    JMenuItem newFileItem, openItem, saveItem, saveAllItem, printItem, closeAllItem, exitItem;
    JMenuItem undoItem, redoItem;
    JMenuItem cutItem, copyItem, pasteItem, deleteItem;
    JMenuItem findItem, findNextItem, replaceItem, goToItem, selectAllItem, timeDateItem;
    JCheckBoxMenuItem wordWrapItem, statusBarItem;
    JMenuItem zoomInItem, zoomOutItem, fullScreenItem;
    JMenuItem runItem, compareFileItem, fileExplorerItem;
    JMenuItem aboutItem;

    JLabel statusBar;

    JMenuItem italicItem, boldItem, underlineItem;

    public TextEditor1() {
        
        // Set up the main frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Text Editor");
        this.setSize(600, 600);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        // Initialize text area with word wrap enabled and default font
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        undoManager = new UndoManager();
        textArea.getDocument().addUndoableEditListener(undoManager); // Attach UndoManager to document
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            // Document listener to update status bar on text changes
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateStatusBar();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateStatusBar();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateStatusBar();
            }
        });

        // Scroll pane for the text area
        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(550, 400));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Status bar initialization
        statusBar = new JLabel("Ln 1, Col 1");
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        statusBar.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.add(statusBar, BorderLayout.CENTER);

        // Font color button
        fontColorButton = new JButton("Color");
        fontColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color selectedColor = JColorChooser.showDialog(null, "Choose Font Color", Color.BLACK);
                if (selectedColor != null) {
                    textArea.setForeground(selectedColor);
                }
            }
        });
        
        // Close button
        closeButton = new JButton("Close");
        closeButton.addActionListener(this);

        // Font selection dropdown
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JComboBox<String> fontBox = new JComboBox<>(fonts);
        fontBox.addActionListener(this);
        fontBox.setSelectedItem("Arial");

        
        
        // Set initial font for text area
        Font initialFont = new Font("Arial", Font.PLAIN, 14); // Example initial font for text area
        textArea.setFont(initialFont);
        // Font size spinner
        fontSizeSpinner = new JSpinner();
        fontSizeSpinner.setPreferredSize(new Dimension(50, 25));
        fontSizeSpinner.setValue(16);
        fontSizeSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // Change text area font size dynamically
                textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, (int) fontSizeSpinner.getValue()));
            }
        });

        // Toolbar panel
        JPanel toolbar = new JPanel();
        toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.X_AXIS)); // Use BoxLayout with horizontal orientation

        // Icons (replace with your actual file paths)
        ImageIcon newIcon = new ImageIcon("C:\\Users\\lishi\\OneDrive\\Desktop\\pics\\new.png");
        ImageIcon openIcon = new ImageIcon("C:\\Users\\lishi\\OneDrive\\Desktop\\pics\\open.png");
        ImageIcon saveIcon = new ImageIcon("C:\\Users\\lishi\\OneDrive\\Desktop\\pics\\save.png");
        ImageIcon cutIcon = new ImageIcon("C:\\Users\\lishi\\OneDrive\\Desktop\\pics\\cut.png");
        ImageIcon copyIcon = new ImageIcon("C:\\Users\\lishi\\OneDrive\\Desktop\\pics\\copy.png");
        ImageIcon pasteIcon = new ImageIcon("C:\\Users\\lishi\\OneDrive\\Desktop\\pics\\paste.png");
        ImageIcon findIcon = new ImageIcon("C:\\Users\\lishi\\OneDrive\\Desktop\\pics\\find.png");
        ImageIcon italicIcon = new ImageIcon("C:\\Users\\lishi\\OneDrive\\Desktop\\pics\\italic.png");
        ImageIcon underlineIcon = new ImageIcon("C:\\Users\\lishi\\OneDrive\\Desktop\\pics\\underline.png");
        ImageIcon boldIcon = new ImageIcon("C:\\Users\\lishi\\OneDrive\\Desktop\\pics\\bold.png");

        // Buttons with icons
        JButton newButton = new JButton(newIcon);
        JButton openButton = new JButton(openIcon);
        JButton saveButton = new JButton(saveIcon);
        JButton cutButton = new JButton(cutIcon);
        JButton copyButton = new JButton(copyIcon);
        JButton pasteButton = new JButton(pasteIcon);
        JButton findButton = new JButton(findIcon);
        JButton italicButton = new JButton(italicIcon);
        JButton underlineButton = new JButton(underlineIcon);
        JButton boldButton = new JButton(boldIcon);

        // Add buttons to toolbar
        toolbar.add(newButton);
        toolbar.add(openButton);
        toolbar.add(saveButton);
        toolbar.add(cutButton);
        toolbar.add(copyButton);
        toolbar.add(pasteButton);
        toolbar.add(findButton);
        toolbar.add(italicButton);
        toolbar.add(underlineButton);
        toolbar.add(boldButton);
        toolbar.add(fontColorButton); // Adding font color button
        toolbar.add(fontBox); // Adding font selection dropdown
        toolbar.add(fontSizeSpinner); 

        // Add toolbar to frame
        add(toolbar, BorderLayout.NORTH);
// New button action
newButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Call existing method for new document
        textArea.setText(""); // Replace with your existing method name
    }
});

// Open button action
openButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Call existing method for opening a file
        openFile(); // Replace with your existing method name
    }
});

// Save button action
saveButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Call existing method for saving a file
        saveFile(); // Replace with your existing method name
    }
});

// Cut button action
cutButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Call existing method for cutting text
        cut(); // Replace with your existing method name
    }
});

// Copy button action
copyButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Call existing method for copying text
        copy(); // Replace with your existing method name
    }
});

// Paste button action
pasteButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Call existing method for pasting text
        paste(); // Replace with your existing method name
    }
});

// Find button action
findButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Call existing method for find functionality
        find(); // Replace with your existing method name
    }
});

// Italic button action
italicButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Call existing method for toggling italic
        toggleItalic(); // Replace with your existing method name
    }
});

// Underline button action
underlineButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Call existing method for toggling underline
        toggleUnderline(); // Replace with your existing method name
    }
});

// Bold button action
boldButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Call existing method for toggling bold
        toggleBold(); // Replace with your existing method name
    }
});

        // Add scroll pane with text area to frame
        add(scrollPane, BorderLayout.CENTER);

        // Add status panel to frame
        add(statusPanel, BorderLayout.SOUTH);

        // Menu bar setup
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        formatMenu = new JMenu("Format");
        viewMenu = new JMenu("View");
        toolsMenu = new JMenu("Tools");
        helpMenu = new JMenu("Help");

        // File menu items
        newFileItem = new JMenuItem("New File");
        openItem = new JMenuItem("Open");
        saveItem = new JMenuItem("Save");
        saveAllItem = new JMenuItem("Save All");
        printItem = new JMenuItem("Print");
        closeAllItem = new JMenuItem("Close All");
        exitItem = new JMenuItem("Exit");

        newFileItem.addActionListener(this);
        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        saveAllItem.addActionListener(this);
        printItem.addActionListener(this);
        closeAllItem.addActionListener(this);
        exitItem.addActionListener(this);

        fileMenu.add(newFileItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAllItem);
        fileMenu.add(printItem);
        fileMenu.add(closeAllItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // Edit menu items
        undoItem = new JMenuItem("Undo");
        redoItem = new JMenuItem("Redo");
        cutItem = new JMenuItem("Cut");
        copyItem = new JMenuItem("Copy");
        pasteItem = new JMenuItem("Paste");
        deleteItem = new JMenuItem("Delete");
        findItem = new JMenuItem("Find");
        findNextItem = new JMenuItem("Find Next");
        replaceItem = new JMenuItem("Replace");
        goToItem = new JMenuItem("Go To");
        selectAllItem = new JMenuItem("Select All");
        timeDateItem = new JMenuItem("Time/Date");

        undoItem.addActionListener(this);
        redoItem.addActionListener(this);
        cutItem.addActionListener(this);
        copyItem.addActionListener(this);
        pasteItem.addActionListener(this);
        deleteItem.addActionListener(this);
        findItem.addActionListener(this);
        findNextItem.addActionListener(this);
        replaceItem.addActionListener(this);
        goToItem.addActionListener(this);
        selectAllItem.addActionListener(this);
        timeDateItem.addActionListener(this);

        editMenu.add(undoItem);
        editMenu.add(redoItem);
        editMenu.addSeparator();
        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.add(deleteItem);
        editMenu.addSeparator();
        editMenu.add(findItem);
        editMenu.add(findNextItem);
        editMenu.add(replaceItem);
        editMenu.add(goToItem);
        editMenu.addSeparator();
        editMenu.add(selectAllItem);
        editMenu.add(timeDateItem);

        // Format menu items
        italicItem = new JMenuItem("Italic");
        boldItem = new JMenuItem("Bold");
        underlineItem = new JMenuItem("Underline");

        italicItem.addActionListener(this);
        boldItem.addActionListener(this);
        underlineItem.addActionListener(this);

        formatMenu.add(italicItem);
        formatMenu.add(boldItem);
        formatMenu.add(underlineItem);

        // View menu items
        zoomInItem = new JMenuItem("Zoom In");
        zoomOutItem = new JMenuItem("Zoom Out");
        fullScreenItem = new JMenuItem("Full Screen");
        statusBarItem = new JCheckBoxMenuItem("Status Bar", true);

        zoomInItem.addActionListener(this);
        zoomOutItem.addActionListener(this);
        fullScreenItem.addActionListener(this);
        statusBarItem.addActionListener(this);

        viewMenu.add(zoomInItem);
        viewMenu.add(zoomOutItem);
        viewMenu.add(fullScreenItem);
        viewMenu.addSeparator();
        viewMenu.add(statusBarItem);

        // Tools menu items
        runItem = new JMenuItem("Run");
        compareFileItem = new JMenuItem("Compare Files");
        fileExplorerItem = new JMenuItem("File Explorer");

        runItem.addActionListener(this);
        compareFileItem.addActionListener(this);
        fileExplorerItem.addActionListener(this);

        toolsMenu.add(runItem);
        toolsMenu.add(compareFileItem);
        toolsMenu.add(fileExplorerItem);

        // Help menu items
        aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(this);
        helpMenu.add(aboutItem);

        // Add menus to the menu bar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(viewMenu);
        menuBar.add(toolsMenu);
        menuBar.add(helpMenu);

        // Add the menu bar to the frame
        setJMenuBar(menuBar);

        // Make the frame visible
        this.setVisible(true);
    }

    // Action handler
    @Override
    public void actionPerformed(ActionEvent e) {
        // File menu actions
        if (e.getSource() == newFileItem) {
            textArea.setText("");
        } else if (e.getSource() == openItem) {
            openFile();
        } else if (e.getSource() == saveItem) {
            saveFile();
        } else if (e.getSource() == printItem) {
            printFile();
        } else if (e.getSource() == exitItem) {
            System.exit(0);
        }
    
        // Edit menu actions
        else if (e.getSource() == undoItem) {
            undo();
        } else if (e.getSource() == redoItem) {
            redo();
        } else if (e.getSource() == cutItem) {
            cut();
        } else if (e.getSource() == copyItem) {
            copy();
        } else if (e.getSource() == pasteItem) {
            paste();
        } else if (e.getSource() == deleteItem) {
            deleteSelectedText();
        } else if (e.getSource() == findItem) {
            find();
        } else if (e.getSource() == replaceItem) {
            replace();
        } else if (e.getSource() == findNextItem) {
            findNext();
        } else if (e.getSource() == goToItem) {
            goTo();
        } else if (e.getSource() == selectAllItem) {
            textArea.selectAll();
        } else if (e.getSource() == timeDateItem) {
            insertDateTime();
        }
    
        // Format menu actions
        else if (e.getSource() == italicItem) {
            toggleItalic();
        } else if (e.getSource() == boldItem) {
            toggleBold();
        } else if (e.getSource() == underlineItem) {
            toggleUnderline();
        }
    
        // View menu actions
        else if (e.getSource() == zoomInItem) {
            zoomIn();
        } else if (e.getSource() == zoomOutItem) {
            zoomOut();
        } else if (e.getSource() == fullScreenItem) {
            toggleFullScreen();
        } else if (e.getSource() == statusBarItem) {
            toggleStatusBar();
        }
    
        // Tools menu actions
        else if (e.getSource() == runItem) {
            run();
        } else if (e.getSource() == compareFileItem) {
            compareFiles();
        } else if (e.getSource() == fileExplorerItem) {
            openFileExplorer();
        }
    
        // Help menu actions
        else if (e.getSource() == aboutItem) {
            showAboutDialog();
        }

        if (e.getSource() == fontBox) {
            String selectedFont = (String) fontBox.getSelectedItem();
            if (selectedFont != null) {
                Font currentFont = textArea.getFont();
                Font newFont = new Font(selectedFont, currentFont.getStyle(), currentFont.getSize());
                textArea.setFont(newFont);
            }
        }
        updateStatusBar();
    }
    
    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int response = fileChooser.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                textArea.read(reader, null);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error opening file: " + ex.getMessage());
            }
        }
    }
    
    private void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        int response = fileChooser.showSaveDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                textArea.write(writer);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage());
            }
        }
    }
    
    private void printFile() {
        try {
            textArea.print();
        } catch (PrinterException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error printing: " + ex.getMessage());
        }
    }
    
    private void undo() {
        if (undoManager.canUndo()) {
            undoManager.undo();
        }
    }
    
    private void redo() {
        if (undoManager.canRedo()) {
            undoManager.redo();
        }
    }
    
    private void cut() {
        textArea.cut();
    }
    
    private void copy() {
        textArea.copy();
    }
    
    private void paste() {
        textArea.paste();
    }
    
    private void deleteSelectedText() {
        textArea.replaceSelection("");
    }
    
    private void find() {
        String searchText = JOptionPane.showInputDialog(this, "Enter text to find:");
        if (searchText != null && !searchText.isEmpty()) {
            String text = textArea.getText();
            int index = text.indexOf(searchText, textArea.getCaretPosition());
            if (index != -1) {
                textArea.setSelectionStart(index);
                textArea.setSelectionEnd(index + searchText.length());
            } else {
                JOptionPane.showMessageDialog(this, "Text not found: " + searchText);
            }
        }
    }
    
    private void findNext() {
        String searchText = JOptionPane.showInputDialog(this, "Enter text to find:");
        if (searchText != null && !searchText.isEmpty()) {
            String text = textArea.getText();
            int index = text.indexOf(searchText, textArea.getCaretPosition() + 1);
            if (index != -1) {
                textArea.setSelectionStart(index);
                textArea.setSelectionEnd(index + searchText.length());
            } else {
                JOptionPane.showMessageDialog(this, "No more occurrences of: " + searchText);
            }
        }
    }
    
    private void replace() {
        String findText = JOptionPane.showInputDialog(this, "Enter text to find:");
        if (findText != null && !findText.isEmpty()) {
            String replaceText = JOptionPane.showInputDialog(this, "Enter text to replace:");
            if (replaceText != null) {
                String text = textArea.getText();
                text = text.replace(findText, replaceText);
                textArea.setText(text);
            }
        }
    }
    
    private void goTo() {
        String lineNumberStr = JOptionPane.showInputDialog(this, "Enter line number:");
        if (lineNumberStr != null && !lineNumberStr.isEmpty()) {
            try {
                int lineNumber = Integer.parseInt(lineNumberStr);
                int startOffset = textArea.getLineStartOffset(lineNumber - 1);
                textArea.setCaretPosition(startOffset);
            } catch (NumberFormatException | BadLocationException e) {
                JOptionPane.showMessageDialog(this, "Invalid line number: " + lineNumberStr);
            }
        }
    }
    
    private void insertDateTime() {
        String dateTime = new SimpleDateFormat("HH:mm dd/MM/yyyy").format(new Date());
        textArea.insert(dateTime, textArea.getCaretPosition());
    }
    
    private void toggleItalic() {
        Font currentFont = textArea.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getStyle() ^ Font.ITALIC);
        textArea.setFont(newFont);
    }
    
    private void toggleBold() {
        Font currentFont = textArea.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getStyle() ^ Font.BOLD);
        textArea.setFont(newFont);
    }
    
    private void toggleUnderline() {
        Font currentFont = textArea.getFont();
        Map<TextAttribute, Object> attributes = new HashMap<>(currentFont.getAttributes());
        boolean isUnderline = TextAttribute.UNDERLINE_ON.equals(attributes.get(TextAttribute.UNDERLINE));
        if (isUnderline) {
            attributes.remove(TextAttribute.UNDERLINE);
        } else {
            attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        }
        Font newFont = new Font(attributes);
        textArea.setFont(newFont);
    }
    
    private void zoomIn() {
        Font currentFont = textArea.getFont();
        int size = currentFont.getSize();
        Font newFont = new Font(currentFont.getName(), currentFont.getStyle(), size + 2);
        textArea.setFont(newFont);
    }
    
    private void zoomOut() {
        Font currentFont = textArea.getFont();
        int size = currentFont.getSize();
        if (size > 2) {
            Font newFont = new Font(currentFont.getName(), currentFont.getStyle(), size - 2);
            textArea.setFont(newFont);
        }
    }
    
    private void toggleFullScreen() {
        if (this.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
            this.setExtendedState(JFrame.NORMAL);
        } else {
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
    }
    
    private void toggleStatusBar() {
        statusBar.setVisible(statusBarItem.isSelected());
    }
    
    private void run() {
        String command = "java -version"; // Example command to print Java version
        ProcessBuilder processBuilder = new ProcessBuilder(command.split("\\s+"));
        
        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                JOptionPane.showMessageDialog(this, "Run completed successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Run failed. Exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error executing command: " + ex.getMessage());
        }
    }
    
    
    private void compareFiles() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select files to compare");
        fileChooser.setMultiSelectionEnabled(true);
    
        int response = fileChooser.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = fileChooser.getSelectedFiles();
            if (selectedFiles.length != 2) {
                JOptionPane.showMessageDialog(this, "Please select exactly two files to compare.");
                return;
            }
    
            try {
                String content1 = readFile(selectedFiles[0]);
                String content2 = readFile(selectedFiles[1]);
    
                if (content1.equals(content2)) {
                    JOptionPane.showMessageDialog(this, "Files are identical.");
                } else {
                    JOptionPane.showMessageDialog(this, "Files are different.");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error comparing files: " + ex.getMessage());
            }
        }
    }
    
    private String readFile(File file) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        }
        return contentBuilder.toString();
    }
    
    private void openFileExplorer() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose a file or directory");
    
        int response = fileChooser.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(this, "Selected path: " + selectedFile.getAbsolutePath());
        }
    }
    
    private void showAboutDialog() {  
        String aboutMessage = "Text Editor\nVersion 1.0\n\n" +
    "Features:\n" +
    "- File operations (New, Open, Save, Print)\n" +
    "- Edit operations (Undo, Redo, Cut, Copy, Paste, Delete)\n" +
    "- Format options (Italic, Bold, Underline)\n" +
    "- View options (Zoom In, Zoom Out, Full Screen, Status Bar)\n" +
    "- Tools (Run, Compare Files, File Explorer)\n" +
    "- Help (About)\n\n" ;
    JOptionPane.showMessageDialog(this, aboutMessage, "About", JOptionPane.INFORMATION_MESSAGE);
    }

   
    
    private void updateStatusBar() {
        try {
            int caretPosition = textArea.getCaretPosition();
            int lineNumber = textArea.getLineOfOffset(caretPosition);
            int column = caretPosition - textArea.getLineStartOffset(lineNumber);
            statusBar.setText("Ln " + (lineNumber + 1) + ", Col " + (column + 1));
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

     
        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                new TextEditor1(); // Create an instance of your text editor
            });
        }
    }

