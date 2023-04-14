package eCommerce.Entity;

public class OrderListEntity {

  private Integer LIST_NO;

  private String COLLECT_NO;

  private String PRODUCT_CODE;

  private String PRODUCT_NAME;

  private String MAKER;

  private Integer ORDER_COUNT;

  private Long ORDER_PRICE;

  public Integer getLIST_NO() {
    return LIST_NO;
  }

  public String getCOLLECT_NO() {
    return COLLECT_NO;
  }

  public String getPRODUCT_CODE() {
    return PRODUCT_CODE;
  }

  public Integer getORDER_COUNT() {
    return ORDER_COUNT;
  }

  public Long getORDER_PRICE() {
    return ORDER_PRICE;
  }

  public void setLIST_NO(Integer lIST_NO) {
    LIST_NO = lIST_NO;
  }

  public void setCOLLECT_NO(String cOLLECT_NO) {
    COLLECT_NO = cOLLECT_NO;
  }

  public void setPRODUCT_CODE(String pRODUCT_CODE) {
    PRODUCT_CODE = pRODUCT_CODE;
  }

  public void setORDER_COUNT(Integer oRDER_COUNT) {
    ORDER_COUNT = oRDER_COUNT;
  }

  public void setORDER_PRICE(Long oRDER_PRICE) {
    ORDER_PRICE = oRDER_PRICE;
  }

  public String getPRODUCT_NAME() {
    return PRODUCT_NAME;
  }

  public void setPRODUCT_NAME(String pRODUCT_NAME) {
    PRODUCT_NAME = pRODUCT_NAME;
  }

  public String getMAKER() {
    return MAKER;
  }

  public void setMAKER(String mAKER) {
    MAKER = mAKER;
  }

}
