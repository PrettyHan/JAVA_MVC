package eCommerce.Repository;

import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import eCommerce.Entity.OrderEntity;


@Repository
public class OrderRepositoryImpl implements OrderRepository {

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  OrderRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
      JdbcTemplate jdbcTemplate) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    this.jdbcTemplate = jdbcTemplate;
  }

  private RowMapper<OrderEntity> RowMapper() {
    return (RowMapper<OrderEntity>) (rs, rowNum) -> {
      OrderEntity orderEntity = new OrderEntity();
      orderEntity.setORDER_NO(Integer.valueOf(rs.getString("ORDER_NO")));
      orderEntity.setMEMBER_NO(Integer.valueOf(rs.getString("MEMBER_NO")));
      orderEntity.setTOTAL_MONEY(rs.getLong("TOTAL_MONEY"));
      orderEntity.setTOTAL_TAX(rs.getLong("TOTAL_TAX"));
      orderEntity.setORDER_DATE(rs.getDate("ORDER_DATE"));
      orderEntity.setCOLLECT_NO(rs.getString("COLLECT_NO"));
      orderEntity.setLAST_UPD_DATE(rs.getTimestamp("LAST_UPD_DATE"));

      return orderEntity;
    };
  }

  private RowMapper<OrderEntity> RowMapperByAdmin() {
    return (RowMapper<OrderEntity>) (rs, rowNum) -> {
      OrderEntity orderEntity = new OrderEntity();
      orderEntity.setORDER_NO(Integer.valueOf(rs.getString("ORDER_NO")));
      orderEntity.setMEMBER_NO(Integer.valueOf(rs.getString("MEMBER_NO")));
      orderEntity.setTEL(rs.getString("TEL"));
      orderEntity.setMEMBER_NAME(rs.getString("NAME"));
      orderEntity.setTOTAL_MONEY(rs.getLong("TOTAL_MONEY"));
      orderEntity.setTOTAL_TAX(rs.getLong("TOTAL_TAX"));
      orderEntity.setTOTAL_RESULT(rs.getLong("TOTAL_MONEY") + rs.getLong("TOTAL_TAX"));
      orderEntity.setORDER_DATE(rs.getDate("ORDER_DATE"));
      orderEntity.setCOLLECT_NO(rs.getString("COLLECT_NO"));
      orderEntity.setLAST_UPD_DATE(rs.getTimestamp("LAST_UPD_DATE"));

      return orderEntity;
    };
  }

  @Override
  public void insert(Integer MEMBER_NO, Long TOTAL_MONEY, Long TOTAL_TAX, Integer COLLECT_NO) {
    MapSqlParameterSource param = new MapSqlParameterSource();
    String sql = "insert into ONLINE_ORDER values "
        + "(:ORDER_NO, :MEMBER_NO, :TOTAL_MONEY, :TOTAL_TAX, NOW(), :COLLECT_NO, NOW())";

    param.addValue("ORDER_NO", null);
    param.addValue("MEMBER_NO", MEMBER_NO);
    param.addValue("TOTAL_MONEY", TOTAL_MONEY);
    param.addValue("TOTAL_TAX", TOTAL_TAX);
    param.addValue("COLLECT_NO", COLLECT_NO);

    namedParameterJdbcTemplate.update(sql, param);


  }

  @Override
  public List<OrderEntity> findByInfo(Integer PAGE, String MEMBER_NO, String MEMBER_NAME,
      Date START_ORDER_DATE, Date END_ORDER_DATE, String LOWER_LIMIT, String UPPER_LIMIT) {

    MapSqlParameterSource param = new MapSqlParameterSource();
    StringBuilder sb = new StringBuilder();
    sb.append(
        "select o.ORDER_NO, o.MEMBER_NO, m.NAME, o.TOTAL_MONEY, o.TOTAL_TAX, o.ORDER_DATE, o.COLLECT_NO, o.LAST_UPD_DATE, m.TEL "
            + "from ONLINE_ORDER as o left outer join ONLINE_MEMBER as m on o.MEMBER_NO = m.MEMBER_NO where 1=1");

    if (MEMBER_NO != null && MEMBER_NO != "") {
      sb.append(" and o.MEMBER_NO = :MEMBER_NO");
      param.addValue("MEMBER_NO", MEMBER_NO);
    }

    if (MEMBER_NAME != null && MEMBER_NAME != "") {
      sb.append(" and NAME like :MEMBER_NAME");
      param.addValue("MEMBER_NAME", "%" + MEMBER_NAME + "%");
    }
    if (START_ORDER_DATE != null) {
      sb.append(" and ORDER_DATE >= :START_ORDER_DATE");
      param.addValue("START_ORDER_DATE", START_ORDER_DATE);
    }
    if (END_ORDER_DATE != null) {
      sb.append(" and ORDER_DATE <= :END_ORDER_DATE");
      param.addValue("END_ORDER_DATE", END_ORDER_DATE);
    }
    if (LOWER_LIMIT != null && LOWER_LIMIT != "") {
      sb.append(" and TOTAL_MONEY + o.TOTAL_TAX >= :LOWER_LIMIT");
      param.addValue("LOWER_LIMIT", Integer.valueOf(LOWER_LIMIT));
    }
    if (UPPER_LIMIT != null && UPPER_LIMIT != "") {
      sb.append(" and TOTAL_MONEY + o.TOTAL_TAX <= :UPPER_LIMIT");
      param.addValue("UPPER_LIMIT", Integer.valueOf(UPPER_LIMIT));
    }

    sb.append(" and DELETE_FLG = 0");
    sb.append(" order by ORDER_DATE desc");
    sb.append(" limit :PAGE,10");


    param.addValue("PAGE", PAGE);

    return this.namedParameterJdbcTemplate.query(sb.toString(), param, RowMapperByAdmin());
  }

  @Override
  public Integer findMax() {
    String sql = "select COUNT(*) from ONLINE_ORDER";

    return jdbcTemplate.queryForObject(sql, Integer.class);
  }

  @Override
  public void delete(String ORDER_NO, String COLLECT_NO) {
    MapSqlParameterSource param = new MapSqlParameterSource();
    String sql =
        "delete from o, ol using ONLINE_ORDER as o left join ONLINE_ORDER_LIST as ol on o.COLLECT_NO = ol.COLLECT_NO where o.COLLECT_NO = :COLLECT_NO";

    param.addValue("COLLECT_NO", COLLECT_NO);

    namedParameterJdbcTemplate.update(sql, param);

  }

  @Override
  public String findByNum(String ORDER_NO) {
    MapSqlParameterSource param = new MapSqlParameterSource();
    String sql = "select * from ONLINE_ORDER where ORDER_NO = :ORDER_NO";

    param.addValue("ORDER_NO", ORDER_NO);


    return namedParameterJdbcTemplate.query(sql, param, RowMapper()).get(0).getCOLLECT_NO();
  }

  @Override
  public List<OrderEntity> findWithMember(String ORDER_NO) {
    MapSqlParameterSource param = new MapSqlParameterSource();
    String sql =
        "select o.ORDER_NO, o.MEMBER_NO, m.NAME, o.TOTAL_MONEY, o.TOTAL_TAX, o.ORDER_DATE, o.COLLECT_NO, o.LAST_UPD_DATE, m.TEL "
            + "from ONLINE_ORDER as o left outer join ONLINE_MEMBER as m on o.MEMBER_NO = m.MEMBER_NO where o.ORDER_NO = :ORDER_NO";

    param.addValue("ORDER_NO", ORDER_NO);

    return namedParameterJdbcTemplate.query(sql, param, RowMapperByAdmin());
  }

  @Override
  public List<OrderEntity> findPage(Integer PAGE, String MEMBER_NO, String MEMBER_NAME,
      Date START_ORDER_DATE, Date END_ORDER_DATE, String LOWER_LIMIT, String UPPER_LIMIT) {
    MapSqlParameterSource param = new MapSqlParameterSource();
    StringBuilder sb = new StringBuilder();
    sb.append(
        "select o.ORDER_NO, o.MEMBER_NO, m.NAME, o.TOTAL_MONEY, o.TOTAL_TAX, o.ORDER_DATE, o.COLLECT_NO, o.LAST_UPD_DATE, m.TEL "
            + "from ONLINE_ORDER as o left outer join ONLINE_MEMBER as m on o.MEMBER_NO = m.MEMBER_NO where 1=1");

    if (MEMBER_NO != null && MEMBER_NO != "") {
      sb.append(" and o.MEMBER_NO = :MEMBER_NO");
      param.addValue("MEMBER_NO", MEMBER_NO);
    }

    if (MEMBER_NAME != null && MEMBER_NAME != "") {
      sb.append(" and NAME like :MEMBER_NAME");
      param.addValue("MEMBER_NAME", "%" + MEMBER_NAME + "%");
    }
    if (START_ORDER_DATE != null) {
      sb.append(" and ORDER_DATE >= :START_ORDER_DATE");
      param.addValue("START_ORDER_DATE", START_ORDER_DATE);
    }
    if (END_ORDER_DATE != null) {
      sb.append(" and ORDER_DATE <= :END_ORDER_DATE");
      param.addValue("END_ORDER_DATE", END_ORDER_DATE);
    }
    if (LOWER_LIMIT != null && LOWER_LIMIT != "") {
      sb.append(" and TOTAL_MONEY + o.TOTAL_TAX >= :LOWER_LIMIT");
      param.addValue("LOWER_LIMIT", Integer.valueOf(LOWER_LIMIT));
    }
    if (UPPER_LIMIT != null && UPPER_LIMIT != "") {
      sb.append(" and TOTAL_MONEY + o.TOTAL_TAX <= :UPPER_LIMIT");
      param.addValue("UPPER_LIMIT", Integer.valueOf(UPPER_LIMIT));
    }

    sb.append(" and DELETE_FLG = 0");
    sb.append(" order by ORDER_DATE desc");

    return this.namedParameterJdbcTemplate.query(sb.toString(), param, RowMapperByAdmin());
  }
}
