package sample.GameChess.PlayGame.Controllers;

import javafx.scene.control.Skin;
import javafx.scene.control.SkinBase;

class GameControlSkin extends SkinBase<GameControl> implements Skin<GameControl> {
	public GameControlSkin(GameControl gameControl) {
		super(gameControl);
	}
}
