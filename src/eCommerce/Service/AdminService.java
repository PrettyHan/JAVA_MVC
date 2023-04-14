package eCommerce.Service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import eCommerce.Entity.AdminEntity;
import eCommerce.Entity.OrderEntity;
import eCommerce.Entity.OrderListEntity;

public interface AdminService {

  AdminEntity loginAdmin(Integer MEMBER_NO, String PASSWORD) throws SQLException, Exception;

  List<OrderEntity> searchOrder(Integer PAGE, String MEMBER_NO, String MEMBER_NAME,
      Date START_ORDER_DATE, Date END_ORDER_DATE, String LOWER_LIMIT, String UPPER_LIMIT)
      throws SQLException, Exception;

  Integer findMaxNum() throws SQLException, Exception;

  void deleteOrder(String ORDER_NO) throws SQLException, Exception;

  OrderEntity findOrderByNum(String ORDER_NO) throws SQLException, Exception;

  List<OrderListEntity> findOrderListByNum(String ORDER_NO) throws SQLException, Exception;

  List<OrderEntity> searchPage(Integer PAGE, String MEMBER_NO, String MEMBER_NAME,
      Date START_ORDER_DATE, Date END_ORDER_DATE, String LOWER_LIMIT, String UPPER_LIMIT)
      throws SQLException, Exception;

}
