package entity.order;

/**
 * 订单状态枚举类
 * 订单状态：0 - 待确认订单，1 - 已确认订单，2 - 已取消订单，-1 - 失效订单（状态异常的订单：如扣库存失败）
 */
public enum OrderStatus {
    UNCONFIRMED("待确认订单"), CONFIRMED("已确认订单"), CANCLED("已取消订单"), INVALID("失效订单");

    private String name;
    //why "Modifier 'public' not allowed for enum constructor"
    //Think of Enums as a class with a finite number of instances. There can never be any different instances beside the ones you initially declare.
    //Thus, you cannot have a public or protected constructor, because that would allow more instances to be created.
    //Note: this is probably not the official reason. But it makes the most sense for me to think of enums this way.
    OrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "OrderStatus{" +
                "name='" + name + '\'' +
                '}';
    }
}
