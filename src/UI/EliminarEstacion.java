/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Controladores.ControladorEstacion;
import Controladores.FabricaObjetos;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Paradise
 */
public class EliminarEstacion extends javax.swing.JFrame {
    private final FabricaObjetos fabrica;
    private final JFrame uiParent;

    /**
     * Creates new form AgregarEmpleado
     */
    public EliminarEstacion(JFrame parent, FabricaObjetos fabricaObj) {
        initComponents();
        setVisible(true);
        uiParent = parent;
        uiParent.setVisible(false);
        fabrica = fabricaObj;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        botonSalir = new javax.swing.JButton();
        botonEliminar = new javax.swing.JButton();
        labelTitulo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridLayout(2, 2, 15, 15));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Id:");
        jPanel1.add(jLabel2);
        jPanel1.add(jTextField1);

        botonSalir.setText("Salir");
        botonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalirActionPerformed(evt);
            }
        });
        jPanel1.add(botonSalir);

        botonEliminar.setText("Eliminar");
        botonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(botonEliminar);

        labelTitulo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTitulo.setText("Eliminar Estacion");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(labelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalirActionPerformed
        uiParent.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_botonSalirActionPerformed

    private void botonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarActionPerformed

        if(!jTextField1.getText().equals(""))
        {
            ControladorEstacion ces = new ControladorEstacion(fabrica);
            ces.eliminar(jTextField1.getText());
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Por favor llene el campo vacio");
        }

    }//GEN-LAST:event_botonEliminarActionPerformed

    public boolean esNumero(String num)
    {
        for(int i=0; i<num.length(); i++)
        {
            if(!num.substring(i, i+1).equals("0") && !num.substring(i, i+1).equals("1")
                    && !num.substring(i, i+1).equals("2") && !num.substring(i, i+1).equals("3")
                    && !num.substring(i, i+1).equals("4") && !num.substring(i, i+1).equals("5")
                    && !num.substring(i, i+1).equals("6") && !num.substring(i, i+1).equals("7")
                    && !num.substring(i, i+1).equals("8") && !num.substring(i, i+1).equals("9"))
            {
                return false;
            }
        }
        return true;
    }
    
    
    /**
     * @param args the command line arguments
     */
  //  public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
 /*       try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AgregarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AgregarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AgregarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AgregarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
*/
        /* Create and display the form */
 /*       java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AgregarEmpleado().setVisible(true);
            }
        });
    }
  */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonEliminar;
    private javax.swing.JButton botonSalir;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel labelTitulo;
    // End of variables declaration//GEN-END:variables
}
