package com.furreverhome.Furrever_Home.repository;

import com.furreverhome.Furrever_Home.entities.PasswordResetToken;
import com.furreverhome.Furrever_Home.entities.User;
import java.util.Date;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken, Long> {
  PasswordResetToken findByToken(String token);

  PasswordResetToken findByUser(User user);

  Stream<PasswordResetToken> findAllByExpiryDateLessThan(Date now);

  void deleteByExpiryDateLessThan(Date now);

  @Modifying
  @Query("delete from PasswordResetToken t where t.expiryDate <= ?1")
  void deleteAllExpiredSince(Date now);
}
