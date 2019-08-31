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
  private long storageFlag=2;           //贮存条件 1||0 默认2未检查
  private long flowFlag=2;              //作业流程 1||0 默认2未检查
  private long signFlag=2;              //标志清晰 1||0 默认2未检查
  private long fenceFlag=2;             //防护措施 1||0 默认2未检查
  private long sanitationFlag=2;        //卫生环境 1||0 默认2未检查
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
