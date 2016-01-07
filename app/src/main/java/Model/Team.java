package Model;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String teamName;
    private List<Player> mPlayerList;

    public Team(String teamName, List<Player> mPlayerList){
        this.teamName = teamName;
        this.mPlayerList = mPlayerList;
    }

    public static Team newDemoInstance(){
        List<Player> list = new ArrayList<Player>();
        list.add(new Player(0, "Alan", 23, 1));
        list.add(new Player(1, "Brandon", 6, 2));
        list.add(new Player(2, "Charles", 4, 3));
        list.add(new Player(3, "Dan", 11, 4));
        list.add(new Player(4, "Eden", 15, 5));
        list.add(new Player(5, "Ferran", 18, 6));
        list.add(new Player(6, "Gerald", 1, 7));
        list.add(new Player(7, "Han", 8, 8));
        list.add(new Player(8, "Ian", 9, 9));
        list.add(new Player(9, "Joseph", 14, 10));
        list.add(new Player(10, "Keegan", 17, 11));
        list.add(new Player(11, "Leonard", 22));
        list.add(new Player(12, "Michael", 31));

        return new Team("Team PlayPal", list);
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<Player> getPlayerList() {
        return mPlayerList;
    }

    public void setPlayerList(List<Player> mPlayerList) {
        this.mPlayerList = mPlayerList;
    }

    private Player findPlayerById(int id){
        for(Player p:mPlayerList){
            if(p.getId() == id){
                return p;
            }
        }

        return null;
    }

    public String findPlayerNameById(int id){
        for(Player p:mPlayerList){
            if(p.getId() == id){
                return p.getName();
            }
        }

        return null;
    }

    public int findPlayerFieldPositionById(int id){
        for(Player p: mPlayerList){
            if(p.getId() == id){
                return p.getFieldPosition();
            }
        }
        return Player.NOT_FIELDED;
    }

    public void swapPlayerFieldPosition(int firstPlayerId, int secondPlayerId){
        Player firstPlayer = findPlayerById(firstPlayerId);
        Player secondPlayer = findPlayerById(secondPlayerId);

        int firstPlayerPosition = firstPlayer.getFieldPosition();
        firstPlayer.setFieldPosition(secondPlayer.getFieldPosition());
        secondPlayer.setFieldPosition(firstPlayerPosition);
    }

}
