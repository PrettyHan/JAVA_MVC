package eCommerce.Controller;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import eCommerce.DTO.Log102DTO;
import eCommerce.DTO.Rec101DTO;
import eCommerce.Entity.AdminEntity;
import eCommerce.Entity.OrderEntity;
import eCommerce.Entity.OrderListEntity;
import eCommerce.Service.AdminService;

@Controller
public class AdminController {
  private static Integer maxNum;
  private static Integer endPage;
  private static Integer curretPage;

  private final AdminService adminService;

  @Autowired
  AdminController(AdminService adminService) {
    this.adminService = adminService;
  }

  @GetMapping("/menu")
  public ModelAndView showMenu(HttpSession session) {
    session.removeAttribute("searchQuery");
    session.removeAttribute("endPage");

    return new ModelAndView("MEN102");
  }

  @GetMapping("/login")
  public ModelAndView loginForm(HttpSession session) {
    return new ModelAndView("LOG102");
  }

  @PostMapping("/login")
  public ModelAndView loginAdmin(@Valid Log102DTO log102DTO, BindingResult error,
      HttpSession session, HttpServletRequest request) {
    if (error.hasErrors()) {
      return new ModelAndView("LOG102");
    }

    try {
      Integer MEMBER_NO = Integer.valueOf(log102DTO.getMember_no());
      String PASSWORD = log102DTO.getPassword();

      AdminEntity admin = adminService.loginAdmin(MEMBER_NO, PASSWORD);

      if (admin == null) {
        String message = "入力した会員NOがございません。";
        return new ModelAndView("LOG102", "error", message);
      }

      if (!PASSWORD.equals(admin.getPASSWORD())) {
        String message = "パスワードが間違っています。";

        return new ModelAndView("LOG102", "error", message);
      }

      session.setAttribute("admin", admin);

    } catch (IndexOutOfBoundsException e) {
      String message = "入力した会員NOがございません。";
      return new ModelAndView("LOG102", "error", message);
    } catch (SQLException e) {
      e.printStackTrace();
      return new ModelAndView("ERR101");
    } catch (Exception e) {
      e.printStackTrace();
      return new ModelAndView("ERR101");
    }

    return new ModelAndView("redirect:/menu.html");
  }

  @GetMapping("/logout")
  public ModelAndView logout(HttpSession session) {
    session.invalidate();
    return new ModelAndView("redirect:/menu.html");
  }

  @GetMapping("/showOrderList")
  public ModelAndView showOrderList(HttpSession session) {
    session.removeAttribute("searchQuery");
    session.removeAttribute("endPage");
    try {
      maxNum = adminService.findMaxNum();
      endPage = (maxNum / 10);
      if (endPage == 0) {
        endPage = 1;
      }
      if (endPage % 10 > 0) {
        endPage += 1;
      }
      curretPage = 1;
    } catch (SQLException e) {
      e.printStackTrace();
      return new ModelAndView("ERR101");
    } catch (Exception e) {
      e.printStackTrace();
      return new ModelAndView("ERR101");
    }
    session.setAttribute("endPage", endPage);
    session.removeAttribute("searchQuery");

    ModelAndView amv = new ModelAndView("REC101");

    amv.addObject("nextPage", 10);
    amv.addObject("returnPage", 0);
    amv.addObject("currentPage", curretPage);

    return amv;
  }

  @GetMapping("/resultOrderList")
  public ModelAndView resultOrderList(@Valid Rec101DTO rec101DTO, BindingResult errors,
      HttpSession session) {
    if (errors.hasErrors()) {
      return new ModelAndView("REC101");
    }

    try {
      Date START_ORDER_DATE = null;
      Date END_ORDER_DATE = null;
      List<Date> dateList = dateFormat(rec101DTO);
      if (dateList.size() != 0) {
        START_ORDER_DATE = dateList.get(0);
        END_ORDER_DATE = dateList.get(1);
      }
      Integer PAGE = 0;
      String MEMBER_NO = rec101DTO.getMember_no();
      String MEMBER_NAME = rec101DTO.getMember_name();
      String LOWER_LIMIT = rec101DTO.getLower_limit();
      String UPPER_LIMIT = rec101DTO.getUpper_limit();
      List<OrderEntity> orderList = adminService.searchOrder(PAGE, MEMBER_NO, MEMBER_NAME,
          START_ORDER_DATE, END_ORDER_DATE, LOWER_LIMIT, UPPER_LIMIT);
      List<OrderEntity> orderListPage = adminService.searchPage(PAGE, MEMBER_NO, MEMBER_NAME,
          START_ORDER_DATE, END_ORDER_DATE, LOWER_LIMIT, UPPER_LIMIT);

      curretPage = 1;
      endPage = (orderListPage.size() / 10);
      if (endPage == 0) {
        endPage = 1;
      }
      if (endPage % 10 > 0 && orderListPage.size() > 10) {
        endPage += 1;
      }

      ModelAndView amv = new ModelAndView("REC101", "orderList", orderList);
      session.setAttribute("searchQuery", rec101DTO);
      session.setAttribute("endPage", endPage);

      amv.addObject("nextPage", 10);
      amv.addObject("returnPage", 0);
      amv.addObject("currentPage", curretPage);

      return amv;

    } catch (SQLException e) {
      e.printStackTrace();
      return new ModelAndView("ERR101");

    } catch (Exception e) {
      e.printStackTrace();
      return new ModelAndView("ERR101");
    }
  }

