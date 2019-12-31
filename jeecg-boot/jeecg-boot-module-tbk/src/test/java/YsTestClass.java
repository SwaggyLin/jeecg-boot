import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Description
 * @Author linminfeng
 * @Date 2019-12-12 12:51
 */
public class YsTestClass {
    /**
     *
     * @param startFloor 起始层
     * @param destinyFloor 目标层
     * @param floors boss层
     * @return
     */
    public static int getDestinyFloor(int startFloor,int destinyFloor, List<Integer> floors){
        ArrayList<Integer> bossFloors=new ArrayList<Integer>(Arrays.asList(new Integer[floors.size()]));
        Collections.copy(bossFloors, floors);
        //将目标层加入boss层数组并排序
        if (bossFloors.contains(destinyFloor)){

        }else{
            bossFloors.add(destinyFloor);
            Collections.sort(bossFloors);//排序
        }
        int nextFloor=startFloor+3;//每次跳3层
        //遍历数组
        for (int i=0;i<bossFloors.size();i++){
            //如果起始层小,跳3层大,就返回boss层/目标层
            if (startFloor<bossFloors.get(i)&&nextFloor>bossFloors.get(i)){
                return bossFloors.get(i);
            }
            //如果跳完三层还是小,则返回跳完3层的层数
            if (nextFloor<=bossFloors.get(i)){
                return nextFloor;
            }
        }
        return nextFloor;
    }
    public static void main(String[] args) {
        Integer startFloor=1;
        Integer destinyFloor=32;
        List<Integer> bossFloors=new ArrayList<Integer>();
        bossFloors.add(6);
        bossFloors.add(7);
        bossFloors.add(15);
        bossFloors.add(24);
        bossFloors.add(30);
        System.out.println("起始层:"+startFloor+"层");
        System.out.println("目标层:"+destinyFloor+"层");
        System.out.println("开始跳了......");
        while (startFloor!=destinyFloor){
            startFloor=YsTestClass.getDestinyFloor(startFloor,destinyFloor,bossFloors);
            System.out.print("现在在"+startFloor+"层,");
            System.out.println(bossFloors.contains(startFloor)?"boss层":startFloor==destinyFloor?"目标层":"普通层");
        }
    }
}
