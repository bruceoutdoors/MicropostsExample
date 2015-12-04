/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Lee Zhen Yong (bruceoutdoors@gmail.com)
 */
public class MainWindow extends javax.swing.JFrame {

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();

        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));

        refreshPosts();
    }

    private void refreshPosts() {
        postPanel.removeAll();
        core.DB db = core.DB.getInstance();
        ResultSet rs = null;
        try {
            rs = db.executeQuery("SELECT * FROM post ORDER BY date_created DESC");
            while (rs.next()) {
                String d = new SimpleDateFormat(" (MMM d)").format(rs.getTimestamp("date_created"));
                addPost(rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("name") + d,
                        rs.getString("content"));
            }
        } catch (Exception ex) {
            System.err.println(ex.toString());
            return;
        }

        validate();

        // puts scrollbar to the top
        javax.swing.SwingUtilities.invokeLater(() -> {
            jScrollPane1.getVerticalScrollBar().setValue(0);
        });
    }

    private void addPost(int id, String title, String author, String content) {
        java.awt.Frame thisWindow = this;
        JTextArea contentTxtArea = new JTextArea(content);
        contentTxtArea.setWrapStyleWord(true);
        contentTxtArea.setLineWrap(true);
        contentTxtArea.setEditable(false);

        JLabel titleLbl = new JLabel(title);
        Font titleFont = titleLbl.getFont();
        titleLbl.setFont(new Font(titleFont.getName(), Font.PLAIN, titleFont.getSize() + 5));
        JPanel leftAlignPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titleLbl.setForeground(Color.BLUE);
        titleLbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ViewPostDialog(thisWindow, true, id).setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                titleLbl.setForeground(Color.MAGENTA);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                titleLbl.setForeground(Color.BLUE);
            }
        });

        JLabel auth = new JLabel("Written by " + author);

        leftAlignPanel.add(titleLbl);
        leftAlignPanel.add(auth);

        postPanel.add(leftAlignPanel);
        postPanel.add(contentTxtArea);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        postPanel = new javax.swing.JPanel();
        addPostBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Microposting Example");

        jScrollPane1.setInheritsPopupMenu(true);
        jScrollPane1.setVerifyInputWhenFocusTarget(false);

        javax.swing.GroupLayout postPanelLayout = new javax.swing.GroupLayout(postPanel);
        postPanel.setLayout(postPanelLayout);
        postPanelLayout.setHorizontalGroup(
            postPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 468, Short.MAX_VALUE)
        );
        postPanelLayout.setVerticalGroup(
            postPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 310, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(postPanel);

        addPostBtn.setText("Add Post");
        addPostBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPostBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addPostBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addPostBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addPostBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPostBtnActionPerformed
        new AddPostDialog(this, true).setVisible(true);
        refreshPosts();
    }//GEN-LAST:event_addPostBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addPostBtn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel postPanel;
    // End of variables declaration//GEN-END:variables
}
