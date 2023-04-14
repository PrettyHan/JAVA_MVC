package eCommerce.Repository;

import java.sql.Date;
import java.util.List;
import eCommerce.Entity.OrderEntity;

public interface OrderRepository {

  void insert(Integer MEMBER_NO, Long TOTAL_MONEY, Long TOTAL_TAX, Integer COLLECT_NO);

  List<OrderEntity> findByInfo(Integer PAGE, String MEMBER_NAME, String MEMBER_NO,
      Date START_ORDER_DATE, Date END_ORDER_DATE, String LOWER_LIMIT, String UPPER_LIMIT);

  Integer findMax();

  void delete(String ORDER_NO, String COLLECT_NO);

  String findByNum(String ORDER_NO);

  List<OrderEntity> findWithMember(String ORDER_NO);

  List<OrderEntity> findPage(Integer pAGE, String mEMBER_NO, String mEMBER_NAME,
      Date sTART_ORDER_DATE, Date eND_ORDER_DATE, String lOWER_LIMIT, String uPPER_LIMIT);


}
