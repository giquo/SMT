/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Controladores.ControladorPasajero;
import Controladores.FabricaObjetos;
import Entidades.Pasajero;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Paradise
 */
public class ModificarPasajero extends javax.swing.JFrame {
    private final FabricaObjetos fabrica;
    private final JFrame uiParent;

    /**
     * Creates new form AgregarEmpleado
     */
    public ModificarPasajero(JFrame parent, FabricaObjetos fabricaObj) {
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
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        botonSalir = new javax.swing.JButton();
        botonAgregar = new javax.swing.JButton();
        labelTitulo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridLayout(7, 2, 15, 15));

        jLabel2.setText("Id:");
        jPanel1.add(jLabel2);
        jPanel1.add(jTextField1);

        jLabel3.setText("Nombre:");
        jPanel1.add(jLabel3);
        jPanel1.add(jTextField2);

        jLabel5.setText("Estrato:");
        jPanel1.add(jLabel5);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6" }));
        jPanel1.add(jComboBox2);

        jLabel7.setText("Direccion:");
        jPanel1.add(jLabel7);
        jPanel1.add(jTextField6);

        jLabel1.setText("Telefono:");
        jPanel1.add(jLabel1);
        jPanel1.add(jTextField8);

        jLabel9.setText("Genero:");
        jPanel1.add(jLabel9);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Masculino", "Femenino" }));
        jPanel1.add(jComboBox1);

        botonSalir.setText("Salir");
        botonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalirActionPerformed(evt);
            }
        });
        jPanel1.add(botonSalir);

        botonAgregar.setText("Modificar");
        botonAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarActionPerformed(evt);
            }
        });
        jPanel1.add(botonAgregar);

        labelTitulo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTitulo.setText("Modificar Pasajero");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57))
            .addGroup(layout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addComponent(labelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalirActionPerformed
        uiParent.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_botonSalirActionPerformed

    private void botonAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarActionPerformed

        if(!esNumero(jTextField8.getText()))
        {
            JOptionPane.showMessageDialog(null, "Dato de telefono no numerico o mal digitado");
        }
        else if(!jTextField1.getText().equals("") && !jTextField2.getText().equals("") &&  !jTextField6.getText().equals("") && !jTextField8.getText().equals(""))
        {
            ControladorPasajero cp = new ControladorPasajero(fabrica);
            Pasajero pasajero = new Pasajero();
            pasajero.setIdPasajero(jTextField1.getText());
            pasajero.setNombrePasajero( jTextField2.getText());
            pasajero.setEstratoPasajero(Integer.parseInt(jComboBox2.getSelectedItem()+""));
            pasajero.setGeneroPasajero( jComboBox1.getSelectedItem()+"");
            pasajero.setDireccionPasajero( jTextField6.getText());
            pasajero.setTelefonoPasajero(jTextField8.getText());

            cp.modificar(pasajero);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Por favor llene los campos vacios");
        }

    }//GEN-LAST:event_botonAgregarActionPerformed

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
    private javax.swing.JButton botonAgregar;
    private javax.swing.JButton botonSalir;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JLabel labelTitulo;
    // End of variables declaration//GEN-END:variables
}
