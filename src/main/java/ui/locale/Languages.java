package ui.locale;

public enum Languages {
    ENGLISH("EN",0),
    RUSSIAN("RU",1),
    BELORUSSIAN("BY",2),
    HUNGARIAN("HUN",3);

    private final int index;
    private final String tag;
    private Languages(String tag, int index){
        this.tag = tag;
        this.index = index;
    }
    public int getIndex(){
        return index;
    }
    public static String getTag(int index){
        return Languages.values()[index].tag;
    }

    public static int getIndex(String tag){
        for (Languages lang: Languages.values()){
            if (lang.tag.equals(tag)){
                return lang.index;
            }
        }
        return -997;
    }

}
