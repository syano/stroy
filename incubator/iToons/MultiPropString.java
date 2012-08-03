/*
 * MultiPropString.java
 *
 * Created on November 20, 2004, 11:57 AM
 */

import java.util.*;

/**
 *
 * @author  oc
 */
public class MultiPropString extends javax.swing.JDialog {
    
    /** Creates new form MultiPropString */
    public MultiPropString(java.awt.Frame parent, boolean modal, String name, Vector data ) {
        super(parent, modal);
        this.data = data;
        initComponents();
        setTitle( name );
        jList1.setListData( data );
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButtonRemove = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jButtonUp = new javax.swing.JButton();
        jButtonDown = new javax.swing.JButton();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jTextField1, gridBagConstraints);

        jButton1.setText("add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        getContentPane().add(jButton1, new java.awt.GridBagConstraints());

        jButtonRemove.setText("remove");
        jButtonRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoveActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        getContentPane().add(jButtonRemove, gridBagConstraints);

        jButton3.setText("ok");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        getContentPane().add(jButton3, gridBagConstraints);

        jScrollPane1.setViewportView(jList1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jScrollPane1, gridBagConstraints);

        jButtonUp.setText("up");
        jButtonUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        getContentPane().add(jButtonUp, gridBagConstraints);

        jButtonDown.setText("down");
        jButtonDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDownActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        getContentPane().add(jButtonDown, gridBagConstraints);

        pack();
    }//GEN-END:initComponents

    private void jButtonDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDownActionPerformed
        if ( jList1.getSelectedValues() != null ) {
            int idx = jList1.getSelectedIndex();

            if ( idx < data.size() - 1 ) {
                String txt = (String)data.get(idx);
                data.remove( idx );            
                data.add( idx + 1, txt );
                jList1.setListData( data );
            }
        }        
    }//GEN-LAST:event_jButtonDownActionPerformed

    private void jButtonUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpActionPerformed
        if ( jList1.getSelectedValues() != null ) {
            int idx = jList1.getSelectedIndex();

            if ( idx > 0 ) {
                String txt = (String)data.get(idx);
                data.remove( idx );            
                data.add( idx - 1, txt );
                jList1.setListData( data );
            }
        }        

    }//GEN-LAST:event_jButtonUpActionPerformed

    private void jButtonRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoveActionPerformed
        if ( jList1.getSelectedValues() != null ) {
            int idx = jList1.getSelectedIndex();
            
            data.remove( idx );
            
            jList1.setListData( data );
        }        
    }//GEN-LAST:event_jButtonRemoveActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String newArtist = jTextField1.getText();
        
        if ( newArtist != "" ) {            
            data.add( newArtist );            
            jList1.setListData( data );
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    
    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        new MultiPropString(new javax.swing.JFrame(), true).show();
//    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButtonDown;
    private javax.swing.JButton jButtonRemove;
    private javax.swing.JButton jButtonUp;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

    private Vector data;
    
}