package api.hotel.features.user;

import api.hotel.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository <Role, Integer> {

    @Query("""
      SELECT r
      FROM Role AS r
      WHERE r.name = 'USER'
      """)
    Role findRoleUser();

    @Query("SELECT r FROM Role AS r WHERE r.name = 'CUSTOMER'")
    Role findRoleCustomer();


}
