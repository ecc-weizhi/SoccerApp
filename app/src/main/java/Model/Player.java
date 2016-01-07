package Model;

public class Player {
    public static final int NOT_FIELDED = -1;

    private int id;              // unique id representing player
    private String name;
    private int jerseyNumber;
    private int fieldPosition;   // is fielded as player # on the field

    public Player(int id, String name, int jerseyNumber){
        this.id = id;
        this.name = name;
        this.jerseyNumber = jerseyNumber;
        fieldPosition = NOT_FIELDED;
    }

    public Player(int id, String name, int jerseyNumber, int position){
        this.id = id;
        this.name = name;
        this.jerseyNumber = jerseyNumber;
        fieldPosition = position;
    }

    public void setId(int id){this.id = id;}
    public int getId(){return id;}
    public void setName(String name){this.name = name;}
    public String getName(){return name;}
    public void setJerseyNumber(int jerseyNumber){this.jerseyNumber = jerseyNumber;}
    public int getJerseyNumber(){return jerseyNumber;}
    public void setFieldPosition(int position){
        fieldPosition = position;}
    public int getFieldPosition(){return fieldPosition;}
}
