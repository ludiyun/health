package Vo;

import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;

import java.io.Serializable;
import java.util.List;

public class CheckGroupVo implements Serializable {
/*检查组信息*/
    private CheckGroup checkGroup;
//    所有的检查项信息
    private List<CheckItem> checkItems;
    //被选中的检查项信息
    private List<Integer> checkitemIds;


    public CheckGroupVo() {
    }

    public CheckGroupVo(CheckGroup checkGroup, List<CheckItem> checkItems, List<Integer> checkitemIds) {
        this.checkGroup = checkGroup;
        this.checkItems = checkItems;
        this.checkitemIds = checkitemIds;
    }

    public CheckGroup getCheckGroup() {
        return checkGroup;
    }

    public void setCheckGroup(CheckGroup checkGroup) {
        this.checkGroup = checkGroup;
    }

    public List<CheckItem> getCheckItems() {
        return checkItems;
    }

    public void setCheckItems(List<CheckItem> checkItems) {
        this.checkItems = checkItems;
    }

    public List<Integer> getCheckitemIds() {
        return checkitemIds;
    }

    public void setCheckitemIds(List<Integer> checkitemIds) {
        this.checkitemIds = checkitemIds;
    }
}
