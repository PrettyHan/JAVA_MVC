package eCommerce.Repository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import eCommerce.Entity.OrderListEntity;

@Repository
public class OrderListRepositoryImpl implements OrderListRepository {

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  OrderListRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
      JdbcTemplate jdbcTemplate) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    this.jdbcTemplate = jdbcTemplate;
  }

  private RowMapper<OrderListEntity> RowMapper() {
    return (RowMapper<OrderListEntity>) (rs, rowNum) -> {
      OrderListEntity orderListEntity = new OrderListEntity();
      orderListEntity.setLIST_NO(Integer.valueOf(rs.getString("LIST_NO")));
      orderListEntity.setCOLLECT_NO(rs.getString("COLLECT_NO"));
      orderListEntity.setPRODUCT_CODE(rs.getString("PRODUCT_CODE"));
      orderListEntity.setORDER_COUNT(Integer.valueOf(rs.getString("ORDER_COUNT")));
      orderListEntity.setORDER_PRICE(rs.getObject("ORDER_PRICE", Long.class));
      orderListEntity.setMAKER(rs.getString("MAKER"));
      orderListEntity.setPRODUCT_NAME(rs.getString("PRODUCT_NAME"));

      return orderListEntity;
    };
  }

  @Override
  public List<OrderListEntity> findWithProduct(String COLLECT_NO) {
    MapSqlParameterSource param = new MapSqlParameterSource();

    String sql =
        "select * from ONLINE_ORDER_LIST as ol left outer join ONLINE_PRODUCT as p on ol.PRODUCT_CODE  = p.PRODUCT_CODE where COLLECT_NO = :COLLECT_NO";

    param.addValue("COLLECT_NO", COLLECT_NO);

    return namedParameterJdbcTemplate.query(sql, param, RowMapper());
  }
}
