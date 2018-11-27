package test;

/**
 * @创建人 yanshiqian
 * @创建时间 2018/11/28
 * @描述
 */

public class SplitTest {
    public static void main(String[] args){
        String s = "1,22,333,,555,666";
        String[] s1=s.split(",");
        System.out.println(s1.length);
        for (String s2:s1){
            System.out.println(s2);
        }
        System.out.println("----1-----");
        System.out.println(s1[3]);
        System.out.println("---2------");
        if (s1[3].equals(""))
        System.out.println("--*******--");
    }
}
