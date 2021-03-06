/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Lee Zhen Yong (bruceoutdoors@gmail.com)
 */
public class AddCommentDialog extends javax.swing.JDialog {
    final private int postId;
    /**
     * Creates new form AddCommentDialog
     */
    public AddCommentDialog(JDialog parent, boolean modal, int postId) {
        super(parent, modal);
        initComponents();
        this.postId = postId;
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
        commenTxtArea = new javax.swing.JTextArea();
        nameField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        addCommentBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        commenTxtArea.setColumns(20);
        commenTxtArea.setRows(5);
        commenTxtArea.setText("Anonymous was here.");
        jScrollPane1.setViewportView(commenTxtArea);

        nameField.setText("Anonymous");

        jLabel1.setText("Name : ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("New Comment");

        addCommentBtn.setText("Add Comment");
        addCommentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCommentBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addCommentBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jScrollPane1)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(addCommentBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addCommentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCommentBtnActionPerformed
        String sql = "INSERT INTO comment (name, comment, post_id, date_created) VALUES (?, ?, ?, ?)";
        
        try {
            PreparedStatement ps = core.DB.getInstance().getPreparedStatement(sql);
            ps.setString(1, nameField.getText());
            ps.setString(2, commenTxtArea.getText());
            ps.setInt(3, postId);
            ps.setTimestamp(4, new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
            
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Comment has successfully been posted.", "Successfully added comment!", JOptionPane.INFORMATION_MESSAGE);
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString(), "Invalid content", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_addCommentBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addCommentBtn;
    private javax.swing.JTextArea commenTxtArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nameField;
    // End of variables declaration//GEN-END:variables
}
