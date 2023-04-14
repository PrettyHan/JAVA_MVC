package eCommerce.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import eCommerce.Entity.AdminEntity;

@Repository
public class AdminRepositoryImpl implements AdminRepository {

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  AdminRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
      JdbcTemplate jdbcTemplate) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    this.jdbcTemplate = jdbcTemplate;
  }

  private RowMapper<AdminEntity> RowMapper() {
    return (RowMapper<AdminEntity>) (rs, rowNum) -> {
      AdminEntity adminEntity = new AdminEntity();
      adminEntity.setSTAFF_NO(Integer.valueOf(rs.getString("STAFF_NO")));
      adminEntity.setPASSWORD(rs.getString("PASSWORD"));
      adminEntity.setNAME(rs.getString("NAME"));
      adminEntity.setAGE(Integer.valueOf(rs.getString("AGE")));
      adminEntity.setSEX(rs.getString("SEX").charAt(0));
      adminEntity.setREGISTER_DATE(rs.getDate("REGISTER_DATE"));
      adminEntity.setLAST_UPD_DATE(rs.getTimestamp("LAST_UPD_DATE"));

      return adminEntity;
    };
  }

  @Override
  public AdminEntity login(Integer MEMBER_NO, String PASSWORD) {
    String sql = "select * from ONLINE_STAFF where STAFF_NO = :MEMBER_NO";
    MapSqlParameterSource param = new MapSqlParameterSource();
    param.addValue("MEMBER_NO", MEMBER_NO);

    return namedParameterJdbcTemplate.query(sql, param, RowMapper()).get(0);
  }

}
