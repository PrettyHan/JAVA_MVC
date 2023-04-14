package eCommerce.Repository;

import eCommerce.Entity.AdminEntity;

public interface AdminRepository {

  AdminEntity login(Integer MEMBER_NO, String PASSWORD);

}
