/**
 * Copyright (c) 2017 The Semux Developers
 *
 * Distributed under the MIT software license, see the accompanying file
 * LICENSE or https://opensource.org/licenses/mit-license.php
 */
package org.semux.gui.dialog;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import org.semux.Kernel;
import org.semux.core.Wallet;
import org.semux.gui.Action;
import org.semux.message.GUIMessages;
import org.semux.gui.SwingUtil;

public class ChangePasswordDialog extends JDialog implements ActionListener {

    public ChangePasswordDialog(JFrame parent) {
        super(parent, GUIMessages.get("ChangePassword"));

        JLabel lblOldPassword = new JLabel(GUIMessages.get("OldPassword") + ":");
        JLabel lblPassword = new JLabel(GUIMessages.get("Password") + ":");
        JLabel lblRepeat = new JLabel(GUIMessages.get("RepeatPassword") + ":");

        oldpasswordField = new JPasswordField();
        passwordField = new JPasswordField();
        repeatField = new JPasswordField();

        JButton btnOk = SwingUtil.createDefaultButton(GUIMessages.get("OK"), this, Action.OK);

        JButton btnCancel = SwingUtil.createDefaultButton(GUIMessages.get("Cancel"), this, Action.CANCEL);

        // @formatter:off
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addGap(20)
                    .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                        .addComponent(lblPassword)
                        .addComponent(lblOldPassword)
                        .addComponent(lblRepeat))
                    .addGap(18)
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                            .addComponent(btnCancel)
                            .addGap(18)
                            .addComponent(btnOk))
                        .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                            .addComponent(repeatField, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                            .addComponent(passwordField, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                            .addComponent(oldpasswordField, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)))
                    .addGap(23))
        );
        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addGap(32)
                    .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblOldPassword)
                        .addComponent(oldpasswordField, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                    .addGap(18)
                    .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblPassword)
                        .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                    .addGap(18)
                    .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblRepeat)
                        .addComponent(repeatField, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                    .addGap(18)
                    .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(btnCancel)
                        .addComponent(btnOk))
                    .addContainerGap(77, Short.MAX_VALUE))
        );
        getContentPane().setLayout(groupLayout);
        // @formatter:on

        this.setModal(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setIconImage(SwingUtil.loadImage("logo", 128, 128).getImage());
        this.setMinimumSize(new Dimension(400, 240));
        this.setLocationRelativeTo(parent);
    }

    private static final long serialVersionUID = 1L;
    private JPasswordField oldpasswordField;
    private JPasswordField passwordField;
    private JPasswordField repeatField;

    @Override
    public void actionPerformed(ActionEvent e) {
        Action action = Action.valueOf(e.getActionCommand());

        switch (action) {
        case OK: {
            String oldPassword = new String(oldpasswordField.getPassword());
            String password = new String(passwordField.getPassword());
            String repeat = new String(repeatField.getPassword());

            Kernel kernel = Kernel.getInstance();
            Wallet wallet = kernel.getWallet();

            if (!password.equals(repeat)) {
                JOptionPane.showMessageDialog(this, GUIMessages.get("RepeatPasswordError"));
            } else if (!wallet.getPassword().equals(oldPassword)) {
                JOptionPane.showMessageDialog(this, GUIMessages.get("IncorrectPassword"));
            } else {
                wallet.changePassword(password);
                wallet.flush();
                JOptionPane.showMessageDialog(this, GUIMessages.get("PasswordChanged"));
                this.dispose();
            }
            break;
        }
        case CANCEL: {
            this.dispose();
            break;
        }
        default:
            break;
        }
    }
}
