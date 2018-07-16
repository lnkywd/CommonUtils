package common.test.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author wd
 * @date 2018/05/28
 * Email 18842602830@163.com
 * Description
 */

@Entity
public class TestModel {

    @Id
    private Long id;
    private String name;
    private int sex;
    private int age;



    @Generated(hash = 838560601)
    public TestModel(Long id, String name, int sex, int age) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
    }


    @Generated(hash = 1568142977)
    public TestModel() {
    }



    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return this.name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public int getSex() {
        return this.sex;
    }


    public void setSex(int sex) {
        this.sex = sex;
    }


    public int getAge() {
        return this.age;
    }


    public void setAge(int age) {
        this.age = age;
    }
}
