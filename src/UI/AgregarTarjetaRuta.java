/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Controladores.ControladorEmpleado;
import Controladores.ControladorTarjetaSeUsaEnEstacion;
import Controladores.ControladorTarjetaSeUsaEnRuta;
import Controladores.FabricaObjetos;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Paradise
 */
public class AgregarTarjetaRuta extends javax.swing.JFrame {
    private final FabricaObjetos fabrica;
    private final JFrame uiParent;

    /**
     * Creates new form AgregarEmpleado
     */
    public AgregarTarjetaRuta(JFrame parent, FabricaObjetos fabricaObj) {
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
        jLabel4 = new javax.swing.JLabel();
        jDateChooserFechaNac = new com.toedter.calendar.JDateChooser();
        botonSalir = new javax.swing.JButton();
        botonAgregar = new javax.swing.JButton();
        labelTitulo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridLayout(4, 2, 15, 15));

        jLabel2.setText("Pin Tarjeta:");
        jPanel1.add(jLabel2);
        jPanel1.add(jTextField1);

        jLabel3.setText("Id Ruta:");
        jPanel1.add(jLabel3);
        jPanel1.add(jTextField2);

        jLabel4.setText("Fecha:");
        jPanel1.add(jLabel4);
        jPanel1.add(jDateChooserFechaNac);

        botonSalir.setText("Salir");
        botonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalirActionPerformed(evt);
            }
        });
        jPanel1.add(botonSalir);

        botonAgregar.setText("Agregar");
        botonAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarActionPerformed(evt);
            }
        });
        jPanel1.add(botonAgregar);

        labelTitulo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTitulo.setText("Agregar Tarjeta Usada en Ruta");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57))
            .addGroup(layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(labelTitulo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
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

        if(!esNumero(jTextField1.getText()))
        {
            JOptionPane.showMessageDialog(null, "Dato no numerico o mal digitado");
        }
        else if(!jTextField1.getText().equals("") && !jTextField2.getText().equals(""))
        {
            ControladorTarjetaSeUsaEnRuta ce = new ControladorTarjetaSeUsaEnRuta(fabrica);
            int pin = Integer.parseInt(jTextField1.getText());
            String ruta = jTextField2.getText();
            
            ce.insertar(ruta, pin, jDateChooserFechaNac.getDate());
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
    private com.toedter.calendar.JDateChooser jDateChooserFechaNac;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel labelTitulo;
    // End of variables declaration//GEN-END:variables
}
