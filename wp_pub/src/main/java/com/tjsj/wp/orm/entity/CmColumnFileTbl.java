package com.tjsj.wp.orm.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the cm_column_file_tbl database table.
 * 
 */
@Entity
@Table(name="cm_column_file_tbl")
@NamedQuery(name="CmColumnFileTbl.findAll", query="SELECT c FROM CmColumnFileTbl c")
public class CmColumnFileTbl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="column_id")
	private int columnId;

	@Column(name="file_id")
	private int fileId;

	@Column(name="insert_time")
	private Timestamp insertTime;

	public CmColumnFileTbl() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getColumnId() {
		return this.columnId;
	}

	public void setColumnId(int columnId) {
		this.columnId = columnId;
	}

	public int getFileId() {
		return this.fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public Timestamp getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

}