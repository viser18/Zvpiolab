package com.example.server.repositories;

import com.example.server.entities.DeviceLicense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceLicenseRepository extends JpaRepository<DeviceLicense, Long> {
    List<DeviceLicense> findByDeviceId(Long deviceId);
    List<DeviceLicense> findByLicenseId(Long licenseId);
    Optional<DeviceLicense> findByLicenseIdAndDeviceId(Long licenseId, Long deviceId);

    @Query("SELECT COUNT(dl) FROM DeviceLicense dl WHERE dl.licenseId = :licenseId")
    Integer countByLicenseId(@Param("licenseId") Long licenseId);
}