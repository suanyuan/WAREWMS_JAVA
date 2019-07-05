import com.wms.entity.GspEnterpriseInfo;
import com.wms.mybatis.dao.DocAsnDetailsMybatisDao;
import com.wms.mybatis.dao.DocAsnHeaderMybatisDao;
import com.wms.service.GspEnterpriseInfoService;
import com.wms.tools.FieldUtil;
import com.wms.utils.BeanUtils;
import com.wms.vo.form.GspBusinessLicenseForm;
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
import java.util.HashMap;
import java.util.Map;

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
    @Autowired
    private DocAsnHeaderMybatisDao docAsnHeaderMybatisDao;
    @Autowired
    private DocAsnDetailsMybatisDao docAsnDetailsMybatisDao;

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

    @Test
    public void beanTest() throws Exception{
        GspBusinessLicenseForm gspBusinessLicenseForm = new GspBusinessLicenseForm();
        gspBusinessLicenseForm.setEnterpriseId("asdas");
        System.out.println(BeanUtils.isEmptyFrom(gspBusinessLicenseForm));
    }

    @Test
    public void testSec(){
       Map<String,Object> map = new HashMap<>();
        map.put("warehouseid","WH01");
        map.put("resultNo","a");
        map.put("resultCode","101");
       String str = docAsnHeaderMybatisDao.getIdSequence(map);
       System.out.println(map.get("resultNo"));


    }
}