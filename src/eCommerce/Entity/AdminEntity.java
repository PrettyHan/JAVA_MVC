package eCommerce.Entity;

import java.sql.Date;
import java.sql.Timestamp;

public class AdminEntity {
  private Integer STAFF_NO;

  private String PASSWORD;

  private String NAME;

  private Integer AGE;

  private char SEX;

  private Date REGISTER_DATE;

  private Timestamp LAST_UPD_DATE;

  public Integer getSTAFF_NO() {
    return STAFF_NO;
  }

  public String getPASSWORD() {
    return PASSWORD;
  }

  public String getNAME() {
    return NAME;
  }

  public Integer getAGE() {
    return AGE;
  }

  public char getSEX() {
    return SEX;
  }

  public Date getREGISTER_DATE() {
    return REGISTER_DATE;
  }

  public Timestamp getLAST_UPD_DATE() {
    return LAST_UPD_DATE;
  }

  public void setSTAFF_NO(Integer sTAFF_NO) {
    STAFF_NO = sTAFF_NO;
  }

  public void setPASSWORD(String pASSWORD) {
    PASSWORD = pASSWORD;
  }

  public void setNAME(String nAME) {
    NAME = nAME;
  }

  public void setAGE(Integer aGE) {
    AGE = aGE;
  }

  public void setSEX(char sEX) {
    SEX = sEX;
  }

  public void setREGISTER_DATE(Date rEGISTER_DATE) {
    REGISTER_DATE = rEGISTER_DATE;
  }

  public void setLAST_UPD_DATE(Timestamp lAST_UPD_DATE) {
    LAST_UPD_DATE = lAST_UPD_DATE;
  }



}
