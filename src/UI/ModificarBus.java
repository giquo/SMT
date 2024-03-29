/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Controladores.ControladorBus;
import Controladores.ControladorRuta;
import Controladores.FabricaObjetos;
import Entidades.Bus;
import Entidades.Ruta;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Paradise
 */
public class ModificarBus extends javax.swing.JFrame {
private static FabricaObjetos fabrica;
private Component uiParent;
    /**
     * Creates new form AgregarBus
     */
    public ModificarBus(JFrame parent, FabricaObjetos fabricaObj) {
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

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        botonSalir = new javax.swing.JButton();
        botonModificar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Modificar Bus");

        jPanel1.setLayout(new java.awt.GridLayout(8, 2, 15, 15));

        jLabel2.setText("Id:");
        jPanel1.add(jLabel2);
        jPanel1.add(jTextField1);

        jLabel3.setText("Tipo:");
        jPanel1.add(jLabel3);
        jPanel1.add(jTextField2);

        jLabel4.setText("Carroceria:");
        jPanel1.add(jLabel4);
        jPanel1.add(jTextField3);

        jLabel5.setText("Motor:");
        jPanel1.add(jLabel5);
        jPanel1.add(jTextField4);

        jLabel6.setText("Capacidad Parados:");
        jPanel1.add(jLabel6);

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField5);

        jLabel7.setText("Capacidad Sillas:");
        jPanel1.add(jLabel7);
        jPanel1.add(jTextField6);

        jLabel8.setText("Ruta Asignada:");
        jPanel1.add(jLabel8);
        jPanel1.add(jTextField7);

        botonSalir.setText("Salir");
        botonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalirActionPerformed(evt);
            }
        });
        jPanel1.add(botonSalir);

        botonModificar.setText("Modificar");
        botonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonModificarActionPerformed(evt);
            }
        });
        jPanel1.add(botonModificar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(127, 127, 127))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void botonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalirActionPerformed
        uiParent.setVisible(true);
        this.dispose();        
    }//GEN-LAST:event_botonSalirActionPerformed

    private void botonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonModificarActionPerformed

    if(!jTextField1.getText().equals("") && !jTextField2.getText().equals("") && !jTextField3.getText().equals("") && !jTextField4.getText().equals("") && !jTextField5.getText().equals("") && !jTextField6.getText().equals("") && !jTextField7.getText().equals(""))    
    {
        ControladorBus cb = new ControladorBus(fabrica);
        ControladorRuta cr = new ControladorRuta(fabrica);
        
        Bus bus = new Bus();
        Ruta ruta = cr.consultar(jTextField7.getText()); 
        
        bus.setIdBus( jTextField1.getText());
        bus.setTipoBus( jTextField2.getText());
        bus.setCarroceria( jTextField3.getText());
        bus.setMotor( jTextField4.getText());
        bus.setCapacidadParados(Integer.parseInt(jTextField5.getText()));
        bus.setCapacidadSillas(Integer.parseInt(jTextField6.getText()));
        bus.setRutaAsignada(ruta);
        
        cb.modificar(bus);
    }
    else
    {
        JOptionPane.showMessageDialog(null, "Por favor llene los campos vacios");
    }
        
    }//GEN-LAST:event_botonModificarActionPerformed

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
            java.util.logging.Logger.getLogger(ModificarBus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ModificarBus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ModificarBus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ModificarBus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
  */      //</editor-fold>

        /* Create and display the form */
 /*       java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ModificarBus(fabrica).setVisible(true);
            }
        });
    }
*/
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonModificar;
    private javax.swing.JButton botonSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables
}
