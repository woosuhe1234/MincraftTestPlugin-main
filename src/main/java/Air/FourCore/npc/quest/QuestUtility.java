package Air.FourCore.npc.quest;

import Air.FourCore.user.UserData;

public class QuestUtility {
    public static void strToClass(UserData data, String str, int amount) {
        switch (str) {
            case "DailyQuest1":
                new DailyQuest1(data).current = amount;
                break;
            case "DailyQuest2":
                new DailyQuest2(data).current = amount;
                break;
            case "DungeonQuest1":
                new DungeonQuest1(data).current = amount;
                break;
            case "DungeonQuest2":
                new DungeonQuest2(data).current = amount;
                break;
            case "DungeonQuest3":
                new DungeonQuest3(data).current = amount;
                break;
            case "DungeonQuest4":
                new DungeonQuest4(data).current = amount;
                break;
            case "DungeonQuest5":
                new DungeonQuest5(data).current = amount;
                break;
            case "DungeonQuest6":
                new DungeonQuest6(data).current = amount;
                break;
            case "DungeonQuest7":
                new DungeonQuest7(data).current = amount;
                break;
            case "DungeonQuest8":
                new DungeonQuest8(data).current = amount;
                break;
            case "DungeonRepeatQuest6":
                new DungeonRepeatQuest6(data).current = amount;
                break;
            case "DungeonRepeatQuest7":
                new DungeonRepeatQuest7(data).current = amount;
                break;
            case "DungeonRepeatQuest8":
                new DungeonRepeatQuest8(data).current = amount;
                break;
            default:
                break;
        }
    }
}
