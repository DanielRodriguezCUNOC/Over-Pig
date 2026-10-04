package com.piglatin.ui.infrastructure.views;

import com.formdev.flatlaf.FlatDarkLaf;
import com.piglatin.common.application.dto.CompilationMode;
import com.piglatin.ui.domain.wrapper.FileNode;
import com.piglatin.ui.infrastructure.facade.LanguajeCompilerFacade;
import com.piglatin.ui.infrastructure.facade.dto.*;
import com.piglatin.ui.infrastructure.views.components.TextLineNumber;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class CodeEditorView extends JFrame {

    // UI Colors
    private final Color bgDark = new Color(30, 30, 30);
    private final Color bgPanel = new Color(37, 37, 40);
    private final Color orangeAccent = new Color(255, 140, 0);
    private final Color fgText = new Color(220, 220, 220);

    // Components
    private JTree fileTree;
    private JTabbedPane editorTabbedPane;
    private JLabel statusBar;
    private JTabbedPane bottomTabs;
    private File currentRootFile;
    private final LanguajeCompilerFacade compilerFacade;
    private JButton btnCompile;
    private final KeyStroke ctrlS = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK);

    public CodeEditorView(LanguajeCompilerFacade compilerFacade) {
        this.compilerFacade = compilerFacade;
        setupTheme();
        setupAppIcon();
        initComponents();
        setupLayout();
        setupCompilerListener();
    }

    private void setupTheme() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
            UIManager.put("Component.focusColor", orangeAccent);
            UIManager.put("TabbedPane.selectedBackground", bgPanel);
            UIManager.put("TabbedPane.underlineColor", orangeAccent);
            UIManager.put("Button.background", bgPanel);
            UIManager.put("Button.hoverBackground", orangeAccent);
            UIManager.put("Button.hoverForeground", Color.BLACK);
            UIManager.put("Tree.selectionBackground", orangeAccent);
            UIManager.put("Tree.selectionForeground", Color.BLACK);
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf");
        }
    }

    private void initComponents() {
        setTitle("C3D Editor");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- Toolbar ---
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setBackground(bgPanel);

        JButton btnOpenDir = createStyledButton("📂 Open Directory");
        btnCompile = createStyledButton("⚙ Compile");
        JButton btnRun = createStyledButton("▶ Execute");
        btnRun.addActionListener(this::onRunClicked);

        btnOpenDir.addActionListener( e -> chooseAndLoadDirectory() );

        toolBar.add(btnCompile);
        toolBar.add(btnOpenDir);
        toolBar.addSeparator();
        toolBar.add(btnRun);
        add(toolBar, BorderLayout.NORTH);

        // --- File Tree ---
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Project Root");
        fileTree = new JTree(root);
        fileTree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) fileTree.getLastSelectedPathComponent();
            if (selectedNode != null && selectedNode.isLeaf()){
                File selectedFile = getSelectedFileOrDirFromNode(selectedNode);
                if (selectedFile != null && selectedFile.isFile())
                    openFileInEditor(selectedFile);

            }
        });

        fileTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                    int row = fileTree.getRowForLocation(e.getX(), e.getY());
                    if (row != -1) {
                        openSelectedFileFromTree();
                    }
                }
            }
        });

        fileTree.setCellRenderer(new javax.swing.tree.DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                File file = getSelectedFileOrDirFromNode(node);

                if (file != null && file.isFile()) {
                    ImageIcon fileIcon = getIconForFile(file);
                    if (fileIcon != null) {
                        setIcon(fileIcon);
                    }
                }
                return this;
            }
        });

        fileTree.setBackground(bgDark);
        fileTree.setForeground(fgText);
        setupFileTreeContextMenu();
        JScrollPane treeScroll = new JScrollPane(fileTree);
        treeScroll.setMinimumSize(new Dimension(200, 0));

        // --- Code Editor ---
        editorTabbedPane = new JTabbedPane();
        editorTabbedPane.setFont(new Font("Consolas", Font.PLAIN, 14));
        editorTabbedPane.setBackground(bgDark);
        editorTabbedPane.setForeground(fgText);
        editorTabbedPane.addChangeListener(e -> updateLineAndColumn());

        JScrollPane editorScroll = new JScrollPane(editorTabbedPane);

        // --- Bottom Tabs  ---
        bottomTabs = new JTabbedPane();
        bottomTabs.setPreferredSize(new Dimension(0, 250));

        bottomTabs.addTab("Symbol Table", createTablePanel(new String[]{"ID", "Name", "Type", "Scope"}));
        bottomTabs.addTab("Types Table", createTablePanel(new String[]{"Type Name", "Category", "Size"}));
        bottomTabs.addTab("Scope Table", createTablePanel(new String[]{"Scope ID", "Parent Scope", "Description"}));
        bottomTabs.addTab("Error Table", createTablePanel(new String[]{"Line", "Column", "Type", "Description"}));

        // --- Split Panes ---
        JSplitPane rightSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, editorTabbedPane, bottomTabs);
        rightSplit.setResizeWeight(0.7);
        rightSplit.setBorder(null);

        JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeScroll, rightSplit);
        mainSplit.setDividerLocation(250);
        mainSplit.setBorder(null);

        add(mainSplit, BorderLayout.CENTER);

        // --- Status Bar ---
        statusBar = new JLabel(" Ready | Line: 1 | Column: 1 ");
        statusBar.setBorder(new EmptyBorder(5, 5, 5, 5));
        statusBar.setForeground(orangeAccent);
        statusBar.setBackground(bgPanel);
        statusBar.setOpaque(true);
        add(statusBar, BorderLayout.SOUTH);
        getRootPane().registerKeyboardAction(e ->
                saveCurrentFileAction(), ctrlS, JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    private void onRunClicked(ActionEvent e) {
        executePipeline(CompilationMode.FULL);
    }

    private void setupLayout() {
    }

    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(orangeAccent);
        return btn;
    }

    private JPanel createTablePanel(String[] columns) {
        JPanel panel = new JPanel(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        table.setBackground(bgDark);
        table.setForeground(fgText);
        table.getTableHeader().setBackground(bgPanel);
        table.getTableHeader().setForeground(orangeAccent);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private void updateLineAndColumn() {
        JTextArea activeEditor = getCurrentEditor();
        File activeFile = getCurrentFile();

        if (activeEditor == null) {
            statusBar.setText(" Ready | No files open ");
            return;
        }

        int caretPos = activeEditor.getCaretPosition();
        int rowNum = 1;
        int colNum = 1;

        try {
            int offset = activeEditor.getLineOfOffset(caretPos);
            rowNum = offset + 1;
            colNum = caretPos - activeEditor.getLineStartOffset(offset) + 1;
        } catch (Exception ex) {

        }

        String name = (activeFile != null) ? activeFile.getName() : "Untitled";
        statusBar.setText(" File: " + name + " | Line: " + rowNum + " | Column: " + colNum + " ");
    }

    private void setupFileTreeContextMenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem itemNewFile = new JMenuItem("New File");
        JMenuItem itemNewFolder = new JMenuItem("New Folder");
        JMenuItem itemOpen = new JMenuItem("Open");
        JMenuItem itemSave = new JMenuItem("Save");
        JMenuItem itemDelete = new JMenuItem("Delete");
        JMenuItem itemDownload = new JMenuItem("Download");

        popupMenu.add(itemNewFile);
        popupMenu.add(itemNewFolder);
        popupMenu.add(itemDelete);
        popupMenu.add(itemOpen);
        popupMenu.add(itemSave);
        popupMenu.addSeparator();
        popupMenu.add(itemDownload);

        itemNewFile.addActionListener(e -> createNewFileAction());
        itemNewFolder.addActionListener(e -> createNewFolderAction());
        itemOpen.addActionListener(e -> openSelectedFileFromTree());
        itemDelete.addActionListener(e -> deleteSelectedNodeAction());
        itemSave.addActionListener(e -> saveCurrentFileAction());
        itemDownload.addActionListener(e -> downloadSelectedNodeAction());

        fileTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger() || SwingUtilities.isRightMouseButton(e)) {
                    int row = fileTree.getClosestRowForLocation(e.getX(), e.getY());
                    fileTree.setSelectionRow(row);
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    public void loadProjectTree(File rootDir) {
        if (rootDir == null || !rootDir.isDirectory()) return;
        this.currentRootFile = rootDir;

        DefaultMutableTreeNode root = new DefaultMutableTreeNode(new FileNode(rootDir));
        buildTree(rootDir, root);
        fileTree.setModel(new DefaultTreeModel(root));
    }

    private void buildTree(File dir, DefaultMutableTreeNode parentNode) {
        File[] files = dir.listFiles((dir1, name) -> {
            File f = new File(dir1, name);
            if (f.isDirectory()) return true;
            return name.endsWith(".y") || name.endsWith(".z") || name.endsWith(".pig") || name.endsWith(".pi");
        });

        if (files != null) {
            for (File file : files) {
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new FileNode(file));
                parentNode.add(childNode);
                if (file.isDirectory()) {
                    buildTree(file, childNode);
                }
            }
        }
    }

    /**
     * Simulates opening a file from the JTree and loading it into the editor
     */
    private void openFileInEditor(File file) {
        if (file == null || !file.exists()) return;

        for (int i = 0; i < editorTabbedPane.getTabCount(); i++) {
            JScrollPane scroll = (JScrollPane) editorTabbedPane.getComponentAt(i);
            JTextArea area = (JTextArea) scroll.getViewport().getView();
            File openFile = (File) area.getClientProperty("file");

            if (openFile != null && openFile.getAbsolutePath().equals(file.getAbsolutePath())) {
                editorTabbedPane.setSelectedIndex(i);
                return;
            }
        }

        try {
            String content = Files.readString(file.toPath());

            JTextArea newEditor = new JTextArea(content);
            newEditor.setFont(new Font("Consolas", Font.PLAIN, 14));
            newEditor.setBackground(bgDark);
            newEditor.setForeground(fgText);
            newEditor.setCaretColor(orangeAccent);
            newEditor.setTabSize(4);
            newEditor.putClientProperty("file", file);

            newEditor.addCaretListener(e -> updateLineAndColumn());

            JScrollPane editorScroll = new JScrollPane(newEditor);
            TextLineNumber tln = new TextLineNumber(newEditor);
            tln.setCurrentLineForeground(orangeAccent);
            editorScroll.setRowHeaderView(tln);

            int newIndex = editorTabbedPane.getTabCount();
            editorTabbedPane.addTab(file.getName(), editorScroll);

            editorTabbedPane.setTabComponentAt(newIndex, createTabHeader(file, editorScroll));
            editorTabbedPane.setSelectedComponent(editorScroll);

            setTitle("C3D Editor - " + file.getName());
            updateLineAndColumn();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error reading file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createTabHeader(File file, JComponent tabContent) {
        JPanel pnl = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pnl.setOpaque(false);

        ImageIcon icon = getIconForFile((file));
        if (icon != null) {
            JLabel lblIcon = new JLabel(icon);
            pnl.add(lblIcon);
        }

        JLabel lblTitle = new JLabel(file.getName());
        lblTitle.setForeground(fgText);

        JButton btnClose = new JButton("✕");
        btnClose.setFont(new Font("Arial", Font.BOLD, 10));
        btnClose.setForeground(orangeAccent);
        btnClose.setMargin(new Insets(0, 4, 0, 4));
        btnClose.setFocusable(false);
        btnClose.setBorder(null);
        btnClose.setContentAreaFilled(false);
        btnClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnClose.addActionListener(e -> {
            int index = editorTabbedPane.indexOfComponent(tabContent);
            if (index != -1) {
                editorTabbedPane.removeTabAt(index);
            }
        });

        pnl.add(lblTitle);
        pnl.add(btnClose);
        return pnl;
    }

    /**
     * Sets up the Action Listener for the Compile button
     */
    private void setupCompilerListener() {
        if (btnCompile != null) btnCompile.addActionListener(this::onCompileClicked);
    }

    private void onCompileClicked(ActionEvent e) {
        executePipeline(CompilationMode.VALIDATE_ONLY);
    }

    private void executePipeline(CompilationMode mode) {
        JTextArea activeEditor = getCurrentEditor();
        File activeFile = getCurrentFile();

        if (activeEditor == null || activeFile == null) {
            JOptionPane.showMessageDialog(this, "No active file open.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String code = activeEditor.getText();
        if (code.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "The editor is empty.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String fileName = activeFile.getName();
        String extension = "";
        int lastDot = fileName.lastIndexOf('.');
        if (lastDot > 0) {
            extension = fileName.substring(lastDot);
        }

        String projectDir = activeFile.getParent();

        if (mode == CompilationMode.FULL) {

            try {
                Process process = compilerFacade.startInteractiveExecution(
                        code, extension, projectDir, fileName);
                TerminalDialog dialog = new TerminalDialog(this, process);
                dialog.setVisible(true);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al ejecutar: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }

        CompilationResultDTO result = compilerFacade.compileCode(
                code, extension, projectDir, fileName, mode);

        renderSymbolTable(result.symbols());
        renderTypesTable(result.types());
        renderScopeTable(result.scopes());
        renderErrorTable(result.errors());

        if (!result.isSuccessful()) {
            bottomTabs.setSelectedIndex(3);
            JOptionPane.showMessageDialog(this,
                    "Compilation failed. Check the Error Table.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Validation successful! No semantic errors found.",
                "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void renderSymbolTable(java.util.List<SymbolDTO> symbols) {
        JTable table = getTableFromTab(0);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (SymbolDTO sym : symbols) {
            model.addRow(new Object[]{sym.id(), sym.name(), sym.type(), sym.scope(), sym.line()});
        }
    }

    private void renderTypesTable(java.util.List<TypeDTO> types) {
        JTable table = getTableFromTab(1);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (TypeDTO type : types) {
            model.addRow(new Object[]{type.typeName(), type.category(), type.size()});
        }
    }

    private void renderScopeTable(java.util.List<ScopeDTO> scopes) {
        JTable table = getTableFromTab(2);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (ScopeDTO scope : scopes) {
            model.addRow(new Object[]{scope.scopeId(), scope.parentScope(), scope.description()});
        }
    }

    private void renderErrorTable(java.util.List<ErrorDTO> errors) {
        JTable table = getTableFromTab(3);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (ErrorDTO error : errors) {
            model.addRow(new Object[]{error.line(), error.column(), error.errorType(), error.message()});
        }
    }

    private JTable getTableFromTab(int tabIndex) {

        JPanel panel = (JPanel) bottomTabs.getComponentAt(tabIndex);
        JScrollPane scrollPane = (JScrollPane) panel.getComponent(0);
        return (JTable) scrollPane.getViewport().getView();
    }

    private void chooseAndLoadDirectory() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select Project Directory");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedDir = chooser.getSelectedFile();
            loadProjectTree(selectedDir);
        }
    }

    private File getSelectedDirectoryInTree() {
        if (currentRootFile == null) return null;

        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) fileTree.getLastSelectedPathComponent();
        if (selectedNode == null) return currentRootFile;

        Object[] pathNodes = selectedNode.getPath();
        StringBuilder relPath = new StringBuilder();
        for (int i = 1; i < pathNodes.length; i++) {
            relPath.append(File.separator).append(pathNodes[i].toString());
        }

        File targetFile = new File(currentRootFile.getParentFile(), relPath.toString());

        if (targetFile.isFile()) {
            return targetFile.getParentFile();
        }
        return targetFile.exists() ? targetFile : currentRootFile;
    }

    private void createNewFileAction() {
        if (currentRootFile == null) {
            JOptionPane.showMessageDialog(this, "Please open a project directory first.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String fileName = JOptionPane.showInputDialog(this, "Enter file name (e.g., main.pig, Nodo.z, utils.y):", "New File", JOptionPane.PLAIN_MESSAGE);
        if (fileName != null && !fileName.trim().isEmpty()) {
            File parentDir = getSelectedDirectoryInTree();
            File newFile = new File(parentDir, fileName.trim());

            try {
                if (newFile.createNewFile()) {
                    loadProjectTree(currentRootFile);
                    openFileInEditor(newFile);
                } else {
                    JOptionPane.showMessageDialog(this, "File already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error creating file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void createNewFolderAction() {
        if (currentRootFile == null) {
            JOptionPane.showMessageDialog(this, "Please open a project directory first.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String folderName = JOptionPane.showInputDialog(this, "Enter folder name:", "New Folder", JOptionPane.PLAIN_MESSAGE);
        if (folderName != null && !folderName.trim().isEmpty()) {
            File parentDir = getSelectedDirectoryInTree();
            File newFolder = new File(parentDir, folderName.trim());

            if (newFolder.mkdirs()) {
                loadProjectTree(currentRootFile);
            } else {
                JOptionPane.showMessageDialog(this, "Could not create folder or it already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openSelectedFileFromTree() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) fileTree.getLastSelectedPathComponent();
        if (selectedNode != null && selectedNode.isLeaf()) {
            File targetFile = getSelectedFileOrDirFromNode(selectedNode);

            if (targetFile != null && targetFile.exists() && targetFile.isFile()) {
                openFileInEditor(targetFile);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Could not find file: " + (targetFile != null ? targetFile.getAbsolutePath() : "null"),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean deleteRecursively(File file) {
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    boolean success = deleteRecursively(child);
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        return file.delete();
    }

    private void deleteSelectedNodeAction() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) fileTree.getLastSelectedPathComponent();

        if (selectedNode == null || selectedNode.isRoot()) {
            JOptionPane.showMessageDialog(this,
                    "Please select a file or folder to delete (cannot delete the root directory).",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        File targetFile = getSelectedFileOrDirFromNode(selectedNode);

        if (targetFile == null || !targetFile.exists()) {
            JOptionPane.showMessageDialog(this, "The selected file/folder does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String type = targetFile.isDirectory() ? "folder and all its contents" : "file";
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this " + type + "?\n" + targetFile.getName(),
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (deleteRecursively(targetFile)) {

               for (int i = 0; i < editorTabbedPane.getTabCount(); i++) {
                   JScrollPane scrollPane = (JScrollPane) editorTabbedPane.getComponentAt(i);
                   JTextArea area = (JTextArea) scrollPane.getViewport().getView();
                   File openFile = (File) area.getClientProperty("file");
                   if (openFile != null && openFile.getAbsolutePath().equals(targetFile.getAbsolutePath())) {
                       editorTabbedPane.removeTabAt(i);
                       break;
                   }
               }

                loadProjectTree(currentRootFile);
                JOptionPane.showMessageDialog(this, "Successfully deleted.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete the selected item.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private File getSelectedFileOrDirFromNode(DefaultMutableTreeNode node) {
        if (node == null) return null;
        Object userObj = node.getUserObject();
        if (userObj instanceof FileNode fileNode) {
            return fileNode.getFile();
        }
        return null;
    }

    private void saveCurrentFileAction() {
        JTextArea activeEditor = getCurrentEditor();
        File activeFile = getCurrentFile();

        if (activeEditor == null || activeFile == null) {
            saveFileAsAction();
            return;
        }

        try {
            Files.writeString(activeFile.toPath(), activeEditor.getText());
            statusBar.setText(" Saved: " + activeFile.getName() + " ");
            JOptionPane.showMessageDialog(this, "File saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveFileAsAction() {
        JTextArea activeEditor = getCurrentEditor();
        if (activeEditor == null) {
            JOptionPane.showMessageDialog(this, "There is no open file to save.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser chooser = new JFileChooser(currentRootFile != null ? currentRootFile : new File("."));
        chooser.setDialogTitle("Save File As...");

        int result = chooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File targetFile = chooser.getSelectedFile();
            try {
                Files.writeString(targetFile.toPath(), activeEditor.getText());
                openFileInEditor(targetFile);
                if (currentRootFile != null) {
                    loadProjectTree(currentRootFile);
                }
                JOptionPane.showMessageDialog(this, "File saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void downloadSelectedNodeAction() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) fileTree.getLastSelectedPathComponent();

        if (selectedNode == null) {
            JOptionPane.showMessageDialog(this, "Please select a file or folder from the tree to download/export.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        File sourceFile = getSelectedFileOrDirFromNode(selectedNode);

        if (sourceFile == null || !sourceFile.exists()) {
            JOptionPane.showMessageDialog(this, "Selected item does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Export / Download To...");

        if (sourceFile.isDirectory()) {
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        } else {
            chooser.setSelectedFile(new File(sourceFile.getName()));
        }

        int result = chooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File destination = chooser.getSelectedFile();
            try {
                if (sourceFile.isDirectory()) {
                    copyFolderRecursively(sourceFile.toPath(), destination.toPath());
                } else {
                    Files.copy(sourceFile.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
                JOptionPane.showMessageDialog(this, "Successfully exported to:\n" + destination.getAbsolutePath(), "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error exporting item: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void copyFolderRecursively(Path source, Path target) throws IOException {
        Files.walk(source).forEach(sourcePath -> {
            try {
                Path targetPath = target.resolve(source.relativize(sourcePath));
                Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException("Error copying path: " + sourcePath, e);
            }
        });
    }

    private JTextArea getCurrentEditor() {
        int index = editorTabbedPane.getSelectedIndex();
        if (index == -1) return null;
        JScrollPane scroll = (JScrollPane) editorTabbedPane.getComponentAt(index);
        return (JTextArea) scroll.getViewport().getView();
    }

    private File getCurrentFile() {
        JTextArea area = getCurrentEditor();
        if (area == null) return null;
        return (File) area.getClientProperty("file");
    }

    private void setupAppIcon() {
        try {

            URL iconURL = getClass().getResource("/icons/app_icon.png");
            if (iconURL != null) {
                ImageIcon icon = new ImageIcon(iconURL);
                setIconImage(icon.getImage());
            }
        } catch (Exception e) {
            System.err.println("Could not load app icon: " + e.getMessage());
        }
    }

    /**
     * Retorna el ImageIcon ajustado a 16x16 según la extensión del archivo.
     */
    private ImageIcon getIconForFile(File file) {
        if (file == null) return null;
        if (file.isDirectory()) {
            return null;
        }

        String name = file.getName().toLowerCase();
        String iconPath = "";

        if (name.endsWith(".pig")) {
            iconPath = "/icons/pig_color.png";
        } else if (name.endsWith(".z")) {
            iconPath = "/icons/alien_color.png";
        } else if (name.endsWith(".y")) {
            iconPath = "/icons/snake_color.png";
        }

        if (!iconPath.isEmpty()) {
            java.net.URL url = getClass().getResource(iconPath);
            if (url != null) {
                ImageIcon originalIcon = new ImageIcon(url);
                Image scaledImg = originalIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImg);
            }
        }
        return null;
    }
}