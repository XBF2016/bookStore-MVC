package bs.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class Conditions {
	// 条件查询的条件
	// 该对象有两个成员。一个是存储条件键值对的Map对象，一个是存储是否进行模糊查询的标记List对象
	// 之所以用LinkedHashMap是因为它可以保留元素的插入顺序
	private Map<String, Object> conditionMap = new LinkedHashMap<String, Object>();
	private List<Boolean> needDimList = new ArrayList<Boolean>();

	public synchronized Map<String, Object> getConditionMap() {
		return conditionMap;
	}

	public synchronized List<Boolean> getNeedDimList() {
		return needDimList;
	}

	// 因为可能要添加多个条件，所以要定义添加方法
	public boolean addConditions(String key, Object value, boolean needDim) {
		// 进行条件的有效性检查
		// 首先判断key是否合法
		if (key == null || key.trim().length() == 0) {
			return false;
		}
		// 其次判断value是否合法
		if (value == null) {
			return false;
		}
		// 最后判断value为string类型时是否合法
		if (value instanceof String) {
			if (((String) value).trim().length() == 0) {
				return false;
			}
		}
		conditionMap.put(key, value);
		needDimList.add(needDim);
		return true;
	}

	public String createURLQueryString() {
		StringBuilder re = new StringBuilder();
		Set<Entry<String, Object>> entrySet = conditionMap.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			re.append(entry.getKey()).append("=").append(entry.getValue())
					.append("&");
		}
		if (re.length() > 0) {
			re.deleteCharAt(re.length() - 1);
		}
		return re.toString();
	}

}
