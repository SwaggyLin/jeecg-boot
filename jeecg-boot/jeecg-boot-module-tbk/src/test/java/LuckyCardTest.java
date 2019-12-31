import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author linminfeng
 * @Date 2019-12-24 09:21
 */
public class LuckyCardTest {
    /**
     * 判断是否满足兑换条件
     * @param cardList 玩家拥有的卡
     * @param exchangeNum 兑换福卡需要的卡片数量
     * @return
     */
    public static boolean checkLucky(List<LuckyCard> cardList,Integer exchangeNum){
        //获取卡片中数量大于1的卡片类型有几种,只要大于等于exchangeNum就可以兑换
        long cardCount=cardList.stream().filter(luckyCard->luckyCard.getCardNum()>0).count();
        return cardCount>=exchangeNum;
    }
    /**
     * 兑换福卡
     * @param cardList 玩家拥有的卡
     * @param exchangeNum 兑换福卡需要的卡片数量
     * @return
     */
    public static List<LuckyCard> exchangeLuckyCard(List<LuckyCard> cardList,Integer exchangeNum){
        //降序排序
        cardList=cardList.stream().sorted(Comparator.comparing(LuckyCard::getCardNum).reversed()).collect(Collectors.toList());

        //排序前n的卡片类型各减1
        for (int i=0;i<exchangeNum;i++){
            cardList.get(i).setCardNum(cardList.get(i).getCardNum()-1);
            System.out.println("卡片"+cardList.get(i).getCardType()+"减1");
        }

        //打印兑换后玩家拥有的卡片类型及对应数量
        cardList.stream().forEach(luckyCard -> System.out.println("卡片"+luckyCard.getCardType()+"剩余"+luckyCard.getCardNum()+"张"));
        return cardList;
    }

    public static void main(String[] args) {
        List<LuckyCard> cardList=new ArrayList<>();
        LuckyCard cardA=new LuckyCard("A",5);
        LuckyCard cardB=new LuckyCard("B",1);
        LuckyCard cardC=new LuckyCard("C",0);
        LuckyCard cardD=new LuckyCard("D",2);
        LuckyCard cardE=new LuckyCard("E",4);
        cardList.add(cardA);
        cardList.add(cardB);
        cardList.add(cardC);
        cardList.add(cardD);
        cardList.add(cardE);

        //兑换福卡需要的卡的种类的数量
        Integer exchangeNum=2;

        //打印玩家初始拥有的卡片类型及对应数量
        cardList.stream().forEach(luckyCard -> System.out.println("卡片"+luckyCard.getCardType()+"剩余"+luckyCard.getCardNum()+"张"));

        //当满足兑换条件的时候才开始兑换
        while (checkLucky(cardList,exchangeNum)){
            System.out.println("满足兑换条件");
            exchangeLuckyCard(cardList,exchangeNum);
        }
    }
}
