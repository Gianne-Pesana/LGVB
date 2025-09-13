package com.leshka_and_friends.lgvb.view.factories;

import com.leshka_and_friends.lgvb.view.components.panels.TitlePanel;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;
import javax.swing.*;

/**
 * Factory for creating main content panels by page.
 * Note: DashboardPanel is now created directly in MainView.
 */
public final class PanelFactory {

	private PanelFactory() { }

	/**
	 * Creates a TitlePanel with the specified title.
	 * 
	 * @param title the title to display
	 * @return a new TitlePanel
	 */
	public static TitlePanel createTitlePanel(String title) {
		return new TitlePanel(title);
	}
}


