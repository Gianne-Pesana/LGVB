
import com.leshka_and_friends.lgvb.view.components.buttons.SidebarButtonPanel;
import java.awt.Color;
import javax.swing.UIManager;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author giann
 */
public class MenuItemButton extends SidebarButtonPanel {

    private final boolean paintClick;

    public MenuItemButton(String text, String svgPath, boolean paintClick) {
        super(text, svgPath);
        this.paintClick = paintClick;
    }

    @Override
    protected void applyCurrentStyle() {
        Color bg;

        if (selected && paintClick) {
            bg = UIManager.getColor("MenuItem.selectedBackground");
        } else if (!selected && hovered) {
            bg = UIManager.getColor("MenuItem.hoverBackground");
        } else {
            bg = UIManager.getColor("LGVB.primary");
        }

        setBackground(bg);
        repaint();
    }

    @Override
    protected void handleClick() {
        if (paintClick) {
            selected = true;   // set selected immediately
            hovered = false;   // disable hover while selected
        }
    }
}
