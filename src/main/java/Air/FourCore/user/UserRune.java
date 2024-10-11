package Air.FourCore.user;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import Air.FourCore.RandomSystem;

import java.util.ArrayList;
import java.util.List;

public class UserRune {
    public UserRune(UserData data){
        this.data = data;
    }

    UserData data;
    List<Integer> states_OLD = new ArrayList<>(); // 0~3
    List<Integer> values = new ArrayList<>(); // 0~2
    int[] states = new int[7];

    public enum State{
        공격력, 이동속도, 체력, 방어력, 경험치, 농작물판매, 광물판매
    }

    public void setStates(){
        Inventory inventory = BagData.getRune(data.uuid);

        states = new int[7];
        for (int i=0; i<7; i++) {
            states[i] = 0;
        }
        for(ItemStack itemStack : inventory.getContents()){
            if(itemStack == null)
                continue;
            setState(itemStack.getItemMeta().getLore().get(0));
        }

        data.updateStatus();
    }

    private void setState(String str){
        String[] arr = str.split(" \\+ ");
        int index = -1;
        for (State state : State.values()){
            if(arr[0].contains(state.name())){
                index = state.ordinal();
                break;
            }
        }
        if(index == -1) return;
        states[index] += Integer.parseInt(arr[1].replace("%", "").trim());
    }

    public int getState(State state){
        int ordinal = state.ordinal();
        return states[ordinal];
    }

    public static int getRune(int ordinal){
        switch (ordinal){
            case 1: return 1;
            case 2: return 50;
            case 3: return 70;
            default: return 0;
        }
    }

    public static String getRandomString(int level){
        int rand = RandomSystem.random.nextInt(State.values().length);
        State state = State.values()[rand];
        String result = state.toString();

        result += " + " + getValue(state, level - 1);
        if(state == State.이동속도 ||
                state == State.경험치 ||
                state == State.농작물판매 ||
                state == State.광물판매)
            result += "%";

        return result;
    }

        /*
    public String getName(int index){
        String result = State.values()[states.get(index)].name() + " + " + getValue(index);
        if(states.get(index) == State.이동속도.ordinal() ||
                states.get(index) == State.경험치.ordinal() ||
                states.get(index) == State.농작물판매.ordinal() ||
                states.get(index) == State.광물판매.ordinal())
            result += "%";
        return result;
    }
         */

    private static int getValue(State state, int index){
        switch (state){
            case 공격력:
                return value(index, 1, 3, 5);
            case 이동속도:
            case 경험치:
                return value(index, 1, 2, 3);
            case 체력:
                return value(index, 4, 7, 10);
            case 방어력:
                return value(index, 2, 3, 4);
            case 농작물판매:
            case 광물판매:
                return value(index, 3, 4, 5);
            default:
                return 0;
        }
    }

    private static int value(int index, int... args){
        return args[index];
    }

    public int getRandomValue(){
        int rand = RandomSystem.random.nextInt(20);
        if(rand == 0)
            return 2;
        if(rand <= 5)
            return 1;
        else
            return 0;
    }

    public void reRole(){
        for(int i = 0; i < states_OLD.size(); i++){
            states_OLD.set(i, RandomSystem.random.nextInt(State.values().length));
        }
        for(int i = 0; i < values.size(); i++){
            values.set(i, getRandomValue());
        }
        data.updateStatus();
    }
}
