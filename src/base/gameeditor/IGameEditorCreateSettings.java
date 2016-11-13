package design.gameeditor;

import com.sun.prism.Image;

import javafx.scene.media.Media;

public interface IGameEditorCreateSettings {
	void setBGM(Media musicfile);
	void setBackgroundImage(Image image);
}
