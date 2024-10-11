package Air.FourCore.network;

public class CoinManager {
    public static final double nether = 10000; // 네더별 개당 만원이라 침
    public static final double fee = 0.0015; // 0.15%

    public static double trade_price = 0.; // trade_price
    public static double high_price = 0.; // high_price
    public static double low_price = 0.; // low_price
    public static double opening_price = 0.; // opening_price
    public static double signed_change_price = 0.;
    public static double signed_change_rate = 0.; // signed_change_rate

    public static void updateCoin(){
        String result = "";
        try {
            result = GetRequest.sendGet("https://api.upbit.com/v1/ticker?markets=KRW-STRK");
        } catch (Exception e) {
            e.printStackTrace();
        }

        trade_price = getDouble(result, "trade_price");
        high_price = getDouble(result, "high_price");
        low_price = getDouble(result, "low_price");
        opening_price = getDouble(result, "opening_price");
        signed_change_price = getDouble(result, "signed_change_price");
        signed_change_rate = getDouble(result, "signed_change_rate");
    }

    public static double getDouble(String json, String key){
        int index = json.indexOf(key);
        int endIndex = json.indexOf(',',index);
        if(index == -1) return 0;
        String str = json.substring(index+key.length()+2, endIndex);
        return Double.parseDouble(str);
    }
}
