package cn.ayeez.javabean;


import java.util.Date;

/**
 * 账单明细的类
 * 里面存放账单的属性，就是一条账单应该有的信息
 * 到时候可以一条记录一个对象，然后存到集合里
 * 用private来保护，有get set方法,有构造方法
 *
 * 然后写一些统计信息
 * 比如说总金额
 * 本月收入支出
 * 本年收入支出
 */
public class AccountingData {
    private String name;
    private double price;
    private Date date;
    private String type;
    private String category;

    public AccountingData(String name, double price, Date date, String type, String category) {
        this.name = name;
        this.price = price;
        this.date = date;
        this.type = type;
        this.category = category;
    }

    public AccountingData(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "AccountingData{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", date=" + date +
                ", type='" + type + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
