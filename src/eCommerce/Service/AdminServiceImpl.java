package eCommerce.Service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import eCommerce.Entity.AdminEntity;
import eCommerce.Entity.OrderEntity;
import eCommerce.Entity.OrderListEntity;
import eCommerce.Repository.AdminRepository;
import eCommerce.Repository.OrderListRepository;
import eCommerce.Repository.OrderRepository;

@Service
public class AdminServiceImpl implements AdminService {

  private final AdminRepository adminRepository;
  private final OrderRepository orderRepository;
  private final OrderListRepository orderListRepository;

  @Autowired
  AdminServiceImpl(AdminRepository adminRepository, OrderRepository orderRepository,
      OrderListRepository orderListRepository) {
    this.adminRepository = adminRepository;
    this.orderRepository = orderRepository;
    this.orderListRepository = orderListRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public AdminEntity loginAdmin(Integer MEMBER_NO, String PASSWORD) throws SQLException, Exception {

    return adminRepository.login(MEMBER_NO, PASSWORD);
  }

  @Override
  @Transactional(readOnly = true)
  public List<OrderEntity> searchOrder(Integer PAGE, String MEMBER_NO, String MEMBER_NAME,
      Date START_ORDER_DATE, Date END_ORDER_DATE, String LOWER_LIMIT, String UPPER_LIMIT)
      throws SQLException, Exception {

    return orderRepository.findByInfo(PAGE, MEMBER_NO, MEMBER_NAME, START_ORDER_DATE,
        END_ORDER_DATE, LOWER_LIMIT, UPPER_LIMIT);
  }

  @Override
  @Transactional(readOnly = true)
  public Integer findMaxNum() throws SQLException, Exception {

    return orderRepository.findMax();
  }

  @Override
  @Transactional(rollbackForClassName = "Exception")
  public void deleteOrder(String ORDER_NO) throws SQLException, Exception {
    String COLLECT_NO = orderRepository.findByNum(ORDER_NO);

    orderRepository.delete(ORDER_NO, COLLECT_NO);

  }

  @Override
  @Transactional(readOnly = true)
  public OrderEntity findOrderByNum(String ORDER_NO) throws SQLException, Exception {

    return orderRepository.findWithMember(ORDER_NO).get(0);
  }

  @Override
  @Transactional(readOnly = true)
  public List<OrderListEntity> findOrderListByNum(String ORDER_NO) throws SQLException, Exception {
    String COLLECT_NO = orderRepository.findByNum(ORDER_NO);

    return orderListRepository.findWithProduct(COLLECT_NO);
  }

  @Override
  @Transactional(readOnly = true)
  public List<OrderEntity> searchPage(Integer PAGE, String MEMBER_NO, String MEMBER_NAME,
      Date START_ORDER_DATE, Date END_ORDER_DATE, String LOWER_LIMIT, String UPPER_LIMIT)
      throws SQLException, Exception {

    return orderRepository.findPage(PAGE, MEMBER_NO, MEMBER_NAME, START_ORDER_DATE, END_ORDER_DATE,
        LOWER_LIMIT, UPPER_LIMIT);
  }


}
