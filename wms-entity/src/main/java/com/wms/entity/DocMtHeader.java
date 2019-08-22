package   com.wms.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class DocMtHeader implements Serializable {

  private String mtno;
  private String mtstatus;      //codeid = 'MT_STS'
  private String mttype;
  private Date fromdate;        //yyyy-MM-dd
  private Date todate;          //yyyy-MM-dd
  private String userdefine1;
  private String userdefine2;
  private String userdefine3;
  private String userdefine4;
  private String userdefine5;
  private String remark;
  private Date addtime;
  private String addwho;
  private Date edittime;
  private String editwho;
  private String warehouseid;

}