  @GetMapping("/showOrder")
  public ModelAndView showOrder(@RequestParam String ORDER_NO) {
    if (ORDER_NO == null && ORDER_NO.isEmpty()) {
      String message = "確認する明細がありません";
      new ModelAndView("ERR101", "error", message);
    }

    try {
      OrderEntity orderEntity = adminService.findOrderByNum(ORDER_NO);
      List<OrderListEntity> orderListEntity = adminService.findOrderListByNum(ORDER_NO);

      ModelAndView amv = new ModelAndView("REC102", "orderInfo", orderEntity);
      amv.addObject("orderListInfo", orderListEntity);
      return amv;

    } catch (SQLException e) {
      e.printStackTrace();
      return new ModelAndView("ERR101");
    } catch (Exception e) {
      e.printStackTrace();
      return new ModelAndView("ERR101");
    }
  }

  @GetMapping("/deleteOrder")
  public ModelAndView showOrderInfo(@RequestParam String ORDER_NO) {
    if (ORDER_NO == null && ORDER_NO.isEmpty()) {
      String message = "削除する明細がありません";
      new ModelAndView("ERR101", "error", message);
    }

    try {
      OrderEntity orderEntity = adminService.findOrderByNum(ORDER_NO);
      List<OrderListEntity> orderListEntity = adminService.findOrderListByNum(ORDER_NO);

      ModelAndView amv = new ModelAndView("REC201", "orderInfo", orderEntity);
      amv.addObject("orderListInfo", orderListEntity);
      return amv;

    } catch (SQLException e) {
      e.printStackTrace();
      return new ModelAndView("ERR101");
    } catch (Exception e) {
      e.printStackTrace();
      return new ModelAndView("ERR101");
    }
  }

  @PostMapping("/deleteOrder")
  public ModelAndView deleteOrder(@RequestParam String ORDER_NO) {
    if (ORDER_NO == null && ORDER_NO.isEmpty()) {
      String message = "削除する明細がありません";
      new ModelAndView("ERR101", "error", message);
    }

    try {
      adminService.deleteOrder(ORDER_NO);

    } catch (SQLException e) {
      e.printStackTrace();
      return new ModelAndView("ERR101");
    } catch (Exception e) {
      e.printStackTrace();
      return new ModelAndView("ERR101");
    }
    return new ModelAndView("REC202");

  }

  @GetMapping("/next")
  public ModelAndView showNextPage(@Valid Rec101DTO rec101DTO, BindingResult errors,
      @RequestParam(name = "nextPage") int PAGE, HttpSession session) {
    if (errors.hasErrors()) {
      return new ModelAndView("REC101");
    }

    try {
      if ((@Valid Rec101DTO) session.getAttribute("searchQuery") != null) {
        rec101DTO = (@Valid Rec101DTO) session.getAttribute("searchQuery");
      }

      Date START_ORDER_DATE = null;
      Date END_ORDER_DATE = null;
      List<Date> dateList = dateFormat(rec101DTO);
      if (dateList.size() != 0) {
        START_ORDER_DATE = dateList.get(0);
        END_ORDER_DATE = dateList.get(1);
      }

      String MEMBER_NO = rec101DTO.getMember_no();
      String MEMBER_NAME = rec101DTO.getMember_name();
      String LOWER_LIMIT = rec101DTO.getLower_limit();
      String UPPER_LIMIT = rec101DTO.getUpper_limit();

      List<OrderEntity> orderList = adminService.searchOrder(PAGE, MEMBER_NO, MEMBER_NAME,
          START_ORDER_DATE, END_ORDER_DATE, LOWER_LIMIT, UPPER_LIMIT);

      ModelAndView amv = new ModelAndView("REC101", "orderList", orderList);

      curretPage += 1;

      amv.addObject("rec101DTO", rec101DTO);
      amv.addObject("nextPage", PAGE + 10);
      amv.addObject("returnPage", PAGE - 10);
      amv.addObject("currentPage", curretPage);
      return amv;
    } catch (SQLException e) {
      e.printStackTrace();
      return new ModelAndView("ERR101");
    } catch (Exception e) {
      e.printStackTrace();
      return new ModelAndView("ERR101");
    }
  }

