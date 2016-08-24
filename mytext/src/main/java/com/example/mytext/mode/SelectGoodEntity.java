package com.example.mytext.mode;

import java.util.List;

public class SelectGoodEntity {
	private Data data;
	private String resCode;
	private String resMsg;
	private String serialNum;

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	public void setRescode(String rescode) {
		this.resCode = rescode;
	}

	public String getRescode() {
		return resCode;
	}

	public void setResmsg(String resmsg) {
		this.resMsg = resmsg;
	}

	public String getResmsg() {
		return resMsg;
	}

	public void setSerialnum(String serialnum) {
		this.serialNum = serialnum;
	}

	public String getSerialnum() {
		return serialNum;
	}

	public static class Goods {

		private String id;
		private String img;
		private String marketPrice;
		private String name;
		private String no;
		private String price;
		private int sales;
		private int isShipping;
		private String shortDesc;
		private String thumb;
		private String unit;

		public int getIsShipping() {
			return isShipping;
		}

		public void setIsShipping(int isShipping) {
			this.isShipping = isShipping;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getId() {
			return id;
		}

		public void setImg(String img) {
			this.img = img;
		}

		public String getImg() {
			return img;
		}

		public void setMarketprice(String marketprice) {
			this.marketPrice = marketprice;
		}

		public String getMarketprice() {
			return marketPrice;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setNo(String no) {
			this.no = no;
		}

		public String getNo() {
			return no;
		}

		public void setPrice(String price) {
			this.price = price;
		}

		public String getPrice() {
			
			return price;
		}

		public void setSales(int sales) {
			this.sales = sales;
		}

		public int getSales() {
			return sales;
		}

		public void setShortdesc(String shortdesc) {
			this.shortDesc = shortdesc;
		}

		public String getShortdesc() {
			return shortDesc;
		}

		public void setThumb(String thumb) {
			this.thumb = thumb;
		}

		public String getThumb() {
			return thumb;
		}

		public void setUnit(String unit) {
			this.unit = unit;
		}

		public String getUnit() {
			return unit;
		}

	}

	public static class Data {

		private int totalRec;
		private int currPage;
		private List<Goods> goods;
		private String imgBasePath;
		private int totalPage;

		public void setTotalrec(int totalrec) {
			this.totalRec = totalrec;
		}

		public int getTotalrec() {
			return totalRec;
		}

		public void setCurrpage(int currpage) {
			this.currPage = currpage;
		}

		public int getCurrpage() {
			return currPage;
		}

		public void setGoods(List<Goods> goods) {
			this.goods = goods;
		}

		public List<Goods> getGoods() {
			return goods;
		}

		public void setImgbasepath(String imgbasepath) {
			this.imgBasePath = imgbasepath;
		}

		public String getImgbasepath() {
			return imgBasePath;
		}

		public void setTotalpage(int totalpage) {
			this.totalPage = totalpage;
		}

		public int getTotalpage() {
			return totalPage;
		}

	}
}
