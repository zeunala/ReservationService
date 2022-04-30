package zeunala.reservation.dto;

public class CommentResult {
	private String comment;
	private Integer commentId;
	private CommentImage commentImage;
	private String createDate;
	private String modifyDate;
	private Integer productId;
	private Double score;
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	public CommentImage getCommentImage() {
		return commentImage;
	}
	public void setCommentImage(CommentImage commentImage) {
		this.commentImage = commentImage;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	
	@Override
	public String toString() {
		return "CommentResult [comment=" + comment + ", commentId=" + commentId + ", commentImage=" + commentImage
				+ ", createDate=" + createDate + ", modifyDate=" + modifyDate + ", productId=" + productId + ", score="
				+ score + "]";
	}
}
