/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Controladores.FabricaObjetos;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author gquevedo
 */
public class UIMainMenu extends javax.swing.JFrame {
    
    private static FabricaObjetos fabrica;
    /**
     * Creates new form UIMainMenu
     */
    public UIMainMenu(){
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        fabrica = new FabricaObjetos();
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        etqStatus = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        bttnAdministracion = new javax.swing.JButton();
        bttnEmpleados = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        bttnClientes = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 600));

        etqStatus.setFont(new java.awt.Font("Verdana", 0, 24)); // NOI18N
        etqStatus.setText("SMT");
        jPanel2.add(etqStatus);

        getContentPane().add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel1.setLayout(new java.awt.GridLayout(2, 2, 5, 5));

        bttnAdministracion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui_elements/adminIco.png"))); // NOI18N
        bttnAdministracion.setText("Administracion");
        bttnAdministracion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bttnAdministracionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bttnAdministracionMouseExited(evt);
            }
        });
        bttnAdministracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttnAdministracionActionPerformed(evt);
            }
        });
        jPanel1.add(bttnAdministracion);

        bttnEmpleados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui_elements/operatorIco.png"))); // NOI18N
        bttnEmpleados.setText("Operadores");
        bttnEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bttnEmpleadosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bttnEmpleadosMouseExited(evt);
            }
        });
        bttnEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttnEmpleadosActionPerformed(evt);
            }
        });
        jPanel1.add(bttnEmpleados);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui_elements/exitIco.png"))); // NOI18N
        jButton3.setText("Salir");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton3MouseExited(evt);
            }
        });
        jPanel1.add(jButton3);

        bttnClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui_elements/clientsIco.png"))); // NOI18N
        bttnClientes.setText("Clientes");
        bttnClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bttnClientesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bttnClientesMouseExited(evt);
            }
        });
        bttnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttnClientesActionPerformed(evt);
            }
        });
        jPanel1.add(bttnClientes);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bttnAdministracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnAdministracionActionPerformed
        // TODO add your handling code here:

        //UILoginDialog.loguear(this, true);
        UIAdminStruct uiAdministrador = new UIAdminStruct(this, fabrica);
        
    }//GEN-LAST:event_bttnAdministracionActionPerformed

    private void bttnEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnEmpleadosActionPerformed
        // TODO add your handling code here:

       int val= UILoginDialog.loguear(this, true, fabrica);
       System.out.println("Valor: "+val);
       if(val==1)
       {
           //Aqui iria una instancia a una ventana que aun no existe :P
       }
        
    }//GEN-LAST:event_bttnEmpleadosActionPerformed

    private void bttnAdministracionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnAdministracionMouseEntered
        // TODO add your handling code here:
        etqStatus.setText("Administracion principal del sistema");
    }//GEN-LAST:event_bttnAdministracionMouseEntered

    private void bttnEmpleadosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnEmpleadosMouseEntered
        // TODO add your handling code here:
        etqStatus.setText("Acciones realizables por empleados");
    }//GEN-LAST:event_bttnEmpleadosMouseEntered

    private void jButton3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseEntered
        // TODO add your handling code here:
        etqStatus.setText("Cerrar la aplicacion");
    }//GEN-LAST:event_jButton3MouseEntered

    private void bttnClientesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnClientesMouseEntered
        // TODO add your handling code here:
        etqStatus.setText("Asistente para clientes");
    }//GEN-LAST:event_bttnClientesMouseEntered

    private void bttnAdministracionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnAdministracionMouseExited
        // TODO add your handling code here:
        etqStatus.setText("SMT");
    }//GEN-LAST:event_bttnAdministracionMouseExited

    private void bttnEmpleadosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnEmpleadosMouseExited
        // TODO add your handling code here:
        etqStatus.setText("SMT");
    }//GEN-LAST:event_bttnEmpleadosMouseExited

    private void jButton3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseExited
        // TODO add your handling code here:
        etqStatus.setText("SMT");
    }//GEN-LAST:event_jButton3MouseExited

    private void bttnClientesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnClientesMouseExited
        // TODO add your handling code here:
        etqStatus.setText("SMT");
    }//GEN-LAST:event_bttnClientesMouseExited

    private void bttnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnClientesActionPerformed
        // TODO add your handling code here:
        
        UIClientes uiClientes = new UIClientes(this, fabrica);
        uiClientes.setVisible(true);
        
    }//GEN-LAST:event_bttnClientesActionPerformed

    public static void abrirOtraVentana()
    {
        JOptionPane.showMessageDialog(null, "Mensaje importante :O", "Mensaje importante :O",JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UIMainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UIMainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UIMainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UIMainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new UIMainMenu().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bttnAdministracion;
    private javax.swing.JButton bttnClientes;
    private javax.swing.JButton bttnEmpleados;
    private javax.swing.JLabel etqStatus;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
