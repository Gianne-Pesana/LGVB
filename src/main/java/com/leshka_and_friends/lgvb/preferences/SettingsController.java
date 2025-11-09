package com.leshka_and_friends.lgvb.preferences;

import com.leshka_and_friends.lgvb.auth.Session;
import com.leshka_and_friends.lgvb.auth.SessionManager;
import com.leshka_and_friends.lgvb.core.app.ServiceLocator;
import com.leshka_and_friends.lgvb.view.settings.SettingsDialog;

import javax.swing.*;

public class SettingsController {

    private final SettingsDialog view;
    private final PreferencesManager preferencesManager;

    public SettingsController() {
        SessionManager sessionManager = ServiceLocator.getInstance().getService(SessionManager.class);
        Session currentSession = sessionManager.getCurrentSession();
        this.view = new SettingsDialog(currentSession.getMainView());
        this.preferencesManager = currentSession.getPreferencesManager();
        initController();
    }

    private void initController() {
        // Remove default listener and add our own
        for (var actionListener : view.getSaveButton().getActionListeners()) {
            view.getSaveButton().removeActionListener(actionListener);
        }

        view.getSaveButton().addActionListener(e -> savePreferences());
    }

    public void showView() {
        loadPreferencesIntoView();
        view.setVisible(true);
    }

    private void loadPreferencesIntoView() {
        NotificationPreferences prefs = preferencesManager.getPreferences();
        view.getEmailToggle().setSelected(prefs.isEmail());
        view.getOsToggle().setSelected(prefs.isPush());
    }

    private void savePreferences() {
        preferencesManager.getPreferences().setEmail(view.getEmailToggle().isSelected());
        preferencesManager.getPreferences().setPush(view.getOsToggle().isSelected());
        preferencesManager.save();
        JOptionPane.showMessageDialog(view, "Settings saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        view.dispose();
    }
}