  @GetMapping("/return")
  public ModelAndView showReturnPage(@Valid Rec101DTO rec101DTO, BindingResult errors,
      @RequestParam(name = "returnPage") int PAGE, HttpSession session) {
    if (errors.hasErrors()) {
      return new ModelAndView("REC101");
    }
    if ((@Valid Rec101DTO) session.getAttribute("searchQuery") != null) {
      rec101DTO = (@Valid Rec101DTO) session.getAttribute("searchQuery");
    }

    try {
      Date START_ORDER_DATE = null;
      Date END_ORDER_DATE = null;
      List<Date> dateList = dateFormat(rec101DTO);
      if (dateList.size() != 0) {
        START_ORDER_DATE = dateList.get(0);
        END_ORDER_DATE = dateList.get(1);
      }

      String MEMBER_NO = rec101DTO.getMember_no();
      String MEMBER_NAME = rec101DTO.getMember_name();
      String LOWER_LIMIT = rec101DTO.getLower_limit();
      String UPPER_LIMIT = rec101DTO.getUpper_limit();

      List<OrderEntity> orderList = adminService.searchOrder(PAGE, MEMBER_NO, MEMBER_NAME,
          START_ORDER_DATE, END_ORDER_DATE, LOWER_LIMIT, UPPER_LIMIT);
      ModelAndView amv = new ModelAndView("REC101", "orderList", orderList);
      session.setAttribute("searchQuery", rec101DTO);
      curretPage -= 1;
      if (PAGE != 0) {
        amv.addObject("nextPage", PAGE + 10);
        amv.addObject("returnPage", PAGE - 10);
        amv.addObject("currentPage", curretPage);
      } else {
        amv.addObject("nextPage", 10);
        amv.addObject("returnPage", 0);
        amv.addObject("currentPage", 1);
      }
      amv.addObject("rec101DTO", rec101DTO);
      return amv;
    } catch (SQLException e) {
      e.printStackTrace();
      return new ModelAndView("ERR101");
    } catch (Exception e) {
      e.printStackTrace();
      return new ModelAndView("ERR101");
    }
  }

  @GetMapping("/frontPage")
  public ModelAndView showFirstPage(@Valid Rec101DTO rec101DTO, BindingResult errors,
      @RequestParam(name = "returnPage") int PAGE, HttpSession session) {
    curretPage = 1;
    if (errors.hasErrors()) {
      return new ModelAndView("REC101");
    }
    try {

      if ((@Valid Rec101DTO) session.getAttribute("searchQuery") != null) {
        rec101DTO = (@Valid Rec101DTO) session.getAttribute("searchQuery");
      }
      Date START_ORDER_DATE = null;
      Date END_ORDER_DATE = null;
      List<Date> dateList = dateFormat(rec101DTO);
      if (dateList.size() != 0) {
        START_ORDER_DATE = dateList.get(0);
        END_ORDER_DATE = dateList.get(1);
      }

      String MEMBER_NO = rec101DTO.getMember_no();
      String MEMBER_NAME = rec101DTO.getMember_name();
      String LOWER_LIMIT = rec101DTO.getLower_limit();
      String UPPER_LIMIT = rec101DTO.getUpper_limit();

      PAGE = 0;

      List<OrderEntity> orderList = adminService.searchOrder(PAGE, MEMBER_NO, MEMBER_NAME,
          START_ORDER_DATE, END_ORDER_DATE, LOWER_LIMIT, UPPER_LIMIT);


      ModelAndView amv = new ModelAndView("REC101", "orderList", orderList);
      session.setAttribute("searchQuery", rec101DTO);
      session.setAttribute("endPage", endPage);

      amv.addObject("nextPage", 10);
      amv.addObject("returnPage", 0);
      amv.addObject("currentPage", curretPage);
      amv.addObject("rec101DTO", rec101DTO);

      return amv;
    } catch (SQLException e) {
      e.printStackTrace();
      return new ModelAndView("ERR101");
    } catch (Exception e) {
      e.printStackTrace();
      return new ModelAndView("ERR101");
    }
  }

