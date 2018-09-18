package com.hhzt.iptv.lvb_x.interfaces;

import com.hhzt.iptv.lvb_x.model.VodDetailItemModel;

public interface IVodDetailItemable {

	void moveItemDown(int position, int totalPage);

	void clickItemBtn(VodDetailItemModel itemModel);

	void moveItemUp(int position);

}
