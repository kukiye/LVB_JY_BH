package com.hhzt.iptv.lvb_x.model;

public class BookModel {
	private int id;
	private String bookname;
	private String synopsis;
	private String author;
	private String content;
	private int type;
	private String img;
	private String filePath;
	private String source;
	private long createTime;
	private long updateTime;
	private int assign;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public int getAssign() {
		return assign;
	}

	public void setAssign(int assign) {
		this.assign = assign;
	}

	@Override
	public String toString() {
		return "BookModel [id=" + id + ", bookname=" + bookname + ", synopsis=" + synopsis
				+ ", author=" + author + ", content=" + content + ", type=" + type + ", img=" + img
				+ ", filePath=" + filePath + ", source=" + source + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", assign=" + assign + "]";
	}

}
