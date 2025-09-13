package com.leshka_and_friends.lgvb.view.factories;

import com.leshka_and_friends.lgvb.view.themes.LGVBDark;
import com.leshka_and_friends.lgvb.view.themes.LGVBLight;
import com.leshka_and_friends.lgvb.view.themes.Theme;
import javax.swing.UIManager;

/**
 * Factory for applying LookAndFeel based on Theme enumeration.
 */
public final class LookAndFeelFactory {

	private LookAndFeelFactory() { }

	public static void apply(Theme theme) throws Exception {
		switch (theme) {
			case DARK:
				UIManager.setLookAndFeel(new LGVBDark());
				break;
			case LIGHT:
				UIManager.setLookAndFeel(new LGVBLight());
				break;
			default:
				throw new IllegalArgumentException("Unknown theme: " + theme);
		}
	}
}


