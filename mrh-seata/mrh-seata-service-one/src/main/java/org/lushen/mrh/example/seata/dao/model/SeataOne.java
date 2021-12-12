package org.lushen.mrh.example.seata.dao.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeataOne implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String name;

}