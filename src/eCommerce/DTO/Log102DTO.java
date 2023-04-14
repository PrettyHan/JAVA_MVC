package eCommerce.DTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Log102DTO {

  @NotEmpty(message = "IDは必須入力です。")
  @Pattern(regexp = "^[0-9]*$", message = "会員Noは数字で入力してください。")
  private String member_no;

  @NotEmpty(message = "パスワードは必須入力です。")
  @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "パスワードは半角英数字で入力してください。")
  @Size(max = 8, message = "パスワードは8文字以内に入力してください。")
  private String password;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getMember_no() {
    return member_no;
  }

  public void setMember_no(String member_no) {
    this.member_no = member_no;
  }



}
