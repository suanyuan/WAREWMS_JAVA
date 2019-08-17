package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class DocSerialNumRecord  implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private int cartonNo;

	private String orderNo;

	private String serialNum;

    public DocSerialNumRecord() {
    }

    public DocSerialNumRecord(String orderNo, String serialNum) {
        this.orderNo = orderNo;
        this.serialNum = serialNum;
    }

    public DocSerialNumRecord(int cartonNo, String orderNo, String serialNum) {
        this.cartonNo = cartonNo;
        this.orderNo = orderNo;
        this.serialNum = serialNum;
    }
}
