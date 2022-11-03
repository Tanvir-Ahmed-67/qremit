package abl.frd.qremit.repository;

import abl.frd.qremit.model.NafexModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface NafexModelRepository extends JpaRepository<NafexModel, Integer> {
    @Query("SELECT n FROM NafexModel n WHERE n.checkT24 = '1'")
    List<NafexModel> findAllNafexModelHavingOnlineAccount();
    @Query("SELECT n FROM NafexModel n WHERE n.checkCoc = '1'")
    List<NafexModel> findAllNafexModelHavingCoc();
    @Query("SELECT n FROM NafexModel n WHERE n.checkBeftn = '1'")
    List<NafexModel> findAllNafexModelHavingBeftn();
    @Query("SELECT n FROM NafexModel n WHERE n.checkAccPayee = '1'")
    List<NafexModel> findAllNafexModelHavingAccountPayee();
}
