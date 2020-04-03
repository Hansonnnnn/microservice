package cn.edu.nju.eczuul.redis;

public class SecKillKey extends BasePrefix {
    private SecKillKey(String prefix) {super(prefix);}
    public static SecKillKey isGoodsOver = new SecKillKey("go");
    public static SecKillKey getSeckillPath = new SecKillKey("mp");
}
