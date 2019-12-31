import lombok.Data;

/**
 * @Description
 * @Author linminfeng
 * @Date 2019-12-24 09:16
 */
@Data
public class LuckyCard{
    private String cardType;
    private Integer cardNum;
    LuckyCard(){

    }
    LuckyCard(String cardType,Integer cardNum){
        this.cardType=cardType;
        this.cardNum=cardNum;
    }
}
