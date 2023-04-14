package eCommerce.Repository;

import java.util.List;
import eCommerce.Entity.OrderListEntity;

public interface OrderListRepository {

  List<OrderListEntity> findWithProduct(String ORDER_NO);

}
