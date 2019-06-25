import com.wms.entity.GspEnterpriseInfo;
import com.wms.service.GspEnterpriseInfoService;
import com.wms.tools.FieldUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.beans.PropertyDescriptor;
import java.nio.file.Files;

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
        try{
            GspEnterpriseInfo gspEnterpriseInfo = new GspEnterpriseInfo();
            gspEnterpriseInfo.setContacts("123");

            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(gspEnterpriseInfo);
            for (int i = 0; i < descriptors.length; i++) {
                String name = descriptors[i].getName();
                if (!"class".equals(name)) {
                    System.out.println(name+":"+ propertyUtilsBean.getNestedProperty(gspEnterpriseInfo, name));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}