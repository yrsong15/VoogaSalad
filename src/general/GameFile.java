package general;

public class GameFile {

    private String myGameName;
    private String myXMLData;

    public GameFile(){

    }

    public GameFile(String gameName, String XMLData){
        this.myGameName = gameName;
        this.myXMLData = XMLData;
    }

    public String getGameName(){
        return myGameName;
    }

    public String getGameData(){
        return myXMLData;
    }
}
