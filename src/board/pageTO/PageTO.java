package board.pageTO;

import java.util.ArrayList;

import board.DTO.BoardDTO;

public class PageTO {
	private ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
	
	private int contentCount = 10;
	private int totalCount;
	private int pageNum;
	private int firstPage;
	private int endPage;
	
	public PageTO() {}
	
	public PageTO(ArrayList<BoardDTO> list, int contentCount, int totalCount, int pageNum, int firstPage, int endPage) {
		super();
		this.list = list;
		this.contentCount = contentCount;
		this.totalCount = totalCount;
		this.pageNum = pageNum;
		this.firstPage = firstPage;
		this.endPage = endPage;
	}
	
	public ArrayList<BoardDTO> getList() {
		return list;
	}
	public void setList(ArrayList<BoardDTO> list) {
		this.list = list;
	}
	public int getContentCount() {
		return contentCount;
	}
	public void setContentCount(int contentCount) {
		this.contentCount = contentCount;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getFirstPage() {
		return firstPage;
	}
	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
}