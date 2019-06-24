import com.wms.service.GspEnterpriseInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/6/23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/applicationContext.xml",
        "classpath*:/conf/spring-datasource.xml",
        "classpath*:/conf/spring-mybatis.xml"

})
public class GspServiceTest {

    @Autowired
    private GspEnterpriseInfoService gspEnterpriseInfoService;

    @Test
    public void test(){
        gspEnterpriseInfoService.getGspEnterpriseInfoCombobox();
    }
}