  @GetMapping("/backPage")
  public ModelAndView showBackPage(@Valid Rec101DTO rec101DTO, BindingResult errors,
      @RequestParam(name = "returnPage") int PAGE, HttpSession session) {
    curretPage = endPage;
    if (errors.hasErrors()) {
      return new ModelAndView("REC101");
    }
    try {
      if ((@Valid Rec101DTO) session.getAttribute("searchQuery") != null) {
        rec101DTO = (@Valid Rec101DTO) session.getAttribute("searchQuery");
      }
      Date START_ORDER_DATE = null;
      Date END_ORDER_DATE = null;
      List<Date> dateList = dateFormat(rec101DTO);
      if (dateList.size() != 0) {
        START_ORDER_DATE = dateList.get(0);
        END_ORDER_DATE = dateList.get(1);
      }

      String MEMBER_NO = rec101DTO.getMember_no();
      String MEMBER_NAME = rec101DTO.getMember_name();
      String LOWER_LIMIT = rec101DTO.getLower_limit();
      String UPPER_LIMIT = rec101DTO.getUpper_limit();

      List<OrderEntity> orderListPage = adminService.searchPage(PAGE, MEMBER_NO, MEMBER_NAME,
          START_ORDER_DATE, END_ORDER_DATE, LOWER_LIMIT, UPPER_LIMIT);

      PAGE = orderListPage.size() - (orderListPage.size() % 10);

      List<OrderEntity> orderList = adminService.searchOrder(PAGE, MEMBER_NO, MEMBER_NAME,
          START_ORDER_DATE, END_ORDER_DATE, LOWER_LIMIT, UPPER_LIMIT);

      ModelAndView amv = new ModelAndView("REC101", "orderList", orderList);

      if (PAGE != 0) {
        amv.addObject("nextPage", PAGE + 10);
        amv.addObject("returnPage", PAGE - 10);
        amv.addObject("currentPage", curretPage);
      } else {
        amv.addObject("nextPage", 10);
        amv.addObject("returnPage", 0);
        amv.addObject("currentPage", 1);
      }
      amv.addObject("rec101DTO", rec101DTO);
      return amv;
    } catch (SQLException e) {
      e.printStackTrace();
      return new ModelAndView("ERR101");
    } catch (Exception e) {
      e.printStackTrace();
      return new ModelAndView("ERR101");
    }

  }

  private ModelAndView reSearch(Rec101DTO rec101DTO) {
    try {
      Date START_ORDER_DATE = null;
      Date END_ORDER_DATE = null;
      List<Date> dateList = dateFormat(rec101DTO);
      if (dateList.size() != 0) {
        START_ORDER_DATE = dateList.get(0);
        END_ORDER_DATE = dateList.get(1);
      }

      Integer PAGE = 0;
      String MEMBER_NO = rec101DTO.getMember_no();
      String MEMBER_NAME = rec101DTO.getMember_name();
      String LOWER_LIMIT = rec101DTO.getLower_limit();
      String UPPER_LIMIT = rec101DTO.getUpper_limit();

      List<OrderEntity> orderList = adminService.searchOrder(PAGE, MEMBER_NO, MEMBER_NAME,
          START_ORDER_DATE, END_ORDER_DATE, LOWER_LIMIT, UPPER_LIMIT);


      ModelAndView amv = new ModelAndView("REC101", "orderList", orderList);

      amv.addObject("nextPage", 10);
      amv.addObject("returnPage", 0);
      amv.addObject("currentPage", curretPage);

      return amv;

    } catch (SQLException e) {
      e.printStackTrace();
      return new ModelAndView("ERR101");

    } catch (Exception e) {
      e.printStackTrace();
      return new ModelAndView("ERR101");
    }
  }

  private List<Date> dateFormat(Rec101DTO rec101DTO) {
    List<Date> dateList = new ArrayList<Date>();

    try {
      String start_year = rec101DTO.getStart_year();
      String start_month = rec101DTO.getStart_month();
      String start_day = rec101DTO.getStart_day();
      String end_year = rec101DTO.getEnd_year();
      String end_month = rec101DTO.getEnd_month();
      String end_day = rec101DTO.getEnd_day();

      if (start_year != "" && start_month != "" && start_day != "" && start_year != null
          && start_month != null && start_day != null) {
        String startDateStr =
            start_year + "-" + String.format("%02d", Integer.parseInt(start_month)) + "-"
                + String.format("%02d", Integer.parseInt(start_day));
        LocalDate startDate =
            LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Date sqlStartDate = Date.valueOf(startDate);
        dateList.add(0, sqlStartDate);
      }

      if (end_year != "" && end_month != "" && end_day != "" && end_year != null
          && end_month != null && end_day != null) {
        String endDateStr = end_year + "-" + String.format("%02d", Integer.parseInt(end_month))
            + "-" + String.format("%02d", Integer.parseInt(end_day));
        LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Date sqlEndDate = Date.valueOf(endDate);
        if (dateList.size() == 0) {
          dateList.add(0, null);
        }
        dateList.add(1, sqlEndDate);
      }

      if (dateList.size() == 1) {
        dateList.add(1, null);
      }
    } catch (NumberFormatException e) {
      return null;
    }
    return dateList;
  }

}
