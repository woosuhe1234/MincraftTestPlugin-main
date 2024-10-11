package Air.FourCore.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UserRecord {
    // on DB
    public List<Boolean> dungeonQuestClear = new ArrayList<>(Arrays.asList(new Boolean[8]));
    public UserRecord(){
        Collections.fill(dungeonQuestClear, false);
    }
}
