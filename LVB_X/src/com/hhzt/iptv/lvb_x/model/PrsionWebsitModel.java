package com.hhzt.iptv.lvb_x.model;

/**
 * 资讯内容
 * 
 * @author 2015pc1
 * 
 */
public class PrsionWebsitModel {
	private int id;
	private String title;
	private String synopsis;
	private String author;
	private String content;

	private String img;
	private String source;
	private String url;
	private long time;
	private String menuUrl;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	@Override
	public String toString() {
		return "PrsionWebsitModel [id=" + id + ", title=" + title
				+ ", synopsis=" + synopsis + ", author=" + author
				+ ", content=" + content + ", img=" + img + ", source="
				+ source + ", url=" + url + ", time=" + time + ", menuUrl="
				+ menuUrl + "]";
	}

}
