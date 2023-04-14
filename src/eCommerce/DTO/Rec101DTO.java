package eCommerce.DTO;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;

public class Rec101DTO {

  @Pattern(regexp = "^[0-9]*$", message = "会員NOは半角数字で入力してください。")
  private String member_no;

  private String member_name;

  private String start_year;

  private String start_month;

  private String start_day;

  private String end_year;

  private String end_month;

  private String end_day;

  @Pattern(regexp = "[\\d]*", message = "商品価格（下限）は半角数字で入力してください")
  private String lower_limit;

  @Pattern(regexp = "[\\d]*", message = "商品価格（上限）は半角数字で入力してください")
  private String upper_limit;

  @AssertTrue(message = "商品価格（下限）は商品価格（上限）よりも小さい数値を入力してください")
  public boolean isGoodsPriceScope() {

    if (lower_limit == null || lower_limit.isEmpty()) {
      return true;
    }
    if (upper_limit == null || upper_limit.isEmpty()) {
      return true;
    }

    try {
      int startPrice = Integer.parseInt(lower_limit);
      int endPrice = Integer.parseInt(upper_limit);

      if (endPrice < startPrice) {
        return false;
      }

      return true;

    } catch (NumberFormatException e) {
      return true;
    }

  }

  @AssertTrue(message = "商品価格は正の数字で入力してください。")
  public boolean isGoodsPriceScope2() {
    try {
      if (lower_limit != null && !lower_limit.isEmpty()) {
        int startPrice = Integer.parseInt(lower_limit);
        if (startPrice == 0) {
          return false;
        }
      }
      if (upper_limit != null && !upper_limit.isEmpty()) {
        int endPrice = Integer.parseInt(upper_limit);
        if (endPrice == 0) {
          return false;
        }
      }
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  @AssertTrue(message = "注文日を正しく設定してください。")
  public boolean isDateScope() {
    try {
      if (start_year != null && start_month != null && start_day != null && end_year != null
          && end_month != null && end_day != null && start_year != "" && start_month != ""
          && start_day != "" && end_year != "" && end_month != "" && end_day != "") {
        String startDateStr =
            start_year + "-" + String.format("%02d", Integer.parseInt(start_month)) + "-"
                + String.format("%02d", Integer.parseInt(start_day));
        LocalDate startDate =
            LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Date sqlStartDate = Date.valueOf(startDate);

        String endDateStr = end_year + "-" + String.format("%02d", Integer.parseInt(end_month))
            + "-" + String.format("%02d", Integer.parseInt(end_day));
        LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Date sqlEndDate = Date.valueOf(endDate);

        if (sqlStartDate.after(sqlEndDate)) {
          return false;
        }

      }
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  @AssertTrue(message = "注文日はすべて入力してください。")
  public boolean isDateStartScope() {
    if ((start_year == null || start_year.trim().isEmpty())
        && (start_month == null || start_month.trim().isEmpty())
        && (start_day == null || start_day.trim().isEmpty())) {
      return true;
    } else {
      return !(start_year == null || start_year.trim().isEmpty() || start_month == null
          || start_month.trim().isEmpty() || start_day == null || start_day.trim().isEmpty());
    }
  }

  @AssertTrue(message = "注文日はすべて入力してください。")
  public boolean isDateEndScope() {
    if ((end_year == null || end_year.trim().isEmpty())
        && (end_month == null || end_month.trim().isEmpty())
        && (end_day == null || end_day.trim().isEmpty())) {
      return true;
    } else {
      return !(end_year == null || end_year.trim().isEmpty() || end_month == null
          || end_month.trim().isEmpty() || end_day == null || end_day.trim().isEmpty());
    }
  }

  public String getMember_no() {
    return member_no;
  }

  public String getMember_name() {
    return member_name;
  }

  public String getStart_year() {
    return start_year;
  }

  public String getStart_month() {
    return start_month;
  }

  public String getStart_day() {
    return start_day;
  }

  public String getEnd_year() {
    return end_year;
  }

  public String getEnd_month() {
    return end_month;
  }

  public String getEnd_day() {
    return end_day;
  }

  public String getLower_limit() {
    return lower_limit;
  }

  public String getUpper_limit() {
    return upper_limit;
  }

  public void setMember_no(String member_no) {
    this.member_no = member_no;
  }

  public void setMember_name(String member_name) {
    this.member_name = member_name;
  }

  public void setStart_year(String start_year) {
    this.start_year = start_year;
  }

  public void setStart_month(String start_month) {
    this.start_month = start_month;
  }

  public void setStart_day(String start_day) {
    this.start_day = start_day;
  }

  public void setEnd_year(String end_year) {
    this.end_year = end_year;
  }

  public void setEnd_month(String end_month) {
    this.end_month = end_month;
  }

  public void setEnd_day(String end_day) {
    this.end_day = end_day;
  }

  public void setLower_limit(String lower_limit) {
    this.lower_limit = lower_limit;
  }

  public void setUpper_limit(String upper_limit) {
    this.upper_limit = upper_limit;
  }



}
