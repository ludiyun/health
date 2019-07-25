package Vo;

import java.io.Serializable;

public class OrderInfoVo implements Serializable {
    private String name;//体检人姓名
    private String sex;//体检人性别
    private String telephone;//手机号
    private String validateCode;//验证码
    private String idCard;//身份证号
    private String orderDate;//预约日期
    private String setmealId;//套餐id
    private String orderType;//预约类型
    private String token;//预约类型


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getSetmealId() {
        return setmealId;
    }

    public void setSetmealId(String setmealId) {
        this.setmealId = setmealId;
    }
}
