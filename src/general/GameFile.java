package general;

import general.interfaces.IGameFile;

public class GameFile implements IGameFile{

    private String myGameName;
    private String myXMLData;

    public GameFile(){

    }

    public GameFile(String gameName, String XMLData){
        this.myGameName = gameName;
        this.myXMLData = XMLData;
    }

    @Override
    public String getGameName(){
        return myGameName;
    }

    @Override
    public String getGameData(){
        return myXMLData;
    }
}
