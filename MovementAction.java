package windyday;

import windyday.blocks.Player;

import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

/*
 * Josh Coates - Movement Action
 */

public class MovementAction extends AbstractAction {
    private Player player;
    private int dx;
    private int dy;

    public MovementAction(Player player, int dx, int dy) {
        this.player = player;
        this.dx = dx;
        this.dy = dy;
    }

    public void actionPerformed(ActionEvent e) {
        player.setXVelocity(dx);
        player.setYVelocity(dy);
    }
}
