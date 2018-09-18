package com.hhzt.iptv.lvb_x.model;

/**
 * 资讯内容
 * @author 2015pc1
 *
 */
public class PrsionInfoModel {
	private int id;
	private String title;
	private String synopsis;
	private String author;
	private String content;
	private String newsCategory;
	private int type;
	private String img;
	private String source;
	private long createTime;
	private long updateTime;
	private String assign;
	private String iptvprofileids;
	private String mediacontentid;
	private String contenturl;
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
	public String getNewsCategory() {
		return newsCategory;
	}
	public void setNewsCategory(String newsCategory) {
		this.newsCategory = newsCategory;
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
	public String getAssign() {
		return assign;
	}
	public void setAssign(String assign) {
		this.assign = assign;
	}
	public String getIptvprofileids() {
		return iptvprofileids;
	}
	public void setIptvprofileids(String iptvprofileids) {
		this.iptvprofileids = iptvprofileids;
	}
	public String getMediacontentid() {
		return mediacontentid;
	}
	public void setMediacontentid(String mediacontentid) {
		this.mediacontentid = mediacontentid;
	}
	public String getContenturl() {
		return contenturl;
	}
	public void setContenturl(String contenturl) {
		this.contenturl = contenturl;
	}
	@Override
	public String toString() {
		return "PrsionInfoModel [id=" + id + ", title=" + title + ", synopsis=" + synopsis
				+ ", author=" + author + ", content=" + content + ", newsCategory=" + newsCategory
				+ ", type=" + type + ", img=" + img + ", source=" + source + ", createTime="
				+ createTime + ", updateTime=" + updateTime + ", assign=" + assign
				+ ", iptvprofileids=" + iptvprofileids + ", mediacontentid=" + mediacontentid
				+ ", contenturl=" + contenturl + "]";
	}
	
	
	
	
}
