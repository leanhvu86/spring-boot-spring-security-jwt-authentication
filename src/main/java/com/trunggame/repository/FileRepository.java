package com.trunggame.repository;

import com.trunggame.models.FileDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileDocument, Long> {

    @Query(value = "SELECT * FROM file WHERE  status = :status and file_type = :type ", nativeQuery = true)
    ArrayList<FileDocument> getListBanner(String status, String type);

    Optional<FileDocument> findFirstByUniqId(String uniqId);

    @Transactional
    @Modifying
    @Query(value = " UPDATE  file set owner_id = null WHERE uniq_id in (:ids) ", nativeQuery = true)
    void updateFileLinkToGame(List<String> ids);
}
