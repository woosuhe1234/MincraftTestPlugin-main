package Air.FourCore.user;

import Air.FourCore.network.CoinManager;

public class UserCoin extends UserStatus {

    public double strkCoin = 0;
    public double strkAmount = 0;
    public double strkAveragePrice = 0; // 평균단가

    public boolean buy(int amount){
        if(CoinManager.trade_price == 0) return false;
        if(strkAmount + amount > 100) return false;
        double price = CoinManager.trade_price * (1 + CoinManager.fee);
        double coin = CoinManager.nether * amount / price;

        try {
            strkAveragePrice = (strkAveragePrice * strkAmount + price * amount) / (strkAmount + amount);
        }catch (Exception e){
            strkAveragePrice = 0.;
        }
        strkAmount += amount;
        strkCoin += coin;
        return true;
    }

    public boolean sell(int amount){
        if(CoinManager.trade_price == 0) return false;
        double price = CoinManager.trade_price * (1 + CoinManager.fee);
        double coin = CoinManager.nether * amount / price;

        if (strkCoin < coin) return false;
        strkAmount *= (strkCoin - coin) / strkCoin;
        strkCoin -= coin;
        return true;
    }

    public double getYield(){
        try {
            return (CoinManager.trade_price - strkAveragePrice) / strkAveragePrice * 100;
        }catch (Exception e){
            return 0.;
        }
    }
}